package org.example.onesteponestamp.admin;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import org.example.onesteponestamp.javafx.AdminMain;

public class LoginController {

  private TextField idField;
  private PasswordField passwordField;
  private BorderPane pane;

  private final AuthManager authManager;

  private final LoginService loginService;

  public LoginController(BorderPane pane) {
    this.pane = pane;
    loginService = new LoginService();
    authManager = AuthManager.getInstance();
  }

  public void initialize(TextField idField, PasswordField passwordField) {
    this.idField = idField;
    this.passwordField = passwordField;
  }

  public void handleLoginButtonAction() {
    String id = idField.getText();
    String password = passwordField.getText();

    boolean success = loginService.login(id, password);
    if (success) {
      showAlert("로그인 성공!!", "환영하니다, " + id + "님 !");
      showAdminMenu();
    } else {
      showAlert("로그인 실패", "ID나 비밀번호를 틀렸습니다.");
    }
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void showAdminMenu() {
    AdminMain adminMenu = new AdminMain(pane);
    adminMenu.show();
  }
}
