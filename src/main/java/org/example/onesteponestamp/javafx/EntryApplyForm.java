package org.example.onesteponestamp.javafx;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import org.example.onesteponestamp.immigration.ImmigrationDAO;

public class EntryApplyForm {

  private static final ImmigrationDAO immigrationDAO = new ImmigrationDAO();
  @Getter
  private GridPane form;
  private TextField applyNoField;

  public EntryApplyForm() {
    createForm();
  }

  private void createForm() {
    form = new GridPane();
    form.setPadding(new Insets(10));
    form.setHgap(10);
    form.setVgap(10);
    form.setAlignment(Pos.CENTER);

    //사전 자동 출입신청번호를 입력
    Label applyNolabel = new Label("자동출입국신청번호");
    GridPane.setConstraints(applyNolabel, 0, 0);
    applyNolabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16px;");

    applyNoField = new TextField();
    GridPane.setConstraints(applyNoField, 1, 0);

    Button submit = new Button("제출");
    GridPane.setConstraints(submit, 2, 0);
    submit.setOnAction(e -> verificationForm(submit));

    form.getChildren().addAll(applyNolabel, applyNoField, submit);
  }

  public String getApplyNo() {
    return applyNoField.getText();
  }

  private void verificationForm(Button submit) {
    //출입국 심사 진행
    Boolean result = immigrationDAO.Immigration(applyNoField.getText());

    if (result) {
      System.out.println(result);
      System.out.println(applyNoField.getText());
      successPopUp();
    } else {
      System.out.println(result);
      System.out.println(applyNoField.getText());
      errorPopUp();
    }
  }

  private void successPopUp() {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText("통과하세요.");
    alert.showAndWait();
  }

  private void errorPopUp() {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("대면 심사로 이동해 주세요");
    alert.showAndWait();
  }
}
