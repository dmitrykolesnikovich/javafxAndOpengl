package javafxAndOpengl;

import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;
import org.lwjgl.opengl.GL;
import org.lwjgl.opengl.GL11;
import org.lwjgl.system.MemoryUtil;

import java.nio.ByteBuffer;

public class GLView {

  private Runnable drawer;
  private WritableImage image;
  private ByteBuffer buffer;
  private long windowHandle;
  private int width;
  private int height;
  private ImageView imageView;

  public GLView(Runnable drawer, int initialWidth, int initialHeight) {
    this.drawer = drawer;
    windowHandle = setupContext();
    onResize(initialWidth, initialHeight);
  }

  public void onUpdate() {
    drawer.run();
    buffer.clear().position(0);
    GL11.glViewport(0, 0, width, height);
    GL11.glReadPixels(0, 0, width, height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
    PixelWriter pixelWriter = image.getPixelWriter();
    for (int x = 0; x < width; x++)
      for (int y = 0; y < height; y++) {
        int i = (x + (width * y)) * 4;
        int r = buffer.get(i) & 0xFF;
        int g = buffer.get(i + 1) & 0xFF;
        int b = buffer.get(i + 2) & 0xFF;
        pixelWriter.setArgb(x, y, (0xFF << 24) | (r << 16) | (g << 8) | b);
      }
  }

  public void onResize(int width, int height) {
    this.width = width;
    this.height = height;
    image = new WritableImage(width, height);
    GLFW.glfwSetWindowSize(windowHandle, width, height);
    buffer = BufferUtils.createByteBuffer(width * height * 4);
  }

  public void onClose() {
    GLFW.glfwDestroyWindow(windowHandle);
    GLFW.glfwTerminate();
  }

  public ImageView getImageView() {
    if (imageView == null) {
      imageView = new ImageView(image);
    }
    return imageView;
  }

  private static long setupContext() {
    if (GLFW.glfwInit() != GL11.GL_TRUE) {
      throw new IllegalStateException("Unable to initialize GLFW");
    }
    GLFW.glfwDefaultWindowHints();
    GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MAJOR, 3);
    GLFW.glfwWindowHint(GLFW.GLFW_CONTEXT_VERSION_MINOR, 2);
    GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_PROFILE, GLFW.GLFW_OPENGL_CORE_PROFILE);
    GLFW.glfwWindowHint(GLFW.GLFW_OPENGL_FORWARD_COMPAT, GL11.GL_TRUE);
    GLFW.glfwWindowHint(GLFW.GLFW_RESIZABLE, GL11.GL_FALSE);
    long window = GLFW.glfwCreateWindow(7, 5, "GL_Context_Holder", MemoryUtil.NULL, MemoryUtil.NULL);
    GLFW.glfwHideWindow(window);
    if (window == MemoryUtil.NULL) {
      GLFW.glfwTerminate();
      throw new RuntimeException("Failed to create the GLFW window");
    }
    GLFW.glfwMakeContextCurrent(window);
    GL.createCapabilities();
    return window;
  }

}
