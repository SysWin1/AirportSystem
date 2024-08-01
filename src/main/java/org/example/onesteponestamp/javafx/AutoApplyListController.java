package org.example.onesteponestamp.javafx;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

  private AutoApplyFormViewController autoApplyFormViewController;

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
    allPersons.setToggleGroup(personGroup);
    locals.setToggleGroup(personGroup);
    foreigners.setToggleGroup(personGroup);
    allPersons.setSelected(true);

    entryExitGroup = new ToggleGroup();
    allEntries.setToggleGroup(entryExitGroup);
    exits.setToggleGroup(entryExitGroup);
    entries.setToggleGroup(entryExitGroup);
    allEntries.setSelected(true);

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

}