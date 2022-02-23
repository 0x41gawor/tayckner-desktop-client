module pl.gawor.taycknerdesktopclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens pl.gawor.taycknerdesktopclient to javafx.fxml;
    exports pl.gawor.taycknerdesktopclient;
}