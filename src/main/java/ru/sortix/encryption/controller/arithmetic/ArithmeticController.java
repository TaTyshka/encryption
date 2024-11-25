package ru.sortix.encryption.controller.arithmetic;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.sortix.encryption.algorithm.arithmetic.*;

public class ArithmeticController {

    @FXML
    private TextField inputU;

    @FXML
    private TextField inputV;

    @FXML
    private TextField inputBase;

    @FXML
    private TextArea resultTextArea;

    private ArithmeticAlgorithm algorithm;

    public void setAlgorithm(ArithmeticAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    @FXML
    private void calculate() {
        if (algorithm == null) {
            resultTextArea.setText("Алгоритм не установлен.");
            return;
        }

        try {
            // Читаем общие входные данные
            String u = inputU.getText();
            String v = inputV.getText();
            int base = Integer.parseInt(inputBase.getText());

            // Проверяем данные и вызываем алгоритм
            if (algorithm instanceof AdditionAlgorithm || algorithm instanceof SubtractionAlgorithm) {
                if (u.length() != v.length()) {
                    resultTextArea.setText("Ошибка: числа U и V должны быть одинаковой разрядности n.");
                    return;
                }
                if (algorithm instanceof SubtractionAlgorithm && u.compareTo(v) <= 0) {
                    resultTextArea.setText("Ошибка: U должно быть больше V.");
                    return;
                }
            } else if (algorithm instanceof DivisionAlgorithm) {
                if (v.charAt(0) == '0') {
                    resultTextArea.setText("Ошибка: старший разряд числа V не может быть 0.");
                    return;
                }
            }

            String result = algorithm.execute(u, v, base);
            resultTextArea.setText("Результат: " + result);
        } catch (NumberFormatException e) {
            resultTextArea.setText("Ошибка: Введите корректные данные.");
        }
    }
}
