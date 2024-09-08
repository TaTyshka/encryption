package ru.sortix.encryption;

public interface EncryptionAlgorithm {
    String encrypt(String input);
    String decrypt(String input);
    void setAlphabet(String alphabet);
    String getShiftedAlphabet();
}
