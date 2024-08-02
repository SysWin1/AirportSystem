package org.example.onesteponestamp.javafx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import org.example.onesteponestamp.autoapply.AutoApply;
import org.example.onesteponestamp.autoapply.AutoApplyService;

public class AutoApplyListController {

  private final AutoApplyService autoApplyService;

  @FXML
  private BorderPane pane;

  @FXML
  private TextField searchField;

  @FXML
  private DatePicker datePicker;

  @FXML
  private ToggleGroup personGroup;

  @FXML
  private ToggleGroup entryExitGroup;

  @FXML
  private TableView<AutoApply> view;

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

  // 기본 생성자 추가
  public AutoApplyListController() {
    this.autoApplyService = new AutoApplyService();
  }

  // 매개변수 있는 생성자 (테스트나 다른 용도로 사용할 수 있음)
  public AutoApplyListController(AutoApplyService autoApplyService) {
    this.autoApplyService = autoApplyService;
  }

  @FXML
  public void initialize() {
    // Initialize ToggleGroups
    personGroup = new ToggleGroup();
    entryExitGroup = new ToggleGroup();

    allPersons.setToggleGroup(personGroup);
    locals.setToggleGroup(personGroup);
    foreigners.setToggleGroup(personGroup);

    allEntries.setToggleGroup(entryExitGroup);
    exits.setToggleGroup(entryExitGroup);
    entries.setToggleGroup(entryExitGroup);

    datePicker.setValue(LocalDate.now());

    // Initialize Table Columns
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

  private String getPersonToggle() {
    RadioButton selectedPerson = (RadioButton) personGroup.getSelectedToggle();
    String personToggle = selectedPerson.getText();
    return personToggle.equals("내국인") ? "LOCAL" :
        personToggle.equals("외국인") ? "FOREIGNER" : null;
  }

  private String getInOutToggle() {
    RadioButton selectedEntry = (RadioButton) entryExitGroup.getSelectedToggle();
    String entryToggle = selectedEntry.getText();
    return entryToggle.equals("출국") ? "OUT" :
        entryToggle.equals("입국") ? "IN" : null;
  }

  public void setItems(ActionEvent event) {
    List<AutoApply> autoApplyList = autoApplyService.getAutoApplicationsForAdmin(
        getPersonToggle(), getInOutToggle(), datePicker.getValue(), searchField.getText()
    );

    ObservableList<AutoApply> data = FXCollections.observableArrayList(autoApplyList);
    view.setItems(data);
  }
}