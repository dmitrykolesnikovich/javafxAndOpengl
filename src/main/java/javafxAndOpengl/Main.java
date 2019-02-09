package javafxAndOpengl;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.lwjgl.opengl.GL11;

public class Main extends Application {

  private final static int initialWidth = 1280;
  private final static int initialHeight = 720;
  private GLView glView1;
  private GLView glView2;

  @Override
  public void start(Stage primaryStage) {

    TabPane tabPane = new TabPane();
    Scene scene = new Scene(tabPane, 600, 500);
    {
      Tab tab = new DraggableTab("Tab 1");
      glView1 = new GLView(() -> {
        GL11.glClearColor((float) Math.random(), 0, 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
      }, initialWidth, initialHeight);
      tab.setContent(glView1.getImageView());
      scene.widthProperty().addListener(
          (observable, oldValue, newValue) -> glView1.getImageView().setFitWidth(newValue.intValue()));
      scene.heightProperty().addListener(
          (observable, oldValue, newValue) -> glView1.getImageView().setFitHeight(newValue.intValue()));
      tabPane.getTabs().add(tab);
    }
    {
      Tab tab = new DraggableTab("Tab 2");
      glView2 = new GLView(() -> {
        GL11.glClearColor(0, (float) Math.random(), 0, 1);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
      }, initialWidth, initialHeight);
      tab.setContent(glView2.getImageView());
      scene.widthProperty().addListener(
          (observable, oldValue, newValue) -> glView2.getImageView().setFitWidth(newValue.intValue()));
      scene.heightProperty().addListener(
          (observable, oldValue, newValue) -> glView2.getImageView().setFitHeight(newValue.intValue()));
      tabPane.getTabs().add(tab);
    }


    primaryStage.setScene(scene);
    primaryStage.setTitle("Hello");
    primaryStage.show();


    Timeline fiveSecondsWonder = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
      glView1.onUpdate();
      glView2.onUpdate();
    }));
    fiveSecondsWonder.setCycleCount(Timeline.INDEFINITE);
    fiveSecondsWonder.play();

  }

  public static void main(String[] args) {
    launch(args);
  }

}
