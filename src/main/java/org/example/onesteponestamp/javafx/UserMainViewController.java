package org.example.onesteponestamp.javafx;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import lombok.Setter;

public class UserMainViewController {

  @Setter
  @FXML
  private BorderPane mainLayout;

  @FXML
  private void initialize() {
  }

  /**
   * 자동 출입국 신청서 작성 form UI로 연결
   */
  public void showAutoApplyForm(ActionEvent event) {

    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/org/example/onesteponestamp/javafx/AutoApplyFormView.fxml"));
      Parent autoApplyForm = loader.load();
      loader.getController();

      this.mainLayout.setCenter(autoApplyForm);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 자동 출입국 신청서 결과 목록 UI로 연결
   */
  public void showAutoApplyList(ActionEvent event) {

    AutoApplyListView listView = new AutoApplyListView();
    VBox container = new VBox(10);
    container.setPadding(new Insets(10));
    container.setAlignment(Pos.CENTER);

    container.getChildren().addAll(listView.getForm(), listView.getTableView());
    mainLayout.setCenter(container);
  }

  public void showImmigrationGateForm(ActionEvent event) {
    ImmigrationForm immigrationForm = new ImmigrationForm();
    mainLayout.setCenter(immigrationForm.getForm());
  }

  public void showUserMainView(MouseEvent event) {
    try {
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/org/example/onesteponestamp/javafx/UserMainContentView.fxml"));
      Parent homeView = loader.load();

      UserMainViewController controller = loader.getController();
      controller.setMainLayout(mainLayout);

      this.mainLayout.setCenter(homeView);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public void showAdminLogin(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(
        getClass().getResource("/org/example/onesteponestamp/javafx/AdminLogIn.fxml"));
    BorderPane adminLoginPain = loader.load();
    mainLayout.getChildren().setAll(adminLoginPain);
  }

  public void gotoHyperlink(ActionEvent event) {
    try {
      java.awt.Desktop.getDesktop()
          .browse(java.net.URI.create("https://github.com/SysWin1/OneStepOneStamp"));
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }
}
