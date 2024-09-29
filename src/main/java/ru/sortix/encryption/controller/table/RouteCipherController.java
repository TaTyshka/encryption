package ru.sortix.encryption.controller.table;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import ru.sortix.encryption.algorithm.table.RouteAlgorithm;

public class RouteCipherController {

    @FXML
    public TextField rowsField;

    @FXML
    public TextField colsField;

    @FXML
    public TextField passwordField;

    @FXML
    public TextArea inputTextArea;

    @FXML
    public TextArea resultTextArea;

    @FXML
    public GridPane inputGrid;

    @FXML
    public GridPane outputGrid;

    public final RouteAlgorithm routeAlgorithm = new RouteAlgorithm(2, 2); // Размер по умолчанию 2x2

    @FXML
    public void initialize() {
        // Установка значений по умолчанию
        rowsField.setText("2");
        colsField.setText("2");

        // Ограничение полей для ввода только цифр и значений от 2 до 10
        setNumericFieldConstraints(rowsField, 2, 10);
        setNumericFieldConstraints(colsField, 2, 10);

        updateInputGrid();
        // Слушатели для обновления таблиц при изменении текста или размера таблицы
        inputTextArea.textProperty().addListener((observable, oldValue, newValue) -> updateInputGrid());
        rowsField.textProperty().addListener((observable, oldValue, newValue) -> updateInputGrid());
        colsField.textProperty().addListener((observable, oldValue, newValue) -> updateInputGrid());
    }

    @FXML
    public void encrypt() {
        if (!validateInput()) {
            return; // Если валидация не пройдена, прерываем процесс
        }

        int m = Integer.parseInt(rowsField.getText());
        int n = Integer.parseInt(colsField.getText());
        String text = inputTextArea.getText();
        String password = passwordField.getText();

        routeAlgorithm.setSize(m, n);
        routeAlgorithm.setPassword(password);
        String encryptedText = routeAlgorithm.encrypt(text);
        resultTextArea.setText(encryptedText);
        updateOutputGrid(encryptedText);
    }

    @FXML
    public void decrypt() {
        if (!validateInput()) {
            return; // Если валидация не пройдена, прерываем процесс
        }

        int m = Integer.parseInt(rowsField.getText());
        int n = Integer.parseInt(colsField.getText());
        String encryptedText = inputTextArea.getText();
        String password = passwordField.getText();

        routeAlgorithm.setSize(m, n);
        routeAlgorithm.setPassword(password);
        String decryptedText = routeAlgorithm.decrypt(encryptedText);
        resultTextArea.setText(decryptedText);
        updateOutputGrid(decryptedText);
    }

    private void updateInputGrid() {
        // Проверяем, пусты ли поля, и устанавливаем значение по умолчанию (2) при необходимости
        String rowsText = rowsField.getText().isEmpty() ? "2" : rowsField.getText();
        String colsText = colsField.getText().isEmpty() ? "2" : colsField.getText();

        try {
            int m = Integer.parseInt(rowsText);
            int n = Integer.parseInt(colsText);
            String text = inputTextArea.getText();
            updateGrid(inputGrid, text, m, n);
        } catch (NumberFormatException e) {
            showError("Некорректные значения m или n.");
        }
    }

    private void updateOutputGrid(String text) {
        int m = Integer.parseInt(rowsField.getText());
        int n = Integer.parseInt(colsField.getText());
        updateGrid(outputGrid, text, m, n);
    }

    private void updateGrid(GridPane grid, String text, int rows, int cols) {
        grid.getChildren().clear();
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                TextField cell = new TextField();
                cell.setText(index < text.length() ? String.valueOf(text.charAt(index)) : "");
                cell.setEditable(false);
                cell.setMaxWidth(30);
                grid.add(cell, j, i);
                index++;
            }
        }
    }

    // Метод для валидации входных данных
    private boolean validateInput() {
        try {
            int m = Integer.parseInt(rowsField.getText());
            int n = Integer.parseInt(colsField.getText());
            String password = passwordField.getText();
            String text = inputTextArea.getText();

            if (m < 2 || m > 10 || n < 2 || n > 10) {
                showError("Размер таблицы должен быть от 2 до 10.");
                return false;
            }

            if (password.length() != n) {
                showError("Длина пароля должна совпадать с количеством столбцов (n).");
                return false;
            }

            if (text.length() > m * n) {
                showError("Текст не должен превышать размер таблицы (m * n).");
                return false;
            }

        } catch (NumberFormatException e) {
            showError("Некорректные значения m или n.");
            return false;
        }
        return true;
    }

    // Ограничение ввода только цифр и значения в диапазоне от min до max
    private void setNumericFieldConstraints(TextField field, int min, int max) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("[^\\d]", ""));
            }
            if (!field.getText().isEmpty()) {
                int value = Integer.parseInt(field.getText());
                if (value < min) {
                    field.setText(String.valueOf(min));
                } else if (value > max) {
                    field.setText(String.valueOf(max));
                }
            }
        });
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}

