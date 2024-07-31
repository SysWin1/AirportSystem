package org.example.onesteponestamp;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.example.onesteponestamp.admin.AuthManager;
import org.example.onesteponestamp.immigration.ImmigrationDAO;
import org.example.onesteponestamp.javafx.MainViewController;


public class HelloApplication extends Application {

  private BorderPane mainLayout;
  private static final ImmigrationDAO immigrationDAO = new ImmigrationDAO();
  private AuthManager authManager;

  @Override
  public void start(Stage stage) {

    stage.setTitle("One Step - One Stamp");

//    mainLayout = new BorderPane();
//    SelectionMain selectionMain = new SelectionMain(mainLayout);

    authManager = new AuthManager();
    try {
      // FXML 파일 로드
      FXMLLoader loader = new FXMLLoader(
          getClass().getResource("/org/example/onesteponestamp/UserMainView.fxml"));
      Parent root = loader.load();

      // 컨트롤러 가져오기
      MainViewController controller = loader.getController();
      controller.setMainLayout(mainLayout);

      Scene scene = new Scene(root, 1200, 700);
      stage.setScene(scene);
      stage.show();

    } catch (IOException e) {
      e.printStackTrace();
    }


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