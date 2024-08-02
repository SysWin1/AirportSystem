package org.example.onesteponestamp.javafx;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DialogPane;
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

    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
    alertStage.getIcons().add(new Image(
        "file:src/main/resources/org/example/onesteponestamp/images/airplaneIcon.png")); // 타이틀 부분 아이콘 셋팅

    ImageView icon = new ImageView(
        new Image(
            "file:src/main/resources/org/example/onesteponestamp/images/teamlogo.png")); //content부분 아이콘 셋팅
    icon.setFitHeight(100); //아이콘 사이즈 조정
    icon.setFitWidth(100);
    alert.setGraphic(icon);

    DialogPane dialogPane = alert.getDialogPane();
    dialogPane.getStylesheets()
        .add(getClass().getResource("/org/example/onesteponestamp/css/alert.css").toExternalForm());
    dialogPane.getStyleClass().add("alert");

    alert.showAndWait();
  }

  private void errorPopUp() {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText("대면 심사로 이동해 주세요");

    Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
    alertStage.getIcons().add(new Image(
        "file:src/main/resources/org/example/onesteponestamp/images/airplaneIcon.png")); // 타이틀 부분 아이콘 셋팅

    ImageView icon = new ImageView(
        new Image(
            "file:src/main/resources/org/example/onesteponestamp/images/error.png")); //content부분 아이콘 셋팅
    icon.setFitHeight(30); //아이콘 사이즈 조정
    icon.setFitWidth(30);
    alert.setGraphic(icon);

    alert.showAndWait();
  }
}
