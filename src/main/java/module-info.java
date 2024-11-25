module ru.sortix.encryption {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.bootstrapfx.core;
    requires org.apache.commons.jexl3;

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
    exports ru.sortix.encryption.controller.gamma;
    opens ru.sortix.encryption.controller.gamma to javafx.fxml;
    exports ru.sortix.encryption.algorithm.gamma;
    opens ru.sortix.encryption.algorithm.gamma to javafx.fxml;
    exports ru.sortix.encryption.controller.euclid;
    opens ru.sortix.encryption.controller.euclid to javafx.fxml;
    exports ru.sortix.encryption.algorithm.euclid;
    opens ru.sortix.encryption.algorithm.euclid to javafx.fxml;
    exports ru.sortix.encryption.algorithm;
    opens ru.sortix.encryption.algorithm to javafx.fxml;
    exports ru.sortix.encryption.controller.probabilistic;
    opens ru.sortix.encryption.controller.probabilistic to javafx.fxml;
    exports ru.sortix.encryption.algorithm.probabilistic;
    opens ru.sortix.encryption.algorithm.probabilistic to javafx.fxml;
    exports ru.sortix.encryption.algorithm.pollard;
    opens ru.sortix.encryption.algorithm.pollard to javafx.fxml;
    exports ru.sortix.encryption.controller.pollard;
    opens ru.sortix.encryption.controller.pollard to javafx.fxml;
    exports ru.sortix.encryption.algorithm.arithmetic;
    opens ru.sortix.encryption.algorithm.arithmetic to javafx.fxml;
    exports ru.sortix.encryption.controller.arithmetic;
    opens ru.sortix.encryption.controller.arithmetic to javafx.fxml;
}