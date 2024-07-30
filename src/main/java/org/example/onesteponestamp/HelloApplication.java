package org.example.onesteponestamp;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.onesteponestamp.admin.AuthManager;
import org.example.onesteponestamp.immigration.ImmigrationDAO;
import org.example.onesteponestamp.javafx.SelectionMain;


public class HelloApplication extends Application {

  private BorderPane mainLayout;
  private static final ImmigrationDAO immigrationDAO = new ImmigrationDAO();
  private AuthManager authManager;

  @Override
  public void start(Stage stage) {

    stage.setTitle("One Step - One Stamp");

    mainLayout = new BorderPane();
    SelectionMain selectionMain = new SelectionMain(mainLayout);
    authManager = new AuthManager();

    Scene scene = new Scene(mainLayout, 1200, 700);
    stage.setScene(scene);
    stage.show();

    selectionMain.show();
  }

  public static void main(String[] args) {
    launch();

//    입출국 게이트 통과 테스트
//    String test = immigrationDAO.Immigration("A012");
//    System.out.println(test);

//    목록조회 테스트
//    LocalDate today = LocalDate.now();
//    List<ImmigrationDTO> dto = immigrationDAO.ImmigrationListSearch("ALL", today, "IN");
//    for (ImmigrationDTO dto1 : dto) {
//      System.out.println(dto1.getInOut());
//    }
  }
}