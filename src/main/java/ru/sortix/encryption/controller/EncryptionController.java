package ru.sortix.encryption.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import ru.sortix.encryption.EncryptionsApplication;

import java.io.IOException;

public class EncryptionController {

    @FXML
    private StackPane contentPane;

    @FXML
    private Label currentModeLabel;  // Поле для Label

    @FXML
    private void switchToCaesar() {
        loadEncryptionView("view/simple/caesar-panel.fxml");
        currentModeLabel.setText("Шифр Цезаря");
    }

    @FXML
    private void switchToAtbash() {
        loadEncryptionView("view/simple/atbash-panel.fxml");
        currentModeLabel.setText("Шифр Атбаш");
    }

    @FXML
    private void switchToRouteCipher() {
        loadEncryptionView("view/table/route-view.fxml");
        currentModeLabel.setText("Маршрутный шифр");
    }

    @FXML
    private void switchToFleissnerCipher() {
        loadEncryptionView("view/table/grid-view.fxml");
        currentModeLabel.setText("Шифр Флейснера");
    }

    @FXML
    private void switchToVigenereCipher() {
        loadEncryptionView("view/table/vigenere-view.fxml");
        currentModeLabel.setText("Шифр Виженера");
    }

    private void loadEncryptionView(String fxmlFilePath) {
        try {
            FXMLLoader loader = new FXMLLoader(EncryptionsApplication.class.getResource(fxmlFilePath));
            Node encryptionView = loader.load();
            contentPane.getChildren().setAll(encryptionView);
        } catch (IOException e) {
            e.printStackTrace();
            showError("Не удалось загрузить интерфейс шифра.");
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
