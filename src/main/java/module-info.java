module org.example.a4map {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafaker;
    requires org.xerial.sqlitejdbc;


    opens org.example.a4map to javafx.fxml;
    exports org.example.a4map;
}