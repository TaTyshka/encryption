package ru.sortix.encryption;

public class CaesarAlgorithm implements EncryptionAlgorithm {
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
        StringBuilder shifted = new StringBuilder(alphabet.length());
        for (int i = 0; i < alphabet.length(); i++) {
            shifted.append(alphabet.charAt((i + shift) % alphabet.length()));
        }
        return shifted.toString();
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

        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
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
