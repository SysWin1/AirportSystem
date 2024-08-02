package org.example.onesteponestamp.javafx;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import org.example.onesteponestamp.admin.LoginController;

public class AdminLogInController {

  @FXML
  private BorderPane pane;

  @FXML
  private TextField idField;

  @FXML
  private PasswordField passwordField;

  @FXML
  private Button loginButton;

  @FXML
  private Button homeButton;

  @FXML
  private ImageView homeIcon;

  @FXML
  private ImageView logoView;

  private LoginController loginController;

  @FXML
  public void initialize() {
    if (pane == null) {
      throw new IllegalStateException("Pane is not initialized.");
    }
    System.out.println(pane.getStylesheets());
    loginController = new LoginController(pane);
    loginController.initialize(idField, passwordField);

    Image homeImage = new Image(
        getClass().getResourceAsStream("/org/example/onesteponestamp/images/home.png"));
    homeIcon.setImage(homeImage);

    if (logoView != null) {
      Image logoImage = new Image(
          getClass().getResourceAsStream("/org/example/onesteponestamp/images/teamlogo.png"));
      logoView.setImage(logoImage);
    }

    loginButton.setOnAction(e -> {
      try {
        loginController.handleLoginButtonAction();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });
    homeButton.setOnAction(e -> {
      try {
        goHome();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  private void goHome() throws IOException {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/org/example/onesteponestamp/fxml/UserMainView.fxml"));
    BorderPane mainView = loader.load();

    // 첫화면으로 돌아가기
    pane.getScene().setRoot(mainView);
  }
}
