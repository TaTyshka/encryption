package ru.sortix.encryption.algorithm.simple;

import ru.sortix.encryption.algorithm.EncryptionAlgorithm;

public interface SimpleEncryptionAlgorithm extends EncryptionAlgorithm {
    void setAlphabet(String alphabet);
    String getShiftedAlphabet();
}
