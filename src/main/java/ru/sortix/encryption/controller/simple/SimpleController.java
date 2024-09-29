package ru.sortix.encryption.controller.simple;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import ru.sortix.encryption.EncryptionAlgorithm;

public abstract class SimpleController {

    @FXML
    protected TextField inputTextField;

    @FXML
    protected TextArea outputTextArea;

    @FXML
    protected RadioButton englishRadioButton;

    @FXML
    protected RadioButton russianRadioButton;

    @FXML
    protected ToggleGroup alphabetGroup;

    @FXML
    protected CheckBox encryptSpacesCheckBox;

    @FXML
    protected CheckBox encryptPunctuationCheckBox;

    @FXML
    protected CheckBox includeUpperCaseCheckBox;

    @FXML
    protected Label alphabetLabel;

    @FXML
    protected Label shiftedAlphabetLabel;

    protected EncryptionAlgorithm currentAlgorithm;

    private static final String ENG_ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
    private static final String RUS_ALPHABET = "АБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯабвгдеёжзийклмнопрстуфхцчшщъыьэюя";
    private static final String PUNCTUATION = ".,!?";
    private static final String SPACE = " ";

    public void initialize() {
        setupRadioButtons();
        encryptSpacesCheckBox.setOnAction(e -> updateAlphabets());
        encryptPunctuationCheckBox.setOnAction(e -> updateAlphabets());
        includeUpperCaseCheckBox.setOnAction(e -> updateAlphabets());
    }

    protected void setupRadioButtons() {
        alphabetGroup = new ToggleGroup();
        englishRadioButton.setToggleGroup(alphabetGroup);
        russianRadioButton.setToggleGroup(alphabetGroup);
        englishRadioButton.setOnAction(e -> updateAlphabets());
        russianRadioButton.setOnAction(e -> updateAlphabets());
        englishRadioButton.setSelected(true);
    }

    protected String getSelectedAlphabet() {
        String baseAlphabet = englishRadioButton.isSelected() ? ENG_ALPHABET : RUS_ALPHABET;

        if (encryptSpacesCheckBox.isSelected()) {
            baseAlphabet += SPACE;
        }

        if (encryptPunctuationCheckBox.isSelected()) {
            baseAlphabet += PUNCTUATION;
        }

        if (!includeUpperCaseCheckBox.isSelected()) {
            baseAlphabet = baseAlphabet.replaceAll("[A-ZА-ЯЁ]", "");
        }

        return baseAlphabet;
    }

    protected void updateAlphabets() {
        String alphabet = getSelectedAlphabet();
        alphabetLabel.setText(alphabet);
        currentAlgorithm.setAlphabet(alphabet);
        updateShiftedAlphabet();
    }

    private void updateShiftedAlphabet() {
        String shiftedAlphabet = currentAlgorithm.getShiftedAlphabet();
        shiftedAlphabetLabel.setText(shiftedAlphabet);
    }
}
