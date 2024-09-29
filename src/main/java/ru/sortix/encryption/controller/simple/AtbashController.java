package ru.sortix.encryption.controller.simple;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import ru.sortix.encryption.algorithm.simple.AtbashAlgorithm;

public class AtbashController extends SimpleController {

    @FXML
    public Button encryptDecryptButton;

    public void initialize() {
        super.initialize();
        currentAlgorithm = new AtbashAlgorithm(getSelectedAlphabet());
        updateAlphabets();
    }

    @FXML
    protected void encryptText() {
        String inputText = inputTextField.getText();
        String encryptedText = currentAlgorithm.encrypt(inputText);
        outputTextArea.setText(encryptedText);
    }
}