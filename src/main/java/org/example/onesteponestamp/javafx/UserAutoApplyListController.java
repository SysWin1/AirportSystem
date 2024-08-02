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

  @FXML
  private TableColumn<UserAutoApplyDTO, String> applyNo;

  @FXML
  private TableColumn<UserAutoApplyDTO, String> englishName;

  @FXML
  private TableColumn<UserAutoApplyDTO, String> gender;

  @FXML
  private TableColumn<UserAutoApplyDTO, String> visaType;

  @FXML
  private TableColumn<UserAutoApplyDTO, String> inout;

  @FXML
  private TableColumn<UserAutoApplyDTO, String> inoutCountry;

  @FXML
  private TableColumn<UserAutoApplyDTO, LocalDate> expectedInOutDate;

  @FXML
  private TableColumn<UserAutoApplyDTO, String> approvalStatus;

  @FXML
  private TableColumn<UserAutoApplyDTO, String> rejectReason;

  @FXML
  private TableColumn<UserAutoApplyDTO, LocalDateTime> createdAt;

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

    // Set the searchButton's action to call searchTableData when clicked
    searchButton.setOnAction(this::searchTableData);
  }

  private void createTableView() {
    applyNo.setCellValueFactory(new PropertyValueFactory<>("applyNo"));
    englishName.setCellValueFactory(new PropertyValueFactory<>("englishName"));
    gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    visaType.setCellValueFactory(new PropertyValueFactory<>("visaType"));
    inout.setCellValueFactory(new PropertyValueFactory<>("inout"));
    inoutCountry.setCellValueFactory(new PropertyValueFactory<>("inoutCountry"));
    expectedInOutDate.setCellValueFactory(new PropertyValueFactory<>("expectedInOutDate"));
    approvalStatus.setCellValueFactory(new PropertyValueFactory<>("approvalStatus"));
    rejectReason.setCellValueFactory(new PropertyValueFactory<>("rejectReason"));
    createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
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
