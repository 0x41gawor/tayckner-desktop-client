module pl.gawor.taycknerdesktopclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.sql;


    opens pl.gawor.taycknerdesktopclient to javafx.fxml;
    opens pl.gawor.taycknerdesktopclient.controller.dayplanner to javafx.fxml;
    opens pl.gawor.taycknerdesktopclient.controller.daytracker to javafx.fxml;
    opens pl.gawor.taycknerdesktopclient.controller.habittracker to javafx.fxml;
    opens pl.gawor.taycknerdesktopclient.controller.Observer to javafx.fxml;
    exports pl.gawor.taycknerdesktopclient;
}