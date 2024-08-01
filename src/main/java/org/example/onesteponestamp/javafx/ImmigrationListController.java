package org.example.onesteponestamp.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Region;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.layout.VBox;
import java.time.LocalDate;
import java.util.List;
import org.example.onesteponestamp.immigration.ImmigrationDAO;
import org.example.onesteponestamp.immigration.ImmigrationDTO;

public class ImmigrationListController {

  @FXML
  private TableView<ImmigrationDTO> view;
  @FXML
  private TableColumn<ImmigrationDTO, Integer> idx;
  @FXML
  private TableColumn<ImmigrationDTO, String> applyNo;
  @FXML
  private TableColumn<ImmigrationDTO, String> passportNo;
  @FXML
  private TableColumn<ImmigrationDTO, String> countryCode;
  @FXML
  private TableColumn<ImmigrationDTO, String> inOut;
  @FXML
  private TableColumn<ImmigrationDTO, LocalDate> inOutDate;
  @FXML
  private TableColumn<ImmigrationDTO, String> visaType;
  @FXML
  private TableColumn<ImmigrationDTO, String> inOutCountry;
  @FXML
  private Pagination pagination;
  @FXML
  private Label totalLabel;
  @FXML
  private ToggleGroup nationalGroup;
  @FXML
  private ToggleGroup entryExitGroup;
  @FXML
  private DatePicker datePicker;
  @FXML
  private RadioButton allNational;
  @FXML
  private RadioButton korean;
  @FXML
  private RadioButton foreign;
  @FXML
  private RadioButton all;
  @FXML
  private RadioButton in;
  @FXML
  private RadioButton out;
  @FXML
  private Region space;

  private final ObservableList<ImmigrationDTO> data = FXCollections.observableArrayList();
  private final ImmigrationDAO immigrationDAO = new ImmigrationDAO();
  private static final int ROWS_PER_PAGE = 20;

  @FXML
  public void initialize() {
    System.out.println("초기화");

    // ToggleGroup 설정
    nationalGroup = new ToggleGroup();
    entryExitGroup = new ToggleGroup();

    allNational.setToggleGroup(nationalGroup);
    korean.setToggleGroup(nationalGroup);
    foreign.setToggleGroup(nationalGroup);

    all.setToggleGroup(entryExitGroup);
    in.setToggleGroup(entryExitGroup);
    out.setToggleGroup(entryExitGroup);

    datePicker.setValue(LocalDate.now());

    setupTableView();
    setupPagination();
    addFilters();
    filterData();
  }

  @FXML
  private void setupTableView() {
    idx.setCellValueFactory(new PropertyValueFactory<>("idx"));
    applyNo.setCellValueFactory(new PropertyValueFactory<>("applyNo"));
    passportNo.setCellValueFactory(new PropertyValueFactory<>("passportNo"));
    countryCode.setCellValueFactory(new PropertyValueFactory<>("countryCode"));
    inOut.setCellValueFactory(new PropertyValueFactory<>("inOut"));
    inOutDate.setCellValueFactory(new PropertyValueFactory<>("inOutDate"));
    visaType.setCellValueFactory(new PropertyValueFactory<>("visaType"));
    inOutCountry.setCellValueFactory(new PropertyValueFactory<>("inOutCountry"));

    view.setItems(FXCollections.observableArrayList()); // 초기 데이터 설정
  }

  private void setupPagination() {
    pagination.setPageFactory(this::createPage);
  }

  private VBox createPage(int pageIndex) {
    // 빈 VBox를 생성하여 페이지 내용만 설정
    VBox box = new VBox();
    box.getChildren().add(view);
    updatePage(pageIndex);
    return box;
  }

  private void addFilters() {
    ChangeListener<Object> filterChangeListener = (ObservableValue<?> observable, Object oldValue, Object newValue) -> {
      filterData();
    };

    nationalGroup.selectedToggleProperty().addListener(filterChangeListener);
    entryExitGroup.selectedToggleProperty().addListener(filterChangeListener);
    datePicker.valueProperty().addListener(filterChangeListener);
  }

  private void filterData() {
    updateTableData();
  }

  private void updateTableData() {
    RadioButton selectedNationality = (RadioButton) nationalGroup.getSelectedToggle();
    RadioButton selectedEntryExit = (RadioButton) entryExitGroup.getSelectedToggle();
    LocalDate selectedDate = datePicker.getValue();

    if (selectedNationality == null || selectedEntryExit == null) {
      // 필터가 선택되지 않은 경우 데이터 업데이트를 막음
      return;
    }

    String countryCode = selectedNationality.getText().equals("내국인") ? "KOR"
        : selectedNationality.getText().equals("외국인") ? "FOREIGNER" : "";
    String inOut = selectedEntryExit.getText().equals("입국") ? "IN"
        : selectedEntryExit.getText().equals("출국") ? "OUT" : "";

    List<ImmigrationDTO> results = immigrationDAO.ImmigrationListSearch(countryCode, selectedDate, inOut);

    data.setAll(results);
    pagination.setPageCount((results.size() / ROWS_PER_PAGE) + 1);
    totalLabel.setText("총 건수: " + results.size());

    updatePage(0); // 페이지 초기화
  }

  private void updatePage(int pageIndex) {
    pagination.setCurrentPageIndex(pageIndex);
    view.setItems(getPageData(pageIndex));
  }

  private ObservableList<ImmigrationDTO> getPageData(int pageIndex) {
    int fromIndex = pageIndex * ROWS_PER_PAGE;
    int toIndex = Math.min(fromIndex + ROWS_PER_PAGE, data.size());
    return FXCollections.observableArrayList(data.subList(fromIndex, toIndex));
  }
}