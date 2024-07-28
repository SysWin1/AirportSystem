package org.example.onesteponestamp.javafx;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.beans.value.ObservableValue;
import javafx.beans.value.ChangeListener;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;
import org.example.onesteponestamp.immigration.ImmigrationDAO;
import org.example.onesteponestamp.immigration.ImmigrationDTO;

public class ImmigrationListForm {

  private GridPane form;
  private ListView<ImmigrationDTO> listView;
  private ObservableList<ImmigrationDTO> data = FXCollections.observableArrayList();
  private ImmigrationDAO immigrationDAO = new ImmigrationDAO();

  private Button previousButton;
  private Button nextButton;
  private Label pageInfoLabel;
  private int currentPage = 1; // 현재페이지
  private int dataPerPage = 20;

  private ToggleGroup nationalGroup;
  private ToggleGroup entryExitGroup;
  private DatePicker datePicker;

  public ImmigrationListForm() {
    createForm();
    addFilters();
    filterData(); // Initialize data
  }

  private void createForm() {
    form = new GridPane();
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
    allNational.setSelected(true); //디폴트값 설정

    entryExitGroup = new ToggleGroup();
    RadioButton all = new RadioButton("전체");
    RadioButton in = new RadioButton("입국");
    RadioButton out = new RadioButton("출국");

    all.setToggleGroup(entryExitGroup);
    in.setToggleGroup(entryExitGroup);
    out.setToggleGroup(entryExitGroup);
    all.setSelected(true);//디폴트값 설정

    datePicker = new DatePicker(LocalDate.now()); //기본값 시스템 날짜 기준

    form.add(allNational, 0, 0);
    form.add(korean, 1, 0);
    form.add(foreign, 2, 0);
    form.add(all, 0, 1);
    form.add(in, 1, 1);
    form.add(out, 2, 1);
    form.add(datePicker, 3, 1);

    previousButton = new Button("이전");
    nextButton = new Button("다음");
    pageInfoLabel = new Label("1 페이지");

    previousButton.setOnAction(e -> changePage(-1));
    nextButton.setOnAction(e -> changePage(1));

    HBox pagingBox = new HBox(10);
    pagingBox.setPadding(new Insets(10, 10, 10, 10));
    pagingBox.getChildren().addAll(previousButton, pageInfoLabel, nextButton);

    listView = new ListView<>();
    listView.setItems(data);
    listView.setCellFactory(param -> new ListCell<>() {
      @Override
      protected void updateItem(ImmigrationDTO item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
          setText(null);
        } else {
          setText("신청 번호: " + item.getApplyNo() +
              ", 여권 번호: " + item.getPassportNo() +
              ", 국가 코드: " + item.getCountryCode() +
              ", 입/출국: " + item.getInOut() +
              ", 입/출국일: " + item.getInOutDate() +
              ", 비자 유형: " + item.getVisaType() +
              ", 입출국 국가: " + item.getInOutCountry());
        }
      }
    });

    GridPane.setVgrow(listView, Priority.ALWAYS);
    form.add(listView, 0, 2, 7, 1);
    form.add(pagingBox, 0, 3, 7, 1);
  }

  // 필터 리스너 설정
  private void addFilters() {
    ChangeListener<Object> filterChangeListener = (ObservableValue<?> observable, Object oldValue, Object newValue) -> {
      filterData(); // 필터 변경 시 필터 데이터 메서드 호출
    };

    nationalGroup.selectedToggleProperty().addListener(filterChangeListener);
    entryExitGroup.selectedToggleProperty().addListener(filterChangeListener);
    datePicker.valueProperty().addListener(filterChangeListener);
  }

  // 필터 데이터 메서드
  private void filterData() {
    RadioButton selectedNationality = (RadioButton) nationalGroup.getSelectedToggle();
    RadioButton selectedEntryExit = (RadioButton) entryExitGroup.getSelectedToggle();
    LocalDate selectedDate = datePicker.getValue();

    String countryCode = selectedNationality.getText().equals("내국인") ? "KOR" : selectedNationality.getText().equals("외국인") ? "FOREIGNER" : "";
    String inOut = selectedEntryExit.getText().equals("입국") ? "IN" : selectedEntryExit.getText().equals("출국") ? "OUT" : "";

    List<ImmigrationDTO> results = immigrationDAO.ImmigrationListSearch(countryCode, selectedDate, inOut);
    data.setAll(results);
  }

  // 페이지 변경 메서드
  private void changePage(int delta) {
    currentPage += delta;
    if (currentPage < 1) {
      currentPage = 1;
    }
    updatePageInfo();
    filterData();
  }

  private void updatePageInfo() {
    pageInfoLabel.setText("페이지 " + currentPage);
  }

  public GridPane getForm() {
    return form;
  }
}