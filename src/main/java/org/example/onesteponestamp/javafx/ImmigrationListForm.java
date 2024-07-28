package org.example.onesteponestamp.javafx;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Priority;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
    RadioButton in = new RadioButton("입국");
    RadioButton out = new RadioButton("출국");

    all.setToggleGroup(entryExitGroup);
    in.setToggleGroup(entryExitGroup);
    out.setToggleGroup(entryExitGroup);
    all.setSelected(true); // 디폴트값 설정

    datePicker = new DatePicker(LocalDate.now()); // 기본값 시스템 날짜 기준

    form.add(allNational, 0, 0);
    form.add(korean, 1, 0);
    form.add(foreign, 2, 0);
    form.add(all, 0, 1);
    form.add(in, 1, 1);
    form.add(out, 2, 1);
    form.add(datePicker, 3, 1);

    view = new TableView<>();
    view.setItems(data);

    TableColumn<ImmigrationDTO, String> applyNo = new TableColumn<>("신청 번호");
    applyNo.setCellValueFactory(new PropertyValueFactory<>("applyNo"));

    TableColumn<ImmigrationDTO, String> passportNo = new TableColumn<>("여권 번호");
    passportNo.setCellValueFactory(new PropertyValueFactory<>("passportNo"));

    TableColumn<ImmigrationDTO, String> countryCode = new TableColumn<>("국가 코드");
    countryCode.setCellValueFactory(new PropertyValueFactory<>("countryCode"));

    TableColumn<ImmigrationDTO, String> inOut = new TableColumn<>("입/출국");
    inOut.setCellValueFactory(new PropertyValueFactory<>("inOut"));

    TableColumn<ImmigrationDTO, LocalDate> inOutDate = new TableColumn<>("입/출국일");
    inOutDate.setCellValueFactory(new PropertyValueFactory<>("inOutDate"));

    TableColumn<ImmigrationDTO, String> visaType = new TableColumn<>("비자 유형");
    visaType.setCellValueFactory(new PropertyValueFactory<>("visaType"));

    TableColumn<ImmigrationDTO, String> inOutCountry = new TableColumn<>("입출국 국가");
    inOutCountry.setCellValueFactory(new PropertyValueFactory<>("inOutCountry"));

    view.getColumns()
        .addAll(applyNo, passportNo, countryCode, inOut, inOutDate, visaType, inOutCountry);

    GridPane.setVgrow(view, Priority.ALWAYS);
    form.add(view, 0, 2, 7, 1);

    totalLabel = new Label("총 건수: 0");
    form.add(totalLabel, 0, 3, 7, 1);
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
    totalLabel.setText("총 건수: " + results.size());
  }

  public GridPane getForm() {
    return form;
  }
}
