package org.example.onesteponestamp.javafx;

import java.time.LocalDate;
import java.util.Arrays;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import lombok.Getter;
import org.example.onesteponestamp.autoapply.AutoApplyService;
import org.example.onesteponestamp.common.Country;
import org.example.onesteponestamp.common.VisaType;

@Getter
public class AutoApplyForm {

  private GridPane form;
  private static final AutoApplyService autoApplyService = new AutoApplyService();

  public AutoApplyForm() {
    createForm();
  }

  private void createForm() {
    form = new GridPane();
    form.setPadding(new Insets(10));
    form.setHgap(10);
    form.setVgap(10);
    form.setAlignment(Pos.CENTER);

    Label titleLabel = new Label("자동 출입국 신청서");
    GridPane.setConstraints(titleLabel, 0, 0, 2, 1);
    titleLabel.setStyle("-fx-font-size: 18px; -fx-font-weight: bold;");

    ToggleGroup inoutGroup = new ToggleGroup();
    RadioButton inButton = new RadioButton("입국");
    inButton.setToggleGroup(inoutGroup);
    RadioButton outButton = new RadioButton("출국");
    outButton.setToggleGroup(inoutGroup);
    outButton.setSelected(true);
    HBox inoutBox = new HBox(10, outButton, inButton);
    GridPane.setConstraints(inoutBox, 1, 1);

    Label inoutLabel = new Label("출국 / 입국");
    GridPane.setConstraints(inoutLabel, 0, 1);

    // 필드들
    Label englishNameLabel = new Label("영문명");
    TextField englishNameInput = new TextField();
    GridPane.setConstraints(englishNameLabel, 0, 2);
    GridPane.setConstraints(englishNameInput, 1, 2);

    Label passportNoLabel = new Label("여권번호");
    TextField passportNoInput = new TextField();
    GridPane.setConstraints(passportNoLabel, 2, 2);
    GridPane.setConstraints(passportNoInput, 3, 2);

    Label countryCodeLabel = new Label("국적");
    ComboBox<String> countryCodeInput = new ComboBox<>();
    countryCodeInput.getItems()
        .addAll(Arrays.stream(Country.values()).map(Country::getCountryName).toList()); // 나라 이름 목록
    countryCodeInput.setValue(Country.KOR.getCountryName()); // 기본값 설정
    GridPane.setConstraints(countryCodeLabel, 0, 3);
    GridPane.setConstraints(countryCodeInput, 1, 3);

    Label birthLabel = new Label("생년월일");
    DatePicker birthInput = new DatePicker();
    GridPane.setConstraints(birthLabel, 2, 3);
    GridPane.setConstraints(birthInput, 3, 3);

    Label issueDateLabel = new Label("여권 발급일");
    DatePicker issueDateInput = new DatePicker();
    GridPane.setConstraints(issueDateLabel, 0, 4);
    GridPane.setConstraints(issueDateInput, 1, 4);

    Label expiryDateLabel = new Label("여권 만료일");
    DatePicker expiryDateInput = new DatePicker();
    GridPane.setConstraints(expiryDateLabel, 2, 4);
    GridPane.setConstraints(expiryDateInput, 3, 4);

    Label genderLabel = new Label("성별");
    ComboBox<String> genderInput = new ComboBox<>();
    genderInput.getItems().addAll("남자", "여자");
    GridPane.setConstraints(genderLabel, 0, 5);
    GridPane.setConstraints(genderInput, 1, 5);

    Label visaTypeLabel = new Label("비자 선택");
    ComboBox<String> visaTypeInput = new ComboBox<>();
    visaTypeInput.getItems().addAll("비자없음", "여행 비자", "취업 비자", "학생 비자"); // 비자 종류
    visaTypeInput.setValue("비자없음");
    GridPane.setConstraints(visaTypeLabel, 2, 5);
    GridPane.setConstraints(visaTypeInput, 3, 5);

    Label expectedInoutDateLabel = new Label("입출국 예상 일자");
    DatePicker expectedInoutDateInput = new DatePicker();
    expectedInoutDateInput.setValue(LocalDate.now());
    GridPane.setConstraints(expectedInoutDateLabel, 0, 6);
    GridPane.setConstraints(expectedInoutDateInput, 1, 6);

    Label inoutCountryLabel = new Label("입출국 국가");
    ComboBox<String> inoutCountryInput = new ComboBox<>();
    inoutCountryInput.getItems()
        .addAll(Arrays.stream(Country.values()).map(Country::getCountryName).toList()); // 나라 이름 목록
    GridPane.setConstraints(inoutCountryLabel, 2, 6);
    GridPane.setConstraints(inoutCountryInput, 3, 6);

    Button submitButton = new Button("제출");
    GridPane.setConstraints(submitButton, 1, 7);

    form.getChildren().addAll(
        titleLabel, inoutLabel, inoutBox,
        englishNameLabel, englishNameInput, passportNoLabel, passportNoInput,
        countryCodeLabel, countryCodeInput, birthLabel, birthInput,
        issueDateLabel, issueDateInput, expiryDateLabel, expiryDateInput,
        genderLabel, genderInput, visaTypeLabel, visaTypeInput,
        expectedInoutDateLabel, expectedInoutDateInput, inoutCountryLabel, inoutCountryInput,
        submitButton
    );

    // 이벤트 핸들러
    submitButton.setOnAction(e -> {
      try {
        // 데이터 검증
        validateInputs(passportNoInput.getText(), countryCodeInput.getValue(),
            englishNameInput.getText(),
            genderInput.getValue(), issueDateInput.getValue(), expiryDateInput.getValue(),
            birthInput.getValue(), visaTypeInput.getValue(), inoutCountryInput.getValue(),
            expectedInoutDateInput.getValue());

        // 데이터베이스에 데이터 삽입 로직 추가
        String passportNo = passportNoInput.getText();
        Country country = Country.getCountry(countryCodeInput.getValue());
        String englishName = englishNameInput.getText();
        String gender = genderInput.getValue().equals("여자") ? "F" : "M";
        LocalDate issueDate = issueDateInput.getValue();
        LocalDate expiryDate = expiryDateInput.getValue();
        LocalDate birth = birthInput.getValue();
        VisaType visaType = VisaType.getVisaType(visaTypeInput.getValue());
        String inout = inButton.isSelected() ? "IN" : "OUT";
        Country inoutCountry = Country.getCountry(inoutCountryInput.getValue());
        LocalDate expectedInoutDate = expectedInoutDateInput.getValue();

        // DB 연결 및 데이터 삽입
        String applyNo = autoApplyService.createAutoApply(
            passportNo, country, englishName, gender, issueDate, expiryDate, birth, visaType, inout,
            inoutCountry, expectedInoutDate
        );

        // DB 작업 성공 시 팝업 표시
        showPopup("신청 완료!", "자동 출입국 신청 완료되었습니다.\n\n 신청 번호 : " + applyNo);

      } catch (Exception ex) {
        // DB 작업 중 오류 발생 시 팝업 표시
        showPopup("신청 실패", ex.getMessage());
      }
    });
  }

