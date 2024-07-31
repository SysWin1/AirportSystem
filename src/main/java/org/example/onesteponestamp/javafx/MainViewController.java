package org.example.onesteponestamp.javafx;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class MainViewController {

  @FXML
  private BorderPane mainLayout;

  @FXML
  private Hyperlink footerlink;

  @FXML
  private void initialize() {
    footerlink.setOnAction(e -> {
      try {
        java.awt.Desktop.getDesktop()
            .browse(java.net.URI.create("https://github.com/SysWin1/OneStepOneStamp"));
      } catch (IOException ex) {
        throw new RuntimeException(ex);
      }
    });
  }

  public void setMainLayout(BorderPane mainLayout) {
    this.mainLayout = mainLayout;
  }

  /**
   * 자동 출입국 신청서 작성 form UI로 연결
   */
  public void showAutoEntryForm(ActionEvent event) {
//    autoEntryFormButton.setStyle("-fx-background-color: #808080;"); // 회색으로 처리

    // AutoApplyForm을 생성하고 중앙에 추가
    AutoApplyForm autoApplyForm = new AutoApplyForm();
    mainLayout.setCenter(autoApplyForm.getForm());
  }

  /**
   * 자동 출입국 신청서 결과 목록 UI로 연결
   */
  public void showAutoApplyList(ActionEvent event) {
//    viewApplicationListButton.setStyle("-fx-background-color: #808080;"); // 회색으로 처리

    AutoApplyListView listView = new AutoApplyListView();
    VBox container = new VBox(10);
    container.setPadding(new Insets(10));
    container.setAlignment(Pos.CENTER);

    container.getChildren().addAll(listView.getForm(), listView.getTableView());
    mainLayout.setCenter(container);
  }

  public void showImmigrationGateForm(Button entryApplicationButton) {
    entryApplicationButton.setStyle("-fx-background-color: #808080;"); //클릭시 색상 변경
    EntryApplyForm entryApplyForm = new EntryApplyForm();
    mainLayout.setCenter(entryApplyForm.getForm());
  }

  public void showHomeForm(Button homeButton) {
    mainLayout.setLeft(null);
    SelectionMain main = new SelectionMain(mainLayout);
    main.show();
  }

  public void showAdminLogin() {
    AdminLoginPage adminLogin = new AdminLoginPage(mainLayout);
    adminLogin.show();
  }
}
