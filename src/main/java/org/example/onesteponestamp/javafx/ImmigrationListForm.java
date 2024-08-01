package org.example.onesteponestamp.javafx;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;
import java.time.LocalDate;
import java.util.List;
import org.example.onesteponestamp.immigration.ImmigrationDAO;
import org.example.onesteponestamp.immigration.ImmigrationDTO;

public class ImmigrationListForm {

  private GridPane form;
  private TableView<ImmigrationDTO> view;
  private ObservableList<ImmigrationDTO> data = FXCollections.observableArrayList();
  private ImmigrationDAO immigrationDAO = new ImmigrationDAO();
  private static final int ROWS_PER_PAGE = 20;

  private Pagination pagination;
  private ToggleGroup nationalGroup;
  private ToggleGroup entryExitGroup;
  private DatePicker datePicker;
  private Label totalLabel;

  public ImmigrationListForm() {
    createForm();
    addFilters();
    filterData();
  }

  private void createForm() {
    form = new GridPane();
    form.getStylesheets().add(
        getClass().getResource("/org/example/onesteponestamp/css/immigrationListStyles.css")
            .toExternalForm());
    form.setPadding(new Insets(10));
    form.setHgap(10);
    form.setVgap(10);

    nationalGroup = new ToggleGroup();
    RadioButton allNational = new RadioButton("전체");
    RadioButton korean = new RadioButton("내국인");
    RadioButton foreign = new RadioButton("외국인");

    allNational.setToggleGroup(nationalGroup);
    korean.setToggleGroup(nationalGroup);
    foreign.setToggleGroup(nationalGroup);
    allNational.setSelected(true); // 디폴트값 설정

    entryExitGroup = new ToggleGroup();
    RadioButton all = new RadioButton("전체");
    RadioButton in = new RadioButton("입국   ");
    RadioButton out = new RadioButton("출국");

    all.setToggleGroup(entryExitGroup);
    in.setToggleGroup(entryExitGroup);
    out.setToggleGroup(entryExitGroup);
    all.setSelected(true); // 디폴트값 설정

    datePicker = new DatePicker(LocalDate.now()); // 기본값 시스템 날짜 기준

    VBox allFilterBox = new VBox(5, allNational, all);
    VBox firstFilterBox = new VBox(5, korean, in);
    VBox secFilterBox = new VBox(5, foreign, out);
    Region space = new Region();
    space.getStyleClass().add("space");
    VBox dateFilterBox = new VBox(5, space, datePicker);
    HBox filterBox = new HBox(50, allFilterBox, firstFilterBox, secFilterBox, dateFilterBox);

    form.add(filterBox, 0, 0);

    view = new TableView<>();
    view.setItems(data);

    TableColumn<ImmigrationDTO, Integer> idx = new TableColumn<>("Idx");
    idx.setCellValueFactory(new PropertyValueFactory<>("idx"));
    idx.setPrefWidth(40);

    TableColumn<ImmigrationDTO, String> applyNo = new TableColumn<>("신청 번호");
    applyNo.setCellValueFactory(new PropertyValueFactory<>("applyNo"));
    applyNo.setPrefWidth(80);

    TableColumn<ImmigrationDTO, String> passportNo = new TableColumn<>("여권 번호");
    passportNo.setCellValueFactory(new PropertyValueFactory<>("passportNo"));
    passportNo.setPrefWidth(80);

    TableColumn<ImmigrationDTO, String> countryCode = new TableColumn<>("국가 코드");
    countryCode.setCellValueFactory(new PropertyValueFactory<>("countryCode"));
    countryCode.setPrefWidth(70);

    TableColumn<ImmigrationDTO, String> inOut = new TableColumn<>("입/출국");
    inOut.setCellValueFactory(new PropertyValueFactory<>("inOut"));
    inOut.setPrefWidth(65);

    TableColumn<ImmigrationDTO, LocalDate> inOutDate = new TableColumn<>("입/출국일");
    inOutDate.setCellValueFactory(new PropertyValueFactory<>("inOutDate"));
    inOutDate.setPrefWidth(100);

    TableColumn<ImmigrationDTO, String> visaType = new TableColumn<>("비자 유형");
    visaType.setCellValueFactory(new PropertyValueFactory<>("visaType"));
    visaType.setPrefWidth(130);

    TableColumn<ImmigrationDTO, String> inOutCountry = new TableColumn<>("입출국 국가");
    inOutCountry.setCellValueFactory(new PropertyValueFactory<>("inOutCountry"));
    inOutCountry.setPrefWidth(80);

    view.getColumns()
        .addAll(idx, applyNo, passportNo, countryCode, inOut, inOutDate, visaType, inOutCountry);

//    HBox tableBox = new HBox(5, view, filterBox);
    GridPane.setVgrow(view, Priority.ALWAYS);

    form.add(view, 0, 0, 8, 2); // 테이블을 전체 열에 걸쳐 추가
//    form.add(tableBox, 0, 0, 10, 1);

    totalLabel = new Label("총 건수: 0");
    form.add(totalLabel, 0, 4, 2, 1);

    pagination = new Pagination();
    pagination.setPageFactory(this::createPage);// 페이지 생성 메서드 설정

//    HBox paginationBox = new HBox(10);
//    paginationBox.setAlignment(Pos.CENTER);
//    paginationBox.getChildren().addAll(pagination);
    form.add(pagination, 0, 3, 10, 1);


  }

  private void addFilters() {
    ChangeListener<Object> filterChangeListener = (ObservableValue<?> observable, Object oldValue, Object newValue) -> {
      filterData(); // 필터 변경 시 필터 데이터 메서드 호출
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

    String countryCode = selectedNationality.getText().equals("내국인") ? "KOR"
        : selectedNationality.getText().equals("외국인") ? "FOREIGNER" : "";
    String inOut = selectedEntryExit.getText().equals("입국") ? "IN"
        : selectedEntryExit.getText().equals("출국") ? "OUT" : "";

    List<ImmigrationDTO> results = immigrationDAO.ImmigrationListSearch(countryCode, selectedDate,
        inOut);

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

  private VBox createPage(int pageIndex) {
    updatePage(pageIndex);
    VBox pageBox = new VBox(view);
    return pageBox;
  }

  public GridPane getForm() {
    return form;
  }
}
