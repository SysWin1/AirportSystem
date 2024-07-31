module org.example.onesteponestamp {
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.web;
  requires javafx.base; // JavaFX Base 모듈을 사용하기 위해 필요 (기본 JavaFX 기능 포함)

  requires org.controlsfx.controls;
  requires com.dlsc.formsfx;
  requires net.synedra.validatorfx;
  requires org.kordamp.ikonli.javafx;
  requires org.kordamp.bootstrapfx.core;
  requires eu.hansolo.tilesfx;
  requires java.sql;
  requires static lombok;
  requires java.desktop;

  opens org.example.onesteponestamp to javafx.fxml;
  opens org.example.onesteponestamp.autoapply to javafx.base; // JavaFX Base 모듈이 org.example.onesteponestamp.autoapply 패키지에 있는 클래스를 리플렉션을 통해 접근할 수 있도록 허용
  opens org.example.onesteponestamp.immigration to javafx.base;
  opens org.example.onesteponestamp.foreigner to javafx.base;
  exports org.example.onesteponestamp;

  opens org.example.onesteponestamp.javafx to javafx.fxml;
}