  private void validateInputs(String passportNo, String country, String englishName, String gender,
      LocalDate issueDate, LocalDate expiryDate, LocalDate birth, String visaType,
      String inoutCountry, LocalDate expectedInoutDate) throws Exception {
    if (englishName == null || englishName.trim().isEmpty()) {
      throw new Exception("영문명을 입력해주세요.");
    }
    if (!englishName.matches("^[a-zA-Z ]+$")) {
      throw new Exception("영문명은 영어 알파벳만 입력해주세요.");
    }
    if (englishName.length() > 30) {
      throw new Exception("영문명은 30자를 넘을 수 없습니다.");
    }

    if (passportNo == null || passportNo.trim().isEmpty()) {
      throw new Exception("여권번호를 입력해주세요.");
    }
    if (passportNo.length() < 8 || passportNo.length() > 10) {
      throw new Exception("여권번호는 8-10자만 입력 가능합니다.");
    }
    if (!passportNo.matches("^[A-Z][0-9A-Z]{7,9}$")) {
      throw new Exception("여권번호 형식이 올바르지 않습니다. (대문자 알파벳로 시작");
    }

    if (country == null || country.trim().isEmpty()) {
      throw new Exception("국적을 선택해주세요.");
    }
    if (birth == null) {
      throw new Exception("생년월일을 선택해주세요.");
    }
    if (issueDate == null) {
      throw new Exception("여권 발급일을 선택해주세요.");
    }
    if (expiryDate == null) {
      throw new Exception("여권 만료일을 선택해주세요.");
    }
    if (gender == null || gender.trim().isEmpty()) {
      throw new Exception("성별을 선택해주세요.");
    }
    if (visaType == null || visaType.trim().isEmpty()) {
      throw new Exception("비자 종류를 선택해주세요.");
    }
    if (expectedInoutDate == null) {
      throw new Exception("입출국 예상 일자를 선택해주세요.");
    }
    if (inoutCountry == null || inoutCountry.trim().isEmpty()) {
      throw new Exception("입출국 국가를 선택해주세요.");
    }
  }

  public GridPane getForm() {
    return form;
  }

  private void showPopup(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();

    // todo : 팝업이 닫힌 후 다른 페이지로 이동
  }
}
