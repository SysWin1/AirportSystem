package org.example.onesteponestamp.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.example.onesteponestamp.immigration.ImmigrationDAO;

public class ImmigrationFormController {

  private static final ImmigrationDAO immigrationDAO = new ImmigrationDAO();

  @FXML
  private GridPane form;

  @FXML
  private TextField applyNoField;

  @FXML
  private void verificationForm() {
    // 출입국 심사 진행
    String result = immigrationDAO.Immigration(applyNoField.getText());

    if (result != null) {
      successPopUp(result);
    } else {
      errorPopUp();
    }
  }

  private void successPopUp(String result) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Success");
    alert.setHeaderText(null);
    alert.setContentText(result);

    ImageView icon = new ImageView(new Image(
        "file:src/main/resources/org/example/onesteponestamp/images/airplaneIcon.png")); // content 부분 아이콘 셋팅
    icon.setFitHeight(50); // 아이콘 사이즈 조정
    icon.setFitWidth(50);
    alert.setGraphic(icon);

    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
    alertStage.getIcons().add(new Image(
        "file:src/main/resources/org/example/onesteponestamp/images/airplaneIcon.png")); // 타이틀 부분 아이콘 셋팅
    alert.showAndWait();
  }

  private void errorPopUp() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("대면 심사로 이동해 주세요");
    alert.showAndWait();
  }
}
