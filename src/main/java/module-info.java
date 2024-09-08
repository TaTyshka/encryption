module ru.sortix.encriptions {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens ru.sortix.encryption to javafx.fxml;
    exports ru.sortix.encryption;
}