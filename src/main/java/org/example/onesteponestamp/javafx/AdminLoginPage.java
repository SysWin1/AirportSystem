package org.example.onesteponestamp.javafx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
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
    loginButton.setOnAction(e -> loginController.handleLoginButtonAction());

    // Layout setup
    GridPane gridPane = new GridPane();
    gridPane.setPadding(new Insets(10));
    gridPane.setHgap(10);
    gridPane.setVgap(10);
    gridPane.setAlignment(Pos.CENTER);

    GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
    GridPane.setConstraints(idField, 0, 1, 2, 1);
    GridPane.setConstraints(passwordField, 0, 2, 2, 1);
    GridPane.setConstraints(loginButton, 0, 3, 2, 1);

    gridPane.getChildren().addAll(titleLabel, idField, passwordField, loginButton);

    // Set up the BorderPane
    pane.setCenter(gridPane);
  }
}
