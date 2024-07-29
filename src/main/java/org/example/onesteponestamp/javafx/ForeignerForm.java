package org.example.onesteponestamp.javafx;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import lombok.Getter;
import org.example.onesteponestamp.foreigner.ForeignerDAO;
import org.example.onesteponestamp.foreigner.ForeignerDTO;

@Getter
public class ForeignerForm {

  private GridPane form;
  private TableView<ForeignerDTO> tableView;

  public ForeignerForm() {
    createForm();
  }

  private void createForm() {
    form = new GridPane();
    form.setPadding(new Insets(10));
    form.setHgap(10);
    form.setVgap(10);
    form.setAlignment(Pos.CENTER);

    // 제목
    Label titleLabel = new Label("외국인 조회");
    GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
    titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

    // RadioButton 그룹 설정
    ToggleGroup group = new ToggleGroup();
    RadioButton allForeignersButton = new RadioButton("외국인 조회");
    allForeignersButton.setToggleGroup(group);
    allForeignersButton.setSelected(true);
    RadioButton illegalForeignersButton = new RadioButton("불법 체류자 조회");
    illegalForeignersButton.setToggleGroup(group);

    HBox radioBox = new HBox(10, allForeignersButton, illegalForeignersButton);
    GridPane.setConstraints(radioBox, 1, 1);

    // 테이블뷰 생성
    createTableView();

    GridPane.setConstraints(tableView, 0, 2, 4, 1);

    form.getChildren().addAll(titleLabel, radioBox, tableView);

    // 이벤트 핸들러 설정
    allForeignersButton.setOnAction(e -> loadAllForeigners());
    illegalForeignersButton.setOnAction(e -> loadIllegalForeigners());

    // 초기 데이터 로드
    loadAllForeigners();
  }

  private void createTableView() {
    tableView = new TableView<>();
    tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    TableColumn<ForeignerDTO, String> applicationIdColumn = new TableColumn<>("신청번호");
    applicationIdColumn.setCellValueFactory(new PropertyValueFactory<>("applicationId"));

    TableColumn<ForeignerDTO, String> passportNoColumn = new TableColumn<>("여권번호");
    passportNoColumn.setCellValueFactory(new PropertyValueFactory<>("passportNo"));

    TableColumn<ForeignerDTO, String> countryCodeColumn = new TableColumn<>("국가코드");
    countryCodeColumn.setCellValueFactory(new PropertyValueFactory<>("countryCode"));

    TableColumn<ForeignerDTO, java.sql.Date> visaExpiryColumn = new TableColumn<>("비자 만료일");
    visaExpiryColumn.setCellValueFactory(new PropertyValueFactory<>("visaExpiry"));

    TableColumn<ForeignerDTO, String> departureStatusColumn = new TableColumn<>("출국 상태");
    departureStatusColumn.setCellValueFactory(new PropertyValueFactory<>("departureStatus"));

    TableColumn<ForeignerDTO, String> illegalStayColumn = new TableColumn<>("불법 체류 여부");
    illegalStayColumn.setCellValueFactory(new PropertyValueFactory<>("illegalStay"));

    tableView.getColumns().addAll(applicationIdColumn, passportNoColumn, countryCodeColumn,
        visaExpiryColumn, departureStatusColumn, illegalStayColumn);
  }

  private void loadAllForeigners() {
    try {
      List<ForeignerDTO> foreigners = ForeignerDAO.getAllForeigners();
      tableView.setItems(FXCollections.observableArrayList(foreigners));
    } catch (Exception e) {
      e.printStackTrace();
      showPopup("오류", "외국인 목록을 불러오는 중 오류가 발생했습니다: " + e.getMessage());
    }
  }

  private void loadIllegalForeigners() {
    try {
      List<ForeignerDTO> foreigners = ForeignerDAO.getIllegalForeigners();
      tableView.setItems(FXCollections.observableArrayList(foreigners));
    } catch (Exception e) {
      e.printStackTrace();
      showPopup("오류", "불법 체류자 목록을 불러오는 중 오류가 발생했습니다: " + e.getMessage());
    }
  }


  private void showPopup(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public GridPane getForm() {
    return form;
  }
}
