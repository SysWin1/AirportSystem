package org.example.onesteponestamp;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.onesteponestamp.javafx.SelectionMain;


public class HelloApplication extends Application {

  private BorderPane mainLayout;

  @Override
  public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

    stage.setTitle("One Step - One Stamp");

    mainLayout = new BorderPane();
    SelectionMain selectionMain = new SelectionMain(mainLayout);

    Scene scene = new Scene(mainLayout, 800, 600);
    stage.setScene(scene);
    stage.show();

    selectionMain.show();
  }

  public static void main(String[] args) {
    launch();
  }
}