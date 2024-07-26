package org.example.onesteponestamp.javafx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class UserMain {

  private BorderPane mainLayout;

  public UserMain(BorderPane mainLayout) {
    this.mainLayout = mainLayout;
  }

  public void show() {
    VBox menu = new VBox(10);
    menu.setPadding(new Insets(10));
    menu.setAlignment(Pos.CENTER_LEFT);

    Button autoEntryFormButton = new Button("자동 출입국 신청서 작성");
    autoEntryFormButton.setMaxWidth(Double.MAX_VALUE);
    autoEntryFormButton.setOnAction(e -> showAutoEntryForm(autoEntryFormButton));

    Button checkFormButton = new Button("출입국 신청서 확인");
    checkFormButton.setMaxWidth(Double.MAX_VALUE);

    Button entryApplicationButton = new Button("출입국 신청");
    entryApplicationButton.setMaxWidth(Double.MAX_VALUE);

    menu.getChildren().addAll(autoEntryFormButton, checkFormButton, entryApplicationButton);
    mainLayout.setLeft(menu);
    mainLayout.setCenter(new VBox());  // 가운데를 빈 상태로 초기화
  }

  private void showAutoEntryForm(Button autoEntryFormButton) {
    autoEntryFormButton.setStyle("-fx-background-color: #808080;"); // 회색으로 처리

    // AutoApplyForm을 생성하고 중앙에 추가
    AutoApplyForm autoApplyForm = new AutoApplyForm();
    mainLayout.setCenter(autoApplyForm.getForm());
  }

}
