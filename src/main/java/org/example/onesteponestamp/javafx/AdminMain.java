package org.example.onesteponestamp.javafx;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.onesteponestamp.autoapply.AutoApplyAdminController;

public class AdminMain {

  private final BorderPane mainLayOut;

  Button autoApplyButton;
  Button immigrationButton;
  Button foreignerButton;

  public AdminMain(BorderPane mainLayOut) {
    this.mainLayOut = mainLayOut;
  }

  public void show() {
    VBox menu = new VBox(10);
    menu.setPadding(new Insets(10));
    menu.setAlignment(Pos.CENTER_LEFT);

    autoApplyButton = new Button("자동 입출국 심사 목록 조회");
    autoApplyButton.setMaxWidth(Double.MAX_VALUE);
    autoApplyButton.setOnAction(e -> showAutoApplyList(autoApplyButton));

    immigrationButton = new Button("입출국 목록 조회");
    immigrationButton.setMaxWidth(Double.MAX_VALUE);
    immigrationButton.setOnAction(e -> showImmigrationList(immigrationButton));

    foreignerButton = new Button("외국인 목록 조회");
    foreignerButton.setMaxWidth(Double.MAX_VALUE);
    foreignerButton.setOnAction(e -> showForeignerList(foreignerButton));

    menu.getChildren().addAll(autoApplyButton, immigrationButton, foreignerButton);
    mainLayOut.setLeft(menu);
    mainLayOut.setCenter(new VBox());

    Region space = new Region();
    space.setMaxHeight(350);
    VBox.setVgrow(space, Priority.ALWAYS);

    ImageView homeIcon = new ImageView(
        new Image(
            "file:src/main/resources/org/example/onesteponestamp/images/home.png")); //content부분 아이콘 셋팅
    homeIcon.setFitHeight(30);
    homeIcon.setFitWidth(30);

    Button homeButton = new Button("홈으로");
    homeButton.setGraphic(homeIcon);
    homeButton.setMaxWidth(Double.MAX_VALUE);
    homeButton.setOnAction(e -> {
      try {
        showHomeForm(homeButton);
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });

    menu.getChildren().addAll(space, homeButton);
    mainLayOut.setLeft(menu);
    mainLayOut.setCenter(new VBox());
  }

  private void resetButton() {
    autoApplyButton.setStyle("");
    immigrationButton.setStyle("");
    foreignerButton.setStyle("");
  }

  private void showAutoApplyList(Button autoApplyButton) {
    resetButton();
    autoApplyButton.setStyle("-fx-background-color: #808080;");

    AutoApplyAdminView view = new AutoApplyAdminView();
    new AutoApplyAdminController(view);
    mainLayOut.setCenter(view.getMainGrid());
  }

  private void showImmigrationList(Button immigrationButton) {
    resetButton();
    immigrationButton.setStyle("-fx-background-color: #808080;");
    ImmigrationListForm immigrationForm = new ImmigrationListForm();
    mainLayOut.setCenter(immigrationForm.getForm());
  }

  private void showForeignerList(Button foreignerButton) {
    resetButton();
    foreignerButton.setStyle("-fx-background-color: #808080;");
    ForeignerForm foreignerForm = new ForeignerForm();
    mainLayOut.setCenter(foreignerForm.getForm());
  }

  private void showHomeForm(Button homeButton) throws IOException {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/org/example/onesteponestamp/javafx/UserMainView.fxml"));
    BorderPane adminLoginPain = loader.load();
    mainLayOut.getChildren().setAll(adminLoginPain);
  }
}