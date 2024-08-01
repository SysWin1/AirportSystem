package org.example.onesteponestamp.javafx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import org.example.onesteponestamp.autoapply.AutoApplyService;
import org.example.onesteponestamp.autoapply.UserAutoApplyDTO;
import org.example.onesteponestamp.common.Country;
import org.example.onesteponestamp.common.VisaType;

public class UserAutoApplyListController {

  @FXML
  private TextField passportNoInput;

  @FXML
  private ComboBox<String> countryNameInput;

  @Getter
  @FXML
  private TableView<UserAutoApplyDTO> tableView;

  @FXML
  private GridPane form;

  private AutoApplyService autoApplyService;

  @FXML
  private Button searchButton;

  public UserAutoApplyListController() {
    autoApplyService = new AutoApplyService();
  }

  @FXML
  public void initialize() {
    createTableView();
    populateCountryComboBox();
  }

  private void createTableView() {
    TableColumn<UserAutoApplyDTO, String> applyNoColumn = new TableColumn<>("신청번호");
    applyNoColumn.setCellValueFactory(new PropertyValueFactory<>("applyNo"));

    TableColumn<UserAutoApplyDTO, String> englishNameColumn = new TableColumn<>("영문명");
    englishNameColumn.setCellValueFactory(new PropertyValueFactory<>("englishName"));

    TableColumn<UserAutoApplyDTO, String> genderColumn = new TableColumn<>("성별");
    genderColumn.setCellValueFactory(new PropertyValueFactory<>("gender"));

    TableColumn<UserAutoApplyDTO, VisaType> visaTypeColumn = new TableColumn<>("비자 종류");
    visaTypeColumn.setCellValueFactory(new PropertyValueFactory<>("visaType"));

    TableColumn<UserAutoApplyDTO, String> inoutColumn = new TableColumn<>("입출국 구분");
    inoutColumn.setCellValueFactory(new PropertyValueFactory<>("inout"));

    TableColumn<UserAutoApplyDTO, Country> inoutCountryColumn = new TableColumn<>("입출국 국가");
    inoutCountryColumn.setCellValueFactory(new PropertyValueFactory<>("inoutCountry"));

    TableColumn<UserAutoApplyDTO, LocalDate> expectedInOutDateColumn = new TableColumn<>(
        "입출국 예상 일자");
    expectedInOutDateColumn.setCellValueFactory(new PropertyValueFactory<>("expectedInOutDate"));

    TableColumn<UserAutoApplyDTO, String> approvalStatusColumn = new TableColumn<>("승인 상태");
    approvalStatusColumn.setCellValueFactory(new PropertyValueFactory<>("approvalStatus"));

    TableColumn<UserAutoApplyDTO, String> rejectReasonColumn = new TableColumn<>("거부 사유");
    rejectReasonColumn.setCellValueFactory(new PropertyValueFactory<>("rejectReason"));

    TableColumn<UserAutoApplyDTO, LocalDateTime> createdAtColumn = new TableColumn<>("신청일자");
    createdAtColumn.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

    tableView.getColumns().addAll(applyNoColumn, englishNameColumn, genderColumn, visaTypeColumn,
        inoutColumn, inoutCountryColumn, expectedInOutDateColumn, approvalStatusColumn,
        rejectReasonColumn, createdAtColumn);
  }

  private void populateCountryComboBox() {
    countryNameInput.getItems()
        .addAll(Arrays.stream(Country.values()).map(Country::getCountryName).toList());
    countryNameInput.setValue(Country.KOR.getCountryName());
  }

  @FXML
  private void searchTableData(ActionEvent event) {
    List<UserAutoApplyDTO> applyList = autoApplyService.getAutoApplications(
        passportNoInput.getText(), countryNameInput.getValue());

    tableView.setItems(
        FXCollections.observableArrayList(applyList)
    );
  }
}
