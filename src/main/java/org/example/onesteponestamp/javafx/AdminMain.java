package org.example.onesteponestamp.javafx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.example.onesteponestamp.admin.AuthManager;

public class AdminMain {

  private final BorderPane mainLayOut;

  Button autoApplyButton;
  Button immigrationButton;
  Button illegalButton;

  public AdminMain(BorderPane mainLayOut) {
    this.mainLayOut = mainLayOut;
  }

  public void show() {
    VBox menu = new VBox(10);
    menu.setPadding(new Insets(10));
    menu.setAlignment(Pos.CENTER_LEFT);

    autoApplyButton = new Button("자동 입출국 심사 목록 조회");
    autoApplyButton.setMaxWidth(Double.MAX_VALUE);

    immigrationButton = new Button("입출국 목록 조회");
    immigrationButton.setMaxWidth(Double.MAX_VALUE);
    immigrationButton.setOnAction(e -> showImmigrationList(immigrationButton));

    illegalButton = new Button("불법체류자 목록 조회");
    illegalButton.setMaxWidth(Double.MAX_VALUE);

    menu.getChildren().addAll(autoApplyButton, immigrationButton, illegalButton);
    mainLayOut.setLeft(menu);
    mainLayOut.setCenter(new VBox());

    Region space = new Region();
    space.setMaxHeight(350);
    VBox.setVgrow(space, Priority.ALWAYS);

    Button homeButton = new Button("홈으로");
    homeButton.setMaxWidth(Double.MAX_VALUE);
    homeButton.setOnAction(e -> showHomeForm(homeButton));

    menu.getChildren().addAll(space, homeButton);
    mainLayOut.setLeft(menu);
    mainLayOut.setCenter(new VBox());
  }

  private void resetButton() {
    autoApplyButton.setStyle("");
    immigrationButton.setStyle("");
    illegalButton.setStyle("");
  }

  private void showImmigrationList(Button immigrationButton) {
    immigrationButton.setStyle("-fx-background-color: #808080;");
    ImmigrationListForm immigrationForm = new ImmigrationListForm();
    mainLayOut.setCenter(immigrationForm.getForm());
  }

  private void showHomeForm(Button homeButton) {
    mainLayOut.setLeft(null);
    SelectionMain main = new SelectionMain(mainLayOut);
    AuthManager.getInstance().logout();
    main.show();
  }
}