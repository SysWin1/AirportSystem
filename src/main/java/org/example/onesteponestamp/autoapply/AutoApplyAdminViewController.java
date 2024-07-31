package org.example.onesteponestamp.autoapply;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;

public class AutoApplyAdminViewController implements Initializable {

  @FXML
  private GridPane mainGrid;

  @FXML
  private TextField searchField;

  @FXML
  private DatePicker datePicker;

  @FXML
  private ToggleGroup personGroup;

  @FXML
  private ToggleGroup entryExitGroup;

  @FXML
  private TableView<AutoApply> tableView;

  @FXML
  private Button searchButton;

  @FXML
  private RadioButton allPersons;

  @FXML
  private RadioButton locals;

  @FXML
  private RadioButton foreigners;

  @FXML
  private RadioButton allEntries;

  @FXML
  private RadioButton exits;

  @FXML
  private RadioButton entries;

  @FXML
  private TableColumn<AutoApply, String> applyNo;

  @FXML
  private TableColumn<AutoApply, String> passportNo;

  @FXML
  private TableColumn<AutoApply, String> countryCode;

  @FXML
  private TableColumn<AutoApply, String> englishName;

  @FXML
  private TableColumn<AutoApply, String> gender;

  @FXML
  private TableColumn<AutoApply, LocalDate> issueDate;

  @FXML
  private TableColumn<AutoApply, LocalDate> expiryDate;

  @FXML
  private TableColumn<AutoApply, LocalDate> birth;

  @FXML
  private TableColumn<AutoApply, String> visaType;

  @FXML
  private TableColumn<AutoApply, String> inout;

  @FXML
  private TableColumn<AutoApply, String> inoutCountry;

  @FXML
  private TableColumn<AutoApply, LocalDate> expectedInOutDate;

  @FXML
  private TableColumn<AutoApply, String> approvalStatus;

  @FXML
  private TableColumn<AutoApply, String> rejectReason;

  @FXML
  private TableColumn<AutoApply, LocalDateTime> createdAt;

  private AutoApplyService autoApplyService;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    autoApplyService = new AutoApplyService();
    allPersons.setSelected(true);
    allEntries.setSelected(true);
    datePicker.setValue(LocalDate.now());
    setupEventHandlers();
    initializeTableColumns();
  }

  private void setupEventHandlers() {
    searchButton.setOnAction(e -> loadListData());
  }

  private void initializeTableColumns() {
    applyNo.setCellValueFactory(new PropertyValueFactory<>("applyNo"));
    passportNo.setCellValueFactory(new PropertyValueFactory<>("passportNo"));
    countryCode.setCellValueFactory(new PropertyValueFactory<>("countryCode"));
    englishName.setCellValueFactory(new PropertyValueFactory<>("englishName"));
    gender.setCellValueFactory(new PropertyValueFactory<>("gender"));
    issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));
    expiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));
    birth.setCellValueFactory(new PropertyValueFactory<>("birth"));
    visaType.setCellValueFactory(new PropertyValueFactory<>("visaType"));
    inout.setCellValueFactory(new PropertyValueFactory<>("inout"));
    inoutCountry.setCellValueFactory(new PropertyValueFactory<>("inoutCountry"));
    expectedInOutDate.setCellValueFactory(new PropertyValueFactory<>("expectedInOutDate"));
    approvalStatus.setCellValueFactory(new PropertyValueFactory<>("approvalStatus"));
    rejectReason.setCellValueFactory(new PropertyValueFactory<>("rejectReason"));
    createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));
  }

  private String getSelectedToggleText(ToggleGroup group) {
    Toggle selectedToggle = group.getSelectedToggle();
    if (selectedToggle != null) {
      return ((RadioButton) selectedToggle).getText();
    }
    return null;
  }

  private String getPersonToggle() {
    String personToggle = getSelectedToggleText(personGroup);
    return "내국인".equals(personToggle) ? "LOCAL" :
        "외국인".equals(personToggle) ? "FOREIGNER" : null;
  }

  private String getInOutToggle() {
    String inoutToggle = getSelectedToggleText(entryExitGroup);
    return "출국".equals(inoutToggle) ? "OUT" :
        "입국".equals(inoutToggle) ? "IN" : null;
  }

  private void loadListData() {
    String personToggle = getPersonToggle();
    String entryExitToggle = getInOutToggle();
    LocalDate searchDate = datePicker.getValue();
    String searchKeyword = searchField.getText();

    List<AutoApply> applyList = autoApplyService.getAutoApplicationsForAdmin(
        personToggle, entryExitToggle, searchDate, searchKeyword
    );
    tableView.setItems(FXCollections.observableArrayList(applyList));
  }
}