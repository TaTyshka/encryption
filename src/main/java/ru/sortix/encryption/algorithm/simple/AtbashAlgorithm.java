package ru.sortix.encryption.algorithm.simple;

import ru.sortix.encryption.algorithm.EncryptionAlgorithm;

public class AtbashAlgorithm implements SimpleEncryptionAlgorithm {

    private String alphabet = "";
    private String reversedAlphabet = "";

    public AtbashAlgorithm(String alphabet) {
        setAlphabet(alphabet);
    }

    private String atbashEncrypt(String input) {

        StringBuilder result = new StringBuilder();
        for (char c : input.toCharArray()) {
            int index = alphabet.indexOf(c);
            if (index != -1) {
                result.append(reversedAlphabet.charAt(index));
            } else {
                result.append(c);
            }
        }
        return result.toString();
    }

    @Override
    public String getShiftedAlphabet() {
        return reversedAlphabet;
    }

    @Override
    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
        this.reversedAlphabet = new StringBuilder(alphabet).reverse().toString();
    }

    @Override
    public String encrypt(String input) {
        return atbashEncrypt(input);
    }

    @Override
    public String decrypt(String input) {
        return atbashEncrypt(input);
    }
}
