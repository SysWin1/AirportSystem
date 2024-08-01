package org.example.onesteponestamp;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.onesteponestamp.admin.AuthManager;
import org.example.onesteponestamp.immigration.ImmigrationDAO;
import org.example.onesteponestamp.javafx.UserMainViewController;


public class HelloApplication extends Application {

  private BorderPane mainLayout;
  private static final ImmigrationDAO immigrationDAO = new ImmigrationDAO();
  private AuthManager authManager;

  @Override
  public void start(Stage stage) {

    stage.setTitle("One Step - One Stamp");
    authManager = new AuthManager();

    try {
      // FXML 파일 로드
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/org/example/onesteponestamp/UserMainView.fxml"));
      Parent root = loader.load();

      // 컨트롤러 가져오기
      UserMainViewController controller = loader.getController();

      mainLayout = (BorderPane) root;
      controller.setMainLayout(mainLayout);

      Scene scene = new Scene(root, 950, 650);
      stage.setScene(scene);
      stage.show();

    } catch (IOException e) {
      e.printStackTrace();
    }


  }

  public static void main(String[] args) {
    launch();
  }
}