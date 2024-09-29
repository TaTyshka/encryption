package ru.sortix.encryption.algorithm.table;

import ru.sortix.encryption.EncryptionAlgorithm;

public class VigenereCipher implements EncryptionAlgorithm {

    private static final String RUSSIAN_ALPHABET = "АБВГДЕЖЗИЙКЛМНОПРСТУФХЦЧШЩЬЫЭЮЯ";
    private static final int ALPHABET_LENGTH = RUSSIAN_ALPHABET.length();
    private String key;

    public void setKey(String key) {
        this.key = key.toUpperCase();
    }

    @Override
    public String encrypt(String input) {
        input = input.toUpperCase();
        String repeatedKey = generateRepeatedKey(input);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char messageChar = input.charAt(i);
            char keyChar = repeatedKey.charAt(i);

            if (isRussianLetter(messageChar)) {
                int messageIndex = RUSSIAN_ALPHABET.indexOf(messageChar);
                int keyIndex = RUSSIAN_ALPHABET.indexOf(keyChar);
                int encryptedIndex = (messageIndex + keyIndex) % ALPHABET_LENGTH;
                result.append(RUSSIAN_ALPHABET.charAt(encryptedIndex));
            } else {
                result.append(messageChar); // Оставляем символы, не являющиеся буквами
            }
        }

        return result.toString();
    }

    @Override
    public String decrypt(String input) {
        input = input.toUpperCase();
        String repeatedKey = generateRepeatedKey(input);
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < input.length(); i++) {
            char messageChar = input.charAt(i);
            char keyChar = repeatedKey.charAt(i);

            if (isRussianLetter(messageChar)) {
                int messageIndex = RUSSIAN_ALPHABET.indexOf(messageChar);
                int keyIndex = RUSSIAN_ALPHABET.indexOf(keyChar);
                int decryptedIndex = (messageIndex - keyIndex + ALPHABET_LENGTH) % ALPHABET_LENGTH;
                result.append(RUSSIAN_ALPHABET.charAt(decryptedIndex));
            } else {
                result.append(messageChar); // Оставляем символы, не являющиеся буквами
            }
        }

        return result.toString();
    }

    @Override
    public void setAlphabet(String alphabet) {

    }

    @Override
    public String getShiftedAlphabet() {
        return "";
    }

    // Метод для генерации повторяющегося ключа
    public String generateRepeatedKey(String input) {
        StringBuilder repeatedKey = new StringBuilder();
        int keyLength = key.length();

        for (int i = 0; i < input.length(); i++) {
            if (isRussianLetter(input.charAt(i))) {
                repeatedKey.append(key.charAt(i % keyLength));
            } else {
                repeatedKey.append(input.charAt(i)); // Пропускаем символы, не являющиеся буквами
            }
        }

        return repeatedKey.toString();
    }

    // Метод проверки, является ли символ буквой русского алфавита
    private boolean isRussianLetter(char ch) {
        return RUSSIAN_ALPHABET.indexOf(ch) >= 0;
    }
}
