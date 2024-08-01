package org.example.onesteponestamp.javafx;

import java.io.IOException;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.example.onesteponestamp.admin.LoginController;

public class AdminLoginPage {

  private final BorderPane pane;

  public AdminLoginPage(BorderPane pane) {
    this.pane = pane;
  }

  public void show() {
    // Create the LoginController
    LoginController loginController = new LoginController(pane);

    // Create UI elements
    Label titleLabel = new Label("Admin Login");
    titleLabel.setStyle("-fx-font-size: 30px; -fx-font-weight: bold;");

    TextField idField = new TextField();
    idField.setPromptText("아이디");
    idField.setStyle("-fx-background-color: #dcdcdc; -fx-font-size: 16px;");

    PasswordField passwordField = new PasswordField();
    passwordField.setPromptText("비밀번호");
    passwordField.setStyle("-fx-background-color: #dcdcdc; -fx-font-size: 16px;");

    Button loginButton = new Button("Log In");
    loginButton.setStyle(
        "-fx-background-color: #00bfff; -fx-text-fill: white; -fx-font-size: 16px;");

    loginController.initialize(idField, passwordField);
    loginButton.setOnAction(e -> {
      try {
        loginController.handleLoginButtonAction();
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    // Home button - image
    Image homeButtonImage = new Image(
        getClass().getResourceAsStream("/org/example/onesteponestamp/images/back-button.png"));
    ImageView homeButtonImageView = new ImageView(homeButtonImage);
    homeButtonImageView.setFitHeight(30);
    homeButtonImageView.setFitWidth(30);
    homeButtonImageView.setOnMouseClicked(e -> goHome());
    Button homeButton = new Button("", homeButtonImageView);
    homeButton.setStyle("-fx-background-color: transparent; -fx-border-color: transparent;");
    homeButton.setOnAction(e -> goHome());

    // Layout setup
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setAlignment(Pos.CENTER);

    GridPane.setConstraints(idField, 0, 1, 2, 1);
    GridPane.setConstraints(passwordField, 0, 2, 2, 1);
    GridPane.setConstraints(loginButton, 0, 3, 2, 1);

    gridPane.getChildren().addAll(titleLabel, idField, passwordField, loginButton);

    // 홈버튼 박스
    HBox topLayout = new HBox(10);
    topLayout.setAlignment(Pos.CENTER_LEFT);
    topLayout.setPadding(new Insets(0, 0, 0, 100));
    topLayout.getChildren().addAll(homeButton);

    VBox vBox = new VBox(20);
    vBox.setAlignment(Pos.CENTER);
    vBox.getChildren().addAll(topLayout, gridPane);

    pane.setCenter(vBox);
    idField.requestFocus();
  }

  private void goHome() {
    SelectionMain mainPage = new SelectionMain(pane);
    mainPage.show();
  }
}