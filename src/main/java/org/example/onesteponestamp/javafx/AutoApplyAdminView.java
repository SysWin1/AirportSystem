package org.example.onesteponestamp.javafx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import lombok.Getter;
import org.example.onesteponestamp.autoapply.AutoApply;

/**
 * 관리자 - "자동 출입국 결과 목록" 조회 table view
 */
@Getter
public class AutoApplyAdminView {

  private GridPane mainGrid;

  private TextField searchField;
  private DatePicker datePicker;
  private ToggleGroup personGroup;
  private ToggleGroup entryExitGroup;
  private TableView<AutoApply> tableView;
  private Button searchButton;

  private String personToggle;
  private String entryExitToggle;

  public AutoApplyAdminView() {
    createPane();
  }

  public GridPane createPane() {
    mainGrid = new GridPane();
    mainGrid.setHgap(10);
    mainGrid.setVgap(10);
    mainGrid.setPadding(new Insets(10));

    /**
     * 전체 or 내국인 or 외국인
     */
    RadioButton allPersons = new RadioButton("전체");
    RadioButton locals = new RadioButton("내국인");
    RadioButton foreigners = new RadioButton("외국인");
    personGroup = new ToggleGroup();
    allPersons.setToggleGroup(personGroup);
    locals.setToggleGroup(personGroup);
    foreigners.setToggleGroup(personGroup);

    allPersons.setSelected(true);
    personToggle = null; //기본값 설정

    /**
     * 전체 vs 출국 vs 입국
     */
    RadioButton allEntries = new RadioButton("전체");
    RadioButton exits = new RadioButton("출국");
    RadioButton entries = new RadioButton("입국");
    entryExitGroup = new ToggleGroup();
    allEntries.setToggleGroup(entryExitGroup);
    exits.setToggleGroup(entryExitGroup);
    entries.setToggleGroup(entryExitGroup);

    allEntries.setSelected(true);
    entryExitToggle = null; // 기본값 설정

    datePicker = new DatePicker();
    datePicker.setValue(LocalDate.now());

    searchField = new TextField();
    searchField.setPromptText("검색어");
    searchButton = new Button("조회");

    mainGrid.add(allPersons, 0, 0);
    mainGrid.add(locals, 1, 0);
    mainGrid.add(foreigners, 2, 0);
    mainGrid.add(datePicker, 3, 0);
    mainGrid.add(allEntries, 0, 1);
    mainGrid.add(exits, 1, 1);
    mainGrid.add(entries, 2, 1);
    mainGrid.add(searchField, 3, 1);
    mainGrid.add(searchButton, 4, 1);

    tableView = new TableView<>();

    TableColumn<AutoApply, String> applyNo = new TableColumn<>("신청 번호");
    applyNo.setCellValueFactory(new PropertyValueFactory<>("applyNo"));

    TableColumn<AutoApply, String> passportNo = new TableColumn<>("여권 번호");
    passportNo.setCellValueFactory(new PropertyValueFactory<>("passportNo"));

    TableColumn<AutoApply, String> countryCode = new TableColumn<>("국가 코드");
    countryCode.setCellValueFactory(new PropertyValueFactory<>("countryCode"));

    TableColumn<AutoApply, String> englishName = new TableColumn<>("영문 이름");
    englishName.setCellValueFactory(new PropertyValueFactory<>("englishName"));

    TableColumn<AutoApply, String> gender = new TableColumn<>("성별");
    gender.setCellValueFactory(new PropertyValueFactory<>("gender"));

    TableColumn<AutoApply, LocalDate> issueDate = new TableColumn<>("여권 발급일");
    issueDate.setCellValueFactory(new PropertyValueFactory<>("issueDate"));

    TableColumn<AutoApply, LocalDate> expiryDate = new TableColumn<>("여권 만료일");
    expiryDate.setCellValueFactory(new PropertyValueFactory<>("expiryDate"));

    TableColumn<AutoApply, LocalDate> birth = new TableColumn<>("생년월일");
    birth.setCellValueFactory(new PropertyValueFactory<>("birth"));

    TableColumn<AutoApply, String> visaType = new TableColumn<>("비자 유형");
    visaType.setCellValueFactory(new PropertyValueFactory<>("visaType"));

    TableColumn<AutoApply, String> inout = new TableColumn<>("입/출국");
    inout.setCellValueFactory(new PropertyValueFactory<>("inout"));

    TableColumn<AutoApply, String> inoutCountry = new TableColumn<>("입/출국 국가");
    inoutCountry.setCellValueFactory(new PropertyValueFactory<>("inoutCountry"));

    TableColumn<AutoApply, LocalDate> expectedInOutDate = new TableColumn<>("입/출국 예상일");
    expectedInOutDate.setCellValueFactory(new PropertyValueFactory<>("expectedInOutDate"));

    TableColumn<AutoApply, String> approvalStatus = new TableColumn<>("승인/거부");
    approvalStatus.setCellValueFactory(new PropertyValueFactory<>("approvalStatus"));

    TableColumn<AutoApply, String> rejectReason = new TableColumn<>("거부 사유");
    rejectReason.setCellValueFactory(new PropertyValueFactory<>("rejectReason"));

    TableColumn<AutoApply, LocalDateTime> createdAt = new TableColumn<>("신청 일자");
    createdAt.setCellValueFactory(new PropertyValueFactory<>("createdAt"));

    tableView.getColumns().addAll(
        applyNo, passportNo, countryCode, englishName, gender, issueDate, expiryDate, birth,
        visaType, inout, inoutCountry, expectedInOutDate, approvalStatus, rejectReason, createdAt
    );

    GridPane.setVgrow(tableView, Priority.ALWAYS);
    mainGrid.add(tableView, 0, 2, 7, 1); // ListView spans 5 columns

    return mainGrid;
  }

}
