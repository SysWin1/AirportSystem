package org.example.onesteponestamp.javafx;

import java.util.Arrays;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import lombok.Getter;
import org.example.onesteponestamp.autoapply.AutoApplyDTO;
import org.example.onesteponestamp.autoapply.AutoApplyService;
import org.example.onesteponestamp.common.Country;

public class AutoApplyListView {

  private TextField passportNoInput;
  private ComboBox<String> countryNameInput;

  @Getter
  private TableView<AutoApplyDTO> tableView;
  private AutoApplyService autoApplyService;

  public AutoApplyListView() {
    autoApplyService = new AutoApplyService();
  }



  /**
   * (여권번호, 국가명) 사용해서 자동 입출국 신청서 목록 조회.
   */
  private void loadTableData(String passportNo, String countryName) {
    List<AutoApplyDTO> applyList = autoApplyService.getAutoApplications(passportNo, countryName);

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
