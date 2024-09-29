package ru.sortix.encryption.controller.table;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.sortix.encryption.algorithm.table.VigenereCipher;

public class VigenereCipherController {

    @FXML
    private TextField messageField;

    @FXML
    private TextField keyField;

    @FXML
    private GridPane vigenereTable;

    @FXML
    private TextArea resultTextArea;

    private VigenereCipher vigenereCipher;

    @FXML
    private void initialize() {
        vigenereCipher = new VigenereCipher(); // создаем объект шифра
    }

    @FXML
    private void encrypt() {
        String message = messageField.getText().trim().toUpperCase();
        String key = keyField.getText().trim().toUpperCase();

        // Проверка: оба поля должны быть заполнены
        if (message.isEmpty() || key.isEmpty()) {
            showAlert("Ошибка", "Поля сообщения и пароля должны быть заполнены.");
            return;
        }

        // Проверка: длина ключа не должна превышать длину сообщения
        if (key.length() > message.length()) {
            showAlert("Ошибка", "Длина пароля не может превышать длину сообщения.");
            return;
        }

        vigenereCipher.setKey(key);

        // Очистка предыдущей таблицы
        vigenereTable.getChildren().clear();

        // Создаем таблицу с сообщением и повторяющимся ключом
        String repeatedKey = vigenereCipher.generateRepeatedKey(message);
        fillTable(message, repeatedKey);

        // Шифруем сообщение
        String result = vigenereCipher.encrypt(message);
        resultTextArea.setText(result);
    }

    @FXML
    private void decrypt() {
        String message = messageField.getText().trim().toUpperCase();
        String key = keyField.getText().trim().toUpperCase();

        // Проверка: оба поля должны быть заполнены
        if (message.isEmpty() || key.isEmpty()) {
            showAlert("Ошибка", "Поля сообщения и пароля должны быть заполнены.");
            return;
        }

        // Проверка: длина ключа не должна превышать длину сообщения
        if (key.length() > message.length()) {
            showAlert("Ошибка", "Длина пароля не может превышать длину сообщения.");
            return;
        }

        vigenereCipher.setKey(key);

        // Очистка предыдущей таблицы
        vigenereTable.getChildren().clear();

        // Создаем таблицу с сообщением и повторяющимся ключом
        String repeatedKey = vigenereCipher.generateRepeatedKey(message);
        fillTable(message, repeatedKey);

        // Дешифруем сообщение
        String result = vigenereCipher.decrypt(message);
        resultTextArea.setText(result);
    }

    // Метод для отображения предупреждений
    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void fillTable(String message, String repeatedKey) {
        for (int i = 0; i < message.length(); i++) {
            // Добавляем символ сообщения
            Label messageLabel = new Label(String.valueOf(message.charAt(i)));
            vigenereTable.add(messageLabel, i, 0);

            // Добавляем символ ключа
            Label keyLabel = new Label(String.valueOf(repeatedKey.charAt(i)));
            vigenereTable.add(keyLabel, i, 1);
        }
    }
}
