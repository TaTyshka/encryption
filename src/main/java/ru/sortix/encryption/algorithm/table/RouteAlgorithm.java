package ru.sortix.encryption.algorithm.table;

import ru.sortix.encryption.algorithm.EncryptionAlgorithm;

import java.util.Arrays;
import java.util.Comparator;

public class RouteAlgorithm implements EncryptionAlgorithm {

    private int rows;
    private int cols;
    private String password;

    public RouteAlgorithm(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public void setSize(int rows, int cols) {
        this.rows = rows;
        this.cols = cols;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private int[] getColumnOrder() {
        Character[] sortedPassword = new Character[password.length()];
        for (int i = 0; i < password.length(); i++) {
            sortedPassword[i] = password.charAt(i);
        }

        // Сортировка индексов по паролю
        Integer[] indices = new Integer[cols];
        for (int i = 0; i < cols; i++) {
            indices[i] = i;
        }

        Arrays.sort(indices, Comparator.comparingInt(i -> sortedPassword[i]));
        return Arrays.stream(indices).mapToInt(i -> i).toArray();
    }

    @Override
    public String encrypt(String text) {
        char[][] table = new char[rows][cols];
        int index = 0;

        // Заполнение таблицы по строкам
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (index < text.length()) {
                    table[i][j] = text.charAt(index++);
                } else {
                    table[i][j] = ' '; // Заполнение пробелами
                }
            }
        }

        // Порядок столбцов по паролю
        int[] columnOrder = getColumnOrder();
        StringBuilder encryptedText = new StringBuilder();

        // Чтение по столбцам в порядке, заданном паролем
        for (int col : columnOrder) {
            for (int i = 0; i < rows; i++) {
                encryptedText.append(table[i][col]);
            }
        }

        return encryptedText.toString();
    }

    public String decrypt(String encryptedText) {
        char[][] table = new char[rows][cols];
        int[] columnOrder = getColumnOrder();
        int index = 0;

        // Заполнение таблицы по столбцам в порядке, заданном паролем
        for (int col : columnOrder) {
            for (int i = 0; i < rows; i++) {
                if (index < encryptedText.length()) {
                    table[i][col] = encryptedText.charAt(index++);
                }
            }
        }

        // Чтение по строкам для расшифровки
        StringBuilder decryptedText = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                decryptedText.append(table[i][j]);
            }
        }

        return decryptedText.toString().trim();
    }
}