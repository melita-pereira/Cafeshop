module com.melita.cafeshop {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires mysql.connector.j;
    requires jasperreports;
    requires org.slf4j;


    opens com.melita.cafeshop to javafx.fxml;
    exports com.melita.cafeshop;
}