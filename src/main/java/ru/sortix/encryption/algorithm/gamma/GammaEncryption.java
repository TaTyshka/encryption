package ru.sortix.encryption.algorithm.gamma;

import ru.sortix.encryption.EncryptionAlgorithm;

public class GammaEncryption implements EncryptionAlgorithm {

    private static final String ALPHABET = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ";
    private static final int MOD = ALPHABET.length();

    private final String gamma;

    // Конструктор с ключом
    public GammaEncryption(String gamma) {
        this.gamma = gamma.toUpperCase();
    }

    // Поиск индекса символа в алфавите
    private int getCharIndex(char ch) {
        return ALPHABET.indexOf(ch);
    }

    // Метод для получения символа по индексу
    private char getCharByIndex(int index) {
        return ALPHABET.charAt(index);
    }

    // Шифрование строки
    @Override
    public String encrypt(String input) {
        input = input.toUpperCase();
        String extendedGamma = extendGamma(input);
        StringBuilder encryptedText = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            int charIndex = getCharIndex(input.charAt(i)) + 1;
            int gammaIndex = getCharIndex(extendedGamma.charAt(i)) + 1;
            if (charIndex != -1 && gammaIndex != -1) {
                int encryptedIndex = (charIndex + gammaIndex) % MOD;
                encryptedText.append(getCharByIndex(encryptedIndex - 1));
            } else {
                encryptedText.append(input.charAt(i));
            }
        }
        return encryptedText.toString();
    }

    // Расшифровка строки
    @Override
    public String decrypt(String input) {
        input = input.toUpperCase();
        String extendedGamma = extendGamma(input);
        StringBuilder decryptedText = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            int charIndex = getCharIndex(input.charAt(i)) + 1;
            int gammaIndex = getCharIndex(extendedGamma.charAt(i)) + 1;
            if (charIndex != -1 && gammaIndex != -1) {
                // Операция вычитания по модулю
                int decryptedIndex = (charIndex - gammaIndex + MOD) % MOD;
                decryptedText.append(getCharByIndex(decryptedIndex - 1));
            } else {
                decryptedText.append(input.charAt(i));
            }
        }
        return decryptedText.toString();
    }

    // Увеличение гаммы до длины текста
    public String extendGamma(String input) {
        return gamma.repeat((input.length() / gamma.length()) + 1).substring(0, input.length());
    }

    @Override
    public void setAlphabet(String alphabet) {
        throw new UnsupportedOperationException();
    }

    @Override
    public String getShiftedAlphabet() {
        throw new UnsupportedOperationException();
    }
}
