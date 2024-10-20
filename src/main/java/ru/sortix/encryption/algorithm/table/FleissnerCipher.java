package ru.sortix.encryption.algorithm.table;

import ru.sortix.encryption.algorithm.EncryptionAlgorithm;

public class FleissnerCipher implements EncryptionAlgorithm {
    private final int[][] grid;
    private final int gridSize;
    private final String password;

    public FleissnerCipher(int[][] grid, int k, String password) {
        this.gridSize = 2 * k;
        this.grid = cloneGrid(grid);
        this.password = password;
    }

    @Override
    public String encrypt(String input) {
        char[][] result = new char[gridSize][gridSize];
        int[] sortedIndices = getSortedIndicesByPassword(password);

        // Вставляем символы в прорези после каждой из 4-х поворотов
        applyGridWithRotation(result, input);

        // Сортируем колонки по паролю и возвращаем результат
        return fromTable(sortColumnsByPassword(result, sortedIndices));
    }

    @Override
    public String decrypt(String input) {
        int[][] gridPattern = cloneGrid(grid);  // Клонируем оригинальный паттерн
        char[][] decrypted = new char[gridSize][gridSize];

        // Преобразуем строку input в 2k x 2k матрицу
        char[][] table = toTable(input);
        int[] sortedIndices = getSortedIndicesByPassword(password);
        table = restoreOriginalOrder(table, sortedIndices);

        // Поворачиваем паттерн прорезей в обратном порядке 4 раза и извлекаем символы
        StringBuilder result = new StringBuilder();
        for (int rotation = 0; rotation < 4; rotation++) {
            rotateGridCounterClockwise(gridPattern);
            getFromGrid(result, table, gridPattern);// Поворачиваем в обратную сторону
        }

        return result.reverse().toString();
    }

    // Преобразование строки в 2k x 2k матрицу
    private char[][] toTable(String input) {
        char[][] table = new char[gridSize][gridSize ];
        for (int i = 0, index = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                table[j][i] = input.charAt(index++);
            }
        }
        return table;
    }

    // Преобразование матрицы обратно в строку
    private String fromTable(char[][] table) {
        StringBuilder result = new StringBuilder();
        for (int j = 0; j < gridSize; j++) {
            for (int i = 0; i < gridSize; i++) {
                result.append(table[i][j]);
            }
        }
        return result.toString();
    }

    // Сортировка колонок по символам пароля
    private char[][] sortColumnsByPassword(char[][] table, int[] sortedIndices) {
        char[][] sortedTable = new char[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                sortedTable[i][j] = table[i][sortedIndices[j]];
            }
        }
        return sortedTable;
    }

    // Восстановление оригинального порядка колонок
    private char[][] restoreOriginalOrder(char[][] table, int[] sortedIndices) {
        char[][] restoredTable = new char[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                restoredTable[i][sortedIndices[j]] = table[i][j];
            }
        }
        return restoredTable;
    }

    // Функция для поворота матрицы на 90 градусов
    private void rotateGrid(int[][] gridPattern) {
        int[][] rotated = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                rotated[j][gridSize - 1 - i] = gridPattern[i][j];
            }
        }
        for (int i = 0; i < gridSize; i++) {
            System.arraycopy(rotated[i], 0, gridPattern[i], 0, gridSize);
        }
    }

    // Функция для поворота паттерна против часовой стрелки на 90 градусов
    private void rotateGridCounterClockwise(int[][] gridPattern) {
        int[][] rotated = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                rotated[gridSize - 1 - j][i] = gridPattern[i][j];
            }
        }
        for (int i = 0; i < gridSize; i++) {
            System.arraycopy(rotated[i], 0, gridPattern[i], 0, gridSize);
        }
    }

    // Применение паттерна с поворотами
    private void applyGridWithRotation(char[][] output, String input) {
        int[][] gridPattern = grid;
        int index = 0;
        for (int rotation = 0; rotation < 4; rotation++) {
            index = applyGrid(output, input, index, gridPattern);
            rotateGrid(gridPattern);
        }
    }

    // Вставка символов в матрицу по текущему паттерну
    private int applyGrid(char[][] output, String input, int startIndex, int[][] gridPattern) {
        for (int i = 0; i < gridSize; i++) {
            for (int j = 0; j < gridSize; j++) {
                if (gridPattern[i][j] != 0 && startIndex < input.length()) {
                    output[i][j] = input.charAt(startIndex++);
                }
            }
        }
        return startIndex;
    }

    private void getFromGrid(StringBuilder output, char[][] grid, int[][] gridPattern) {
        for (int i = gridSize - 1; i >= 0; i--) {
            for (int j = gridSize - 1; j >= 0; j--) {
                if (gridPattern[i][j] != 0) {
                    output.append(grid[i][j]);
                }
            }
        }
    }

    // Клонирование матрицы
    private int[][] cloneGrid(int[][] originalGrid) {
        int[][] clone = new int[gridSize][gridSize];
        for (int i = 0; i < gridSize; i++) {
            System.arraycopy(originalGrid[i], 0, clone[i], 0, gridSize);
        }
        return clone;
    }

    // Получение индексов символов пароля в отсортированном порядке
    private int[] getSortedIndicesByPassword(String password) {
        return password.chars()
                .mapToObj(c -> (char) c)
                .sorted()
                .mapToInt(password::indexOf)
                .toArray();
    }
}