package ru.sortix.encryption.algorithm;

public interface EncryptionAlgorithm {
    String encrypt(String input);
    String decrypt(String input);
}
