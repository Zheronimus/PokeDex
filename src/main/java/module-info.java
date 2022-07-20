module com.example.pokedex2 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires com.google.gson;

    opens com.example.pokedex2 to javafx.fxml;
    exports com.example.pokedex2;
}