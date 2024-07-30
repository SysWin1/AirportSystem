package org.example.onesteponestamp.autoapply;

import java.time.LocalDate;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import org.example.onesteponestamp.javafx.AutoApplyAdminView;

public class AutoApplyAdminController {

  private final AutoApplyAdminView view;
  private final AutoApplyService autoApplyService;

  public AutoApplyAdminController(AutoApplyAdminView view) {
    this.autoApplyService = new AutoApplyService();
    this.view = view;
    setupEventHandlers();
  }

  private void setupEventHandlers() {
    view.getSearchButton().setOnAction(e -> loadListData());
  }

  private String getSelectedToggleText(ToggleGroup group) {
    Toggle selectedToggle = group.getSelectedToggle();
    if (selectedToggle != null) {
      return ((RadioButton) selectedToggle).getText();
    }
    return null;
  }

  private String getPersonToggle() {
    String personToggle = getSelectedToggleText(view.getPersonGroup());
    return "내국인".equals(personToggle) ? "LOCAL" :
        "외국인".equals(personToggle) ? "FOREIGNER" : null;
  }

  private String getInOutToggle(){
    String inoutToggle = getSelectedToggleText(view.getEntryExitGroup());
    return "출국".equals(inoutToggle) ? "OUT" :
        "입국".equals(inoutToggle) ? "IN" : null;
  }

  private void loadListData() {
    String personToggle = getPersonToggle();
    String entryExitToggle = getInOutToggle();
    LocalDate searchDate = view.getDatePicker().getValue();
    String searchKeyword = view.getSearchField().getText();

    List<AutoApply> applyList = autoApplyService.getAutoApplicationsForAdmin(
        personToggle, entryExitToggle, searchDate, searchKeyword
    );
    view.getTableView().setItems(FXCollections.observableArrayList(applyList));
  }
}
