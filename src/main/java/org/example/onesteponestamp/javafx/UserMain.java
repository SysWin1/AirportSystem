package org.example.onesteponestamp.javafx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class UserMain {

  private final BorderPane mainLayout;
  Button autoEntryFormButton;
  Button viewApplicationListButton;
  Button entryApplicationButton;

  public UserMain(BorderPane mainLayout) {
    this.mainLayout = mainLayout;
  }

  public void show() {
    VBox menu = new VBox(10);
    menu.setPadding(new Insets(10));
    menu.setAlignment(Pos.CENTER_LEFT);

    autoEntryFormButton = new Button("자동 출입국 신청서 작성");
    autoEntryFormButton.setMaxWidth(Double.MAX_VALUE);
    autoEntryFormButton.setOnAction(e -> showAutoEntryForm(autoEntryFormButton));

    viewApplicationListButton = new Button("출입국 신청 결과 목록 확인");
    viewApplicationListButton.setMaxWidth(Double.MAX_VALUE);
    viewApplicationListButton.setOnAction(e -> showAutoApplyList(viewApplicationListButton));

    entryApplicationButton = new Button("출입국 신청");
    entryApplicationButton.setMaxWidth(Double.MAX_VALUE);
    entryApplicationButton.setOnAction(e -> showImmigrationGateForm(entryApplicationButton));

    menu.getChildren()
        .addAll(autoEntryFormButton, viewApplicationListButton, entryApplicationButton);
    mainLayout.setLeft(menu);
    mainLayout.setCenter(new VBox());  // 가운데를 빈 상태로 초기화

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
    homeButton.setOnAction(e -> showHomeForm(homeButton));

    menu.getChildren().addAll(space, homeButton);
    mainLayout.setLeft(menu);
    mainLayout.setCenter(new VBox());
  }

  //버튼 색상 초기화
  private void resetButtonStyles() {
    autoEntryFormButton.setStyle("");
    viewApplicationListButton.setStyle("");
    entryApplicationButton.setStyle("");
  }

  /**
   * 자동 출입국 신청서 작성 form UI로 연결
   */
  private void showAutoEntryForm(Button autoEntryFormButton) {
    resetButtonStyles();
    autoEntryFormButton.setStyle("-fx-background-color: #808080;"); // 회색으로 처리

    // AutoApplyForm을 생성하고 중앙에 추가
    AutoApplyForm autoApplyForm = new AutoApplyForm();
    mainLayout.setCenter(autoApplyForm.getForm());
  }

  /**
   * 자동 출입국 신청서 결과 목록 UI로 연결
   */
  private void showAutoApplyList(Button viewApplicationListButton) {
    resetButtonStyles();
    viewApplicationListButton.setStyle("-fx-background-color: #808080;"); // 회색으로 처리

    AutoApplyListView listView = new AutoApplyListView();
    VBox container = new VBox(10);
    container.setPadding(new Insets(10));
    container.setAlignment(Pos.CENTER);

    container.getChildren().addAll(listView.getForm(), listView.getTableView());
    mainLayout.setCenter(container);
  }

  private void showImmigrationGateForm(Button entryApplicationButton) {
    resetButtonStyles();
    entryApplicationButton.setStyle("-fx-background-color: #808080;"); //클릭시 색상 변경
    ImmigrationForm immigrationForm = new ImmigrationForm();
    mainLayout.setCenter(immigrationForm.getForm());
  }

  private void showHomeForm(Button homeButton) {
    mainLayout.setLeft(null);
    SelectionMain main = new SelectionMain(mainLayout);
    main.show();
  }
}