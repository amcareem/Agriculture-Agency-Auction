module com.example.auction {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.xerial.sqlitejdbc;

    opens agriculture to javafx.fxml;
    exports agriculture;
    exports agriculture.Users;
    opens agriculture.Users to javafx.fxml;
    exports agriculture.Controllers;
    opens agriculture.Controllers to javafx.fxml;
}