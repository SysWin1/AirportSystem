package org.example.onesteponestamp.javafx;


import javafx.fxml.FXML;
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
    private ImageView homeButtonImageView;

    private LoginController loginController;
    @FXML
    private ImageView imageView;

    @FXML
    public void initialize() {
        if (pane == null) {
            throw new IllegalStateException("Pane is not initialized.");
        }
        System.out.println(pane.getStylesheets());
        loginController = new LoginController(pane);
        loginController.initialize(idField, passwordField);
        if(imageView != null){
            Image image = new Image(getClass().getResourceAsStream("/org/example/onesteponestamp/images/back-button.png"));
            imageView.setImage(image);
        }
        loginButton.setOnAction(e -> loginController.handleLoginButtonAction());
        homeButton.setOnAction(e -> goHome());
        homeButtonImageView.setOnMouseClicked(e -> goHome());
    }

    private void goHome() {
        SelectionMain mainPage = new SelectionMain(pane);
        mainPage.show();
    }

}
