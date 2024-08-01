package org.example.onesteponestamp.javafx;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import org.example.onesteponestamp.foreigner.ForeignerDAO;
import org.example.onesteponestamp.foreigner.ForeignerDTO;

public class ForeignerFormController {

  @FXML
  private TableView<ForeignerDTO> tableView;
  @FXML
  private RadioButton allForeignersButton;
  @FXML
  private RadioButton illegalForeignersButton;
  @FXML
  private TableColumn<ForeignerDTO, String> applicationIdColumn;
  @FXML
  private TableColumn<ForeignerDTO, String> passportNoColumn;
  @FXML
  private TableColumn<ForeignerDTO, String> countryCodeColumn;
  @FXML
  private TableColumn<ForeignerDTO, java.sql.Date> visaExpiryColumn;
  @FXML
  private TableColumn<ForeignerDTO, String> departureStatusColumn;
  @FXML
  private TableColumn<ForeignerDTO, String> illegalStayColumn;

  @FXML
  private ToggleGroup group;

  @FXML
  public void initialize() {
    // RadioButton 그룹 설정
    group = new ToggleGroup();
    allForeignersButton.setToggleGroup(group);
    illegalForeignersButton.setToggleGroup(group);
    allForeignersButton.setSelected(true);

    // 테이블뷰 생성
    createTableView();

    // 이벤트 핸들러 설정
    allForeignersButton.setOnAction(e -> loadAllForeigners());
    illegalForeignersButton.setOnAction(e -> loadIllegalForeigners());
    loadAllForeigners();
  }

  private void createTableView() {
    applicationIdColumn.setCellValueFactory(new PropertyValueFactory<>("applicationId"));
    passportNoColumn.setCellValueFactory(new PropertyValueFactory<>("passportNo"));
    countryCodeColumn.setCellValueFactory(new PropertyValueFactory<>("countryCode"));
    visaExpiryColumn.setCellValueFactory(new PropertyValueFactory<>("visaExpiry"));
    departureStatusColumn.setCellValueFactory(new PropertyValueFactory<>("departureStatus"));
    illegalStayColumn.setCellValueFactory(new PropertyValueFactory<>("illegalStay"));
  }

  private void loadAllForeigners() {
    try {
      List<ForeignerDTO> foreigners = ForeignerDAO.getAllForeigners();
      tableView.setItems(FXCollections.observableArrayList(foreigners));
    } catch (Exception e) {
      e.printStackTrace();
      showPopup("오류", "외국인 목록을 불러오는 중 오류가 발생했습니다: " + e.getMessage());
    }
  }

  private void loadIllegalForeigners() {
    try {
      List<ForeignerDTO> foreigners = ForeignerDAO.getIllegalForeigners();
      tableView.setItems(FXCollections.observableArrayList(foreigners));
    } catch (Exception e) {
      e.printStackTrace();
      showPopup("오류", "불법 체류자 목록을 불러오는 중 오류가 발생했습니다: " + e.getMessage());
    }
  }

  private void showPopup(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
