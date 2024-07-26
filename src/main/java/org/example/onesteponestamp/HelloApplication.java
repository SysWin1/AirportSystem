package org.example.onesteponestamp;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.onesteponestamp.javafx.SelectionMain;
import org.example.onesteponestamp.immigration.ImmigrationDAO;


public class HelloApplication extends Application {

  private BorderPane mainLayout;
  private static final ImmigrationDAO immigrationDAO = new ImmigrationDAO();


  @Override
  public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
//        stage.setTitle("Hello!");
//        stage.setScene(scene);
//        stage.show();

    stage.setTitle("One Step - One Stamp");

    mainLayout = new BorderPane();
    SelectionMain selectionMain = new SelectionMain(mainLayout);

    Scene scene = new Scene(mainLayout, 800, 600);
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