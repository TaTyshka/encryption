package ru.sortix.encryption.controller.euclid;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.sortix.encryption.algorithm.euclid.EuclidAlgorithm;

public class EuclidEncryptionController {

    @FXML
    private TextField inputA;

    @FXML
    private TextField inputB;

    @FXML
    private TextArea resultTextArea;

    private EuclidAlgorithm algorithm;

    // Метод для установки алгоритма Евклида
    public void setAlgorithm(EuclidAlgorithm algorithm) {
        this.algorithm = algorithm;
    }

    // Метод для нахождения НОД
    @FXML
    public void encrypt() {
        if (algorithm == null) {
            resultTextArea.setText("Алгоритм не установлен.");
            return;
        }

        try {
            // Чтение и парсинг значений
            int a = Integer.parseInt(inputA.getText());
            int b = Integer.parseInt(inputB.getText());

            // Проверка условий: 0 < b ≤ a
            if (b <= 0 || b > a) {
                resultTextArea.setText("Ошибка: должно выполняться условие 0 < b ≤ a");
                return;
            }

            // Вызов алгоритма и вывод результата
            String result = algorithm.findGCD(a, b);
            resultTextArea.setText(result);

        } catch (NumberFormatException e) {
            resultTextArea.setText("Ошибка: Введите корректные целые числа.");
        }
    }
}