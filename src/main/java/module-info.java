module ru.sortix.encryption {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;

    opens ru.sortix.encryption to javafx.fxml;
    exports ru.sortix.encryption;
    exports ru.sortix.encryption.controller;
    opens ru.sortix.encryption.controller to javafx.fxml;
    exports ru.sortix.encryption.controller.simple;
    opens ru.sortix.encryption.controller.simple to javafx.fxml;
    exports ru.sortix.encryption.algorithm.simple;
    opens ru.sortix.encryption.algorithm.simple to javafx.fxml;
    exports ru.sortix.encryption.controller.table;
    opens ru.sortix.encryption.controller.table to javafx.fxml;
    exports ru.sortix.encryption.algorithm.table;
    opens ru.sortix.encryption.algorithm.table to javafx.fxml;
}