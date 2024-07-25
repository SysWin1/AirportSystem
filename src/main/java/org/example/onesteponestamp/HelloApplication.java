package org.example.onesteponestamp;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.example.onesteponestamp.autoapply.AutoApplyService;


public class HelloApplication extends Application {
    private static final AutoApplyService autoApplyService = new AutoApplyService();

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
//        launch();

        /**
         *  신청서 작성 insert
         *  todo 1 : insert 할 때 trigger 넣어서, 심사 승인 trigger 연결 시켜야 함 (-> db에서 수행)
         *  todo 2 : 이후 JavaFX로 로직 연결
         */
//        autoApplyService.createAutoApply(
//            "P0001", Country.GBR, "Emma Watsob", "F",
//            LocalDate.parse("2020/01/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
//            LocalDate.parse("2029/07/25", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
//            LocalDate.parse("1980/01/02", DateTimeFormatter.ofPattern("yyyy/MM/dd")),
//            VisaType.STUDENT_VISA, "IN", Country.GBR,
//            LocalDate.now()
//            );

    }
}