package ru.sortix.encryption.controller.probabilistic;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.sortix.encryption.algorithm.probabilistic.JacobiSymbol;

public class JacobiSymbolController {
    @FXML
    private TextField inputA;

    @FXML
    private TextField inputN;

    @FXML
    private TextArea resultTextArea;

    @FXML
    public void calculateJacobiSymbol() {
        try {
            int a = Integer.parseInt(inputA.getText());
            int n = Integer.parseInt(inputN.getText());

            if (n < 3 || n % 2 == 0) {
                resultTextArea.setText("Ошибка: число n должно быть нечетным и >= 3.");
                return;
            }

            if (!(0 <= a && a < n)) {
                resultTextArea.setText("Ошибка: число a должно быть в диапазоне [0, n).");
                return;
            }

            int jacobiSymbol = JacobiSymbol.compute(a, n);
            resultTextArea.setText("Символ Якоби (a/n) = " + jacobiSymbol);

        } catch (NumberFormatException e) {
            resultTextArea.setText("Ошибка: Введите корректные целые числа.");
        }
    }
}
