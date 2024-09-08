package ru.sortix.encryption;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.StringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.util.function.UnaryOperator;

public class EncryptionController {
    @FXML
    public Label keyTextLabel;
    @FXML
    public CheckBox includeUpperCaseCheckBox;
    @FXML
    private TextField inputTextField;
    @FXML
    private TextField keyTextField;
    @FXML
    private TextArea outputTextArea;
    @FXML
    private CheckBox encryptSpacesCheckBox;
    @FXML
    private CheckBox encryptPunctuationCheckBox;
    @FXML
    private RadioButton englishRadioButton;
    @FXML
    private RadioButton russianRadioButton;
    @FXML
    private Label alphabetLabel;
    @FXML
    private Label shiftedAlphabetLabel;
    @FXML
    private ToggleGroup alphabetGroup;

    private EncryptionAlgorithm currentAlgorithm = new CaesarAlgorithm("", 0);

    private final String engAlphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private final String rusAlphabet = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private final String punctuation = ".,!?";
    private final String space = " ";

    public void initialize() {
        setKeyInputFilter();
        setupRadioButtons();
        updateAlphabets();
        encryptSpacesCheckBox.setOnAction(e -> updateAlphabets());
        encryptPunctuationCheckBox.setOnAction(e -> updateAlphabets());
        includeUpperCaseCheckBox.setOnAction(e -> updateAlphabets());
        keyTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (currentAlgorithm instanceof CaesarAlgorithm) {
                updateShiftedAlphabet();
            }
        });
    }

    private void setKeyInputFilter() {
        if (currentAlgorithm instanceof CaesarAlgorithm caesarAlgorithm) {
            UnaryOperator<TextFormatter.Change> filter = change -> {
                String newText = change.getControlNewText();
                if (newText.isEmpty()) {
                    caesarAlgorithm.setShift(0);
                    return change;
                }
                if (newText.matches("\\d*")) {
                    try {
                        int key = Integer.parseInt(newText);
                        if (key >= 0 && key < getSelectedAlphabet().length()) {
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

    private void setupRadioButtons() {
        alphabetGroup = new ToggleGroup();
        englishRadioButton.setToggleGroup(alphabetGroup);
        russianRadioButton.setToggleGroup(alphabetGroup);
        englishRadioButton.setOnAction(e -> updateAlphabets());
        russianRadioButton.setOnAction(e -> updateAlphabets());
        englishRadioButton.setSelected(true);
    }

    private String getSelectedAlphabet() {
        String baseAlphabet = englishRadioButton.isSelected() ? engAlphabet : rusAlphabet;

        if (encryptSpacesCheckBox.isSelected()) {
            baseAlphabet += space;
        }

        if (encryptPunctuationCheckBox.isSelected()) {
            baseAlphabet += punctuation;
        }

        if (!includeUpperCaseCheckBox.isSelected()) {
            baseAlphabet = baseAlphabet.replaceAll("[A-ZА-ЯЁ]", "");
        }

        return baseAlphabet;
    }

    private void updateAlphabets() {
        String alphabet = getSelectedAlphabet();
        alphabetLabel.setText(alphabet);
        currentAlgorithm.setAlphabet(alphabet);
        updateShiftedAlphabet();
    }

    private void updateShiftedAlphabet() {
        String shiftedAlphabet = currentAlgorithm.getShiftedAlphabet();
        shiftedAlphabetLabel.setText(shiftedAlphabet);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Ошибка");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void encryptText() {
        String inputText = inputTextField.getText();
        if (inputText.isEmpty()) {
            showError("Введите текст для шифрования.");
            return;
        }
        if (keyTextField.getText().isEmpty()) {
            showError("Введите корректный ключ.");
            return;
        }
        String encryptedText = currentAlgorithm.encrypt(inputText);
        outputTextArea.setText(encryptedText);
    }

    @FXML
    private void decryptText() {
        String inputText = inputTextField.getText();
        if (inputText.isEmpty()) {
            showError("Введите текст для расшифровки.");
            return;
        }
        if (keyTextField.getText().isEmpty()) {
            showError("Введите корректный ключ.");
            return;
        }
        String decryptedText = currentAlgorithm.decrypt(inputText);
        outputTextArea.setText(decryptedText);
    }

    public void switchToCaesar() {
        keyTextField.setVisible(true);
        keyTextLabel.setVisible(true);
        currentAlgorithm = new CaesarAlgorithm(getSelectedAlphabet(), 0);
        keyTextField.setText("0");
        setKeyInputFilter();
        updateShiftedAlphabet();
    }

    public void switchToAtbash() {
        keyTextField.setVisible(false);
        keyTextLabel.setVisible(false);
        currentAlgorithm = new AtbashAlgorithm(getSelectedAlphabet());
        updateShiftedAlphabet();
    }
}
