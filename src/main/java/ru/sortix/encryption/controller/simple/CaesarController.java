package ru.sortix.encryption.controller.simple;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;
import ru.sortix.encryption.algorithm.simple.CaesarAlgorithm;

import java.util.function.UnaryOperator;

public class CaesarController extends SimpleController {

    @FXML
    private TextField keyTextField;

    @FXML
    protected Button encryptTextButton;

    @FXML
    protected Button decryptTextButton;

    public void initialize() {
        super.initialize();
        currentAlgorithm = new CaesarAlgorithm(getSelectedAlphabet(), 0);
        setKeyInputFilter();
        updateAlphabets();
        keyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentAlgorithm instanceof CaesarAlgorithm) {
                updateAlphabets();
            }
        });
    }

    private void setKeyInputFilter() {
        if (currentAlgorithm instanceof CaesarAlgorithm caesarAlgorithm) {
            UnaryOperator<TextFormatter.Change> filter = change -> {
                String newText = change.getControlNewText();
                if (newText.isEmpty() || newText.equals("-")) {
                    caesarAlgorithm.setShift(0);
                    return change;
                }
                if (newText.matches("-?\\d*")) {
                    try {
                        int key = Integer.parseInt(newText);
                        if (Math.abs(key) < getSelectedAlphabet().length()) {
                            caesarAlgorithm.setShift(key);
                            return change;
                        }
                    } catch (NumberFormatException ex) {
                        return null;
                    }
                }
                return null;
            };
            StringConverter<Integer> converter = new IntegerStringConverter();
            TextFormatter<Integer> formatter = new TextFormatter<>(converter, 0, filter);
            keyTextField.setTextFormatter(formatter);
        }
    }

    @FXML
    protected void encryptText() {
        String inputText = inputTextField.getText();
        int key = Integer.parseInt(keyTextField.getText());
        ((CaesarAlgorithm) currentAlgorithm).setShift(key);
        String encryptedText = currentAlgorithm.encrypt(inputText);
        outputTextArea.setText(encryptedText);
    }

    @FXML
    protected void decryptText() {
        String inputText = inputTextField.getText();
        int key = Integer.parseInt(keyTextField.getText());
        ((CaesarAlgorithm) currentAlgorithm).setShift(key);
        String decryptedText = currentAlgorithm.decrypt(inputText);
        outputTextArea.setText(decryptedText);
    }
}
