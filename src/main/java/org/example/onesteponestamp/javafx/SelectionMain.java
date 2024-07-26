package org.example.onesteponestamp.javafx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class SelectionMain {

  private final BorderPane mainLayout;

  public SelectionMain(BorderPane mainLayout) {
    this.mainLayout = mainLayout;
  }

  public void show() {
    VBox selectionBox = new VBox(10);
    selectionBox.setPadding(new Insets(20));
    selectionBox.setAlignment(Pos.CENTER);

    Button userButton = new Button("사용자");
    userButton.setPrefSize(200, 200);
    userButton.setOnAction(e -> showUserMenu());

    Button adminButton = new Button("관리자");
    adminButton.setPrefSize(200, 200);

    HBox buttonBox = new HBox(20, userButton, adminButton);
    buttonBox.setAlignment(Pos.CENTER);

    selectionBox.getChildren().add(buttonBox);
    mainLayout.setCenter(selectionBox);
  }

  private void showUserMenu() {
    UserMain userMenu = new UserMain(mainLayout);
    userMenu.show();
  }
}
