package ru.sortix.encryption.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import ru.sortix.encryption.EncryptionsApplication;
import ru.sortix.encryption.algorithm.euclid.BinaryEuclidAlgorithm;
import ru.sortix.encryption.algorithm.euclid.ClassicEuclidAlgorithm;
import ru.sortix.encryption.algorithm.euclid.ExtendedBinaryEuclidAlgorithm;
import ru.sortix.encryption.algorithm.euclid.ExtendedEuclidAlgorithm;
import ru.sortix.encryption.controller.euclid.EuclidEncryptionController;

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

    @FXML
    private void switchToGammaCipher() {
        loadEncryptionView("view/gamma/gamma-view.fxml");
        currentModeLabel.setText("Гамма шифр");
    }

    @FXML
    private void switchToClassicEuclid() {
        EuclidEncryptionController controller = (EuclidEncryptionController) loadEncryptionView("view/euclid/euclid-view.fxml");
        if (controller != null) {
            controller.setAlgorithm(new ClassicEuclidAlgorithm());
            currentModeLabel.setText("Классический Евклид");
        }
    }

    // Переключение на бинарный алгоритм Евклида
    @FXML
    private void switchToBinaryEuclid() {
        EuclidEncryptionController controller = (EuclidEncryptionController) loadEncryptionView("view/euclid/euclid-view.fxml");
        if (controller != null) {
            controller.setAlgorithm(new BinaryEuclidAlgorithm());
            currentModeLabel.setText("Бинарный Евклид");
        }
    }

    @FXML
    private void switchToExtendedEuclid() {
        EuclidEncryptionController controller = (EuclidEncryptionController) loadEncryptionView("view/euclid/euclid-view.fxml");
        if (controller != null) {
            controller.setAlgorithm(new ExtendedEuclidAlgorithm());
            currentModeLabel.setText("Расширенный Евклид");
        }
    }


    @FXML
    private void switchToExtendedBinaryEuclid() {
        EuclidEncryptionController controller = (EuclidEncryptionController) loadEncryptionView("view/euclid/euclid-view.fxml");
        if (controller != null) {
            controller.setAlgorithm(new ExtendedBinaryEuclidAlgorithm());
            currentModeLabel.setText("Расширенный бинарный Евклид");
        }
    }

    // Метод для загрузки представления и получения контроллера
    private Object loadEncryptionView(String fxmlFilePath) {
        try {
            FXMLLoader loader = new FXMLLoader(EncryptionsApplication.class.getResource(fxmlFilePath));
            Node encryptionView = loader.load();
            contentPane.getChildren().setAll(encryptionView);
            return loader.getController();  // Возвращаем контроллер загруженного FXML
        } catch (IOException e) {
            e.printStackTrace();
            showError("Не удалось загрузить интерфейс шифра.");
            return null;
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
