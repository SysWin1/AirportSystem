package org.example.onesteponestamp.javafx;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import org.example.onesteponestamp.autoapply.UserAutoApplyDTO;
import org.example.onesteponestamp.autoapply.AutoApplyService;
import org.example.onesteponestamp.common.Country;
import org.example.onesteponestamp.common.VisaType;

public class AutoApplyListView {

  private TextField passportNoInput;
  private ComboBox<String> countryNameInput;

  @Getter
  private TableView<UserAutoApplyDTO> tableView;
  private AutoApplyService autoApplyService;

  public AutoApplyListView() {
    autoApplyService = new AutoApplyService();
    createTableView();
  }

  /**
   * 자동 출입국 신청 결과 목록 조회 -> Table 형식으로 보여줌
   * todo : table형식 -> list형식으로 변경 예정.
   */
  private void createTableView() {
    tableView = new TableView<>();
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

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

    TableColumn<UserAutoApplyDTO, LocalDate> expectedInOutDateColumn = new TableColumn<>("입출국 예상 일자");
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

  /**
   * (여권번호, 국가명) 사용해서 자동 입출국 신청서 목록 조회.
   */
  private void loadTableData(String passportNo, String countryName) {
    List<UserAutoApplyDTO> applyList = autoApplyService.getAutoApplications(passportNo, countryName);

    tableView.setItems(
        FXCollections.observableArrayList(applyList)
    );
  }

  /**
   * 사용자에게 여권번호+국가명 입력받는 form.
   */
  public GridPane getForm() {
    GridPane form = new GridPane();
    form.setHgap(10);
    form.setVgap(10);
    form.setPadding(new Insets(10));

    Label passportNoLabel = new Label("여권번호");
    passportNoInput = new TextField();
    form.add(passportNoLabel, 0, 0);
    form.add(passportNoInput, 1, 0);

    Label countryCodeLabel = new Label("국적");
    countryNameInput = new ComboBox<>();
    countryNameInput.getItems()
        .addAll(Arrays.stream(Country.values()).map(Country::getCountryName).toList());
    countryNameInput.setValue(Country.KOR.getCountryName());
    form.add(countryCodeLabel, 2, 0);
    form.add(countryNameInput, 3, 0);

    Button searchButton = new Button("조회");
    searchButton.setOnAction(
        e -> loadTableData(passportNoInput.getText(), countryNameInput.getValue()));
    form.add(searchButton, 4, 0);

    return form;
  }
}
