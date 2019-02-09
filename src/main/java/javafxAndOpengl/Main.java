package javafxAndOpengl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import org.lwjgl.opengl.GL11;

public class Main extends Application {

  private final int initialWidth = 1280, initialHeight = 720;

  static {
    GLView.setDebug(true);
  }

  private GLView glView1;
  private GLView glView2;

  @Override
  public void start(Stage primaryStage) throws Exception {

    TabPane tabPane = new TabPane();
    Scene scene = new Scene(tabPane, 600, 500);
    {
      Tab tab = new Tab();
      glView1 = new GLView(() -> {
        GL11.glClearColor(1, 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
      }, initialWidth, initialHeight);
      tab.setContent(glView1.get());
      scene.widthProperty().addListener(
          (observable, oldValue, newValue) -> glView1.get().setFitWidth(newValue.intValue()));
      scene.heightProperty().addListener(
          (observable, oldValue, newValue) -> glView1.get().setFitHeight(newValue.intValue()));
      tabPane.getTabs().add(tab);
    }
    {
      Tab tab = new Tab();
      glView2 = new GLView(() -> {
        GL11.glClearColor(0, 1, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
      }, initialWidth, initialHeight);
      tab.setContent(glView2.get());
      scene.widthProperty().addListener(
          (observable, oldValue, newValue) -> glView2.get().setFitWidth(newValue.intValue()));
      scene.heightProperty().addListener(
          (observable, oldValue, newValue) -> glView2.get().setFitHeight(newValue.intValue()));
      tabPane.getTabs().add(tab);
    }


    primaryStage.setScene(scene);
    primaryStage.setTitle("Hello");
    primaryStage.show();

    glView1.onUpdate();
    glView2.onUpdate();
  }

  public static void main(String[] args) {
    launch(args);
  }
}
