package ru.sortix.encryption.controller.gamma;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import ru.sortix.encryption.algorithm.gamma.GammaEncryption;

public class GammaEncryptionController {

    @FXML
    private TextArea inputTextArea;
    @FXML
    private TextField gammaField;
    @FXML
    private TextArea extendedGammaTextArea;
    @FXML
    private TextArea resultTextArea;

    private GammaEncryption gammaEncryption;

    // Метод для шифрования
    private void processText(boolean isEncrypt) {
        String inputText = inputTextArea.getText();
        String gamma = gammaField.getText();

        if (inputText.isEmpty() || gamma.isEmpty()) {
            resultTextArea.setText("Пожалуйста, введите текст и гамму!");
            return;
        }

        gammaEncryption = new GammaEncryption(gamma);

        String extendedGamma = gammaEncryption.extendGamma(inputText);

        String resultText;
        if (isEncrypt) {
            resultText = gammaEncryption.encrypt(inputText);
        } else {
            resultText = gammaEncryption.decrypt(inputText);
        }

        extendedGammaTextArea.setText(extendedGamma);
        resultTextArea.setText(resultText);
    }

    @FXML
    private void encrypt() {
        processText(true);
    }

    @FXML
    private void decrypt() {
        processText(false);
    }
}
