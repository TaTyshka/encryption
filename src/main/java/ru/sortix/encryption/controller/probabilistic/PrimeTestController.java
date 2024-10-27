package ru.sortix.encryption.controller.probabilistic;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.sortix.encryption.algorithm.probabilistic.PrimeTest;

public class PrimeTestController {

    @FXML
    private TextField inputN;

    @FXML
    private TextField inputIterations;

    @FXML
    private TextArea resultTextArea;

    private PrimeTest primeTestAlgorithm;

    public void setPrimeTestAlgorithm(PrimeTest primeTestAlgorithm) {
        this.primeTestAlgorithm = primeTestAlgorithm;
    }

    @FXML
    public void testPrimality() {
        if (primeTestAlgorithm == null) {
            resultTextArea.setText("Алгоритм не установлен.");
            return;
        }

        try {
            // Чтение и парсинг значений n и количества итераций
            int n = Integer.parseInt(inputN.getText());
            int iterations = Integer.parseInt(inputIterations.getText());

            // Проверка условий: n должно быть нечетным и >= 5, итерации положительные
            if (n < 5 || n % 2 == 0) {
                resultTextArea.setText("Ошибка: число n должно быть нечетным и ≥ 5.");
                return;
            }

            if (iterations <= 0) {
                resultTextArea.setText("Ошибка: количество итераций должно быть положительным.");
                return;
            }

            // Вызов алгоритма и вывод результата
            boolean isProbablePrime = primeTestAlgorithm.isProbablePrime(n, iterations);
            String result = isProbablePrime ? "Число n, вероятно, простое." : "Число n составное.";
            resultTextArea.setText(result);

        } catch (NumberFormatException e) {
            resultTextArea.setText("Ошибка: Введите корректные целые числа.");
        }
    }
}
