package ru.sortix.encryption.algorithm.simple;

import ru.sortix.encryption.algorithm.EncryptionAlgorithm;

public class CaesarAlgorithm implements SimpleEncryptionAlgorithm {
    private String alphabet;
    private int shift;

    public CaesarAlgorithm(String alphabet, int shift) {
        this.alphabet = alphabet;
        this.shift = shift;
    }

    public void setShift(int shift) {
        this.shift = shift;
    }

    @Override
    public String getShiftedAlphabet() {
        return caesarCipher(alphabet, shift);
    }

    @Override
    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    @Override
    public String encrypt(String text) {
        return caesarCipher(text, shift);
    }

    @Override
    public String decrypt(String text) {
        return caesarCipher(text, -shift);
    }

    private String caesarCipher(String text, int shift) {
        StringBuilder result = new StringBuilder();
        int alphabetLength = alphabet.length();

        for (char c : text.toCharArray()) {
            int pos = alphabet.indexOf(c);

            if (pos != -1) {
                int newPos = (pos + shift + alphabetLength) % alphabetLength;
                result.append(alphabet.charAt(newPos));
            } else {
                result.append(c);
            }
        }

        return result.toString();
    }
}
