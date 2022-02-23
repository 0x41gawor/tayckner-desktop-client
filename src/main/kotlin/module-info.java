module pl.gawor.taycknerdesktopclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.sql;


    opens pl.gawor.taycknerdesktopclient to javafx.fxml;
    opens pl.gawor.taycknerdesktopclient.controller to javafx.fxml;
    exports pl.gawor.taycknerdesktopclient;
}