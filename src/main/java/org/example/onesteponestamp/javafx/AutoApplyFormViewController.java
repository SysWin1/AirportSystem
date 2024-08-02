package org.example.onesteponestamp.javafx;

import java.time.LocalDate;
import java.util.Arrays;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.GridPane;
import org.example.onesteponestamp.autoapply.AutoApplyService;
import org.example.onesteponestamp.common.Country;
import org.example.onesteponestamp.common.VisaType;

public class AutoApplyFormViewController {

  @FXML
  private GridPane form;
  @FXML
  private RadioButton inButton;
  @FXML
  private RadioButton outButton;
  @FXML
  private TextField englishNameInput;
  @FXML
  private TextField passportNoInput;
  @FXML
  private ComboBox<String> countryCodeInput;
  @FXML
  private DatePicker birthInput;
  @FXML
  private DatePicker issueDateInput;
  @FXML
  private DatePicker expiryDateInput;
  @FXML
  private ComboBox<String> genderInput;
  @FXML
  private ComboBox<String> visaTypeInput;
  @FXML
  private DatePicker expectedInoutDateInput;
  @FXML
  private ComboBox<String> inoutCountryInput;
  @FXML
  private Button submitButton;

  private static final AutoApplyService autoApplyService = new AutoApplyService();

  @FXML
  public void initialize() {

    // ToggleGroup 설정
    ToggleGroup inoutGroup = new ToggleGroup();
    inButton.setToggleGroup(inoutGroup);
    outButton.setToggleGroup(inoutGroup);
    outButton.setSelected(true);

    // ComboBox 초기화
    countryCodeInput.getItems()
        .addAll(Arrays.stream(Country.values()).map(Country::getCountryName).toList());
    countryCodeInput.setValue(Country.KOR.getCountryName()); // 기본값 설정

    genderInput.getItems().addAll("남자", "여자");

    visaTypeInput.getItems().addAll("비자없음", "여행 비자", "취업 비자", "학생 비자");
    visaTypeInput.setValue("비자없음");

    inoutCountryInput.getItems()
        .addAll(Arrays.stream(Country.values()).map(Country::getCountryName).toList());
    expectedInoutDateInput.setValue(LocalDate.now());

    // 제출 버튼 이벤트 핸들러
    submitButton.setOnAction(e -> {
      try {
        validateInputs(
            passportNoInput.getText(), countryCodeInput.getValue(), englishNameInput.getText(),
            genderInput.getValue(), issueDateInput.getValue(), expiryDateInput.getValue(),
            birthInput.getValue(), visaTypeInput.getValue(), inoutCountryInput.getValue(),
            expectedInoutDateInput.getValue()
        );

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
        showErrorPopup("신청 실패", ex.getMessage());
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
      throw new Exception("여권번호 형식이 올바르지 않습니다. (대문자 알파벳으로 시작)");
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

  private void showPopup(String title, String message) {
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  private void showErrorPopup(String title, String message) {
    Alert alert = new Alert(AlertType.ERROR);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
