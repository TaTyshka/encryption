package ru.sortix.encryption.controller.table;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import javafx.util.converter.IntegerStringConverter;
import ru.sortix.encryption.algorithm.table.FleissnerCipher;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.UnaryOperator;

public class FleissnerCipherController {

    @FXML
    public GridPane outputGrid;
    @FXML
    private GridPane inputGrid;
    @FXML
    private TextField gridSizeField, passwordField;
    @FXML
    private TextArea inputTextArea, resultTextArea;
    @FXML
    private Label charCountLabel; // Новая метка для отображения количества символов

    private int gridSize = 2;  // Размер решетки k
    private int[][] defaultGrid;
    private int[][] grid;  // Прорези, которые клиент выбирает
    private ConcurrentHashMap<Integer, Pair<Integer, Button>> selectedButtons = new ConcurrentHashMap<>();  // Сохранение выбранных кнопок

    private final int MIN_GRID_SIZE = 2;
    private final int MAX_GRID_SIZE = 6;

    @FXML
    public void initialize() {
        // Ограничиваем ввод только числами от 2 до 6 с помощью TextFormatter
        UnaryOperator<TextFormatter.Change> filter = change -> {
            String text = change.getControlNewText();
            if (text.isEmpty()) {
                return change;
            }
            try {
                int value = Integer.parseInt(text);
                if (value >= MIN_GRID_SIZE && value <= MAX_GRID_SIZE) {
                    return change;
                }
            } catch (NumberFormatException e) {
                // Игнорируем нечисловые значения
            }
            return null;
        };
        gridSizeField.setText(String.valueOf(MIN_GRID_SIZE)); // Устанавливаем значение по умолчанию
        gridSizeField.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), MIN_GRID_SIZE, filter));

        // Добавляем слушатель изменений для автоматической генерации решетки
        gridSizeField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.isEmpty()) {
                gridSize = MIN_GRID_SIZE;
                gridSizeField.setText(String.valueOf(MIN_GRID_SIZE));
            } else {
                try {
                    int value = Integer.parseInt(newValue);
                    if (value < MIN_GRID_SIZE || value > MAX_GRID_SIZE) {
                        gridSize = MIN_GRID_SIZE;
                        gridSizeField.setText(String.valueOf(MIN_GRID_SIZE));
                    } else {
                        gridSize = value;
                    }
                } catch (NumberFormatException e) {
                    gridSize = MIN_GRID_SIZE;
                    gridSizeField.setText(String.valueOf(MIN_GRID_SIZE));
                }
            }
            generateGrid();
            updateCharCount();
        });

        // Добавляем слушатель для обновления счетчика символов
        inputTextArea.textProperty().addListener((observable, oldValue, newValue) -> updateCharCount());
        generateGrid();
    }

    // Обновление метки с количеством символов
    private void updateCharCount() {
        int currentLength = inputTextArea.getText().length();
        int maxLength = gridSize * 2 * gridSize * 2;
        charCountLabel.setText(currentLength + "/" + maxLength);
    }

    @FXML
    public void generateGrid() {
        // gridSize уже установлен через слушатель
        defaultGrid = generateNumberGrid(gridSize);
        grid = new int[gridSize * 2][gridSize * 2];
        drawSelectableGrid(inputGrid);
    }

    // Отображение решетки с возможностью выбора ячеек
    private void drawSelectableGrid(GridPane gridPane) {
        gridPane.getChildren().clear();
        selectedButtons.clear(); // Очищаем предыдущие выбранные кнопки

        for (int i = 0; i < gridSize * 2; i++) {
            for (int j = 0; j < gridSize * 2; j++) {
                Button button = new Button("");
                button.setMaxSize(50, 50);
                button.setText(String.valueOf(defaultGrid[i][j]));
                int finalI = i;
                int finalJ = j;

                // Обработка кликов по кнопке (выбор ячейки)
                button.setOnAction(event -> handleGridSelection(button, finalI, finalJ));
                gridPane.add(button, j, i);
            }
        }

        // Выбираем 4 случайные уникальные кнопки
        selectRandomButtons();
    }

    private void selectRandomButtons() {
        List<Pair<Integer, Integer>> availablePositions = new ArrayList<>();

        // Собираем все возможные позиции для выбора
        for (int i = 0; i < gridSize * 2; i++) {
            for (int j = 0; j < gridSize * 2; j++) {
                availablePositions.add(new Pair<>(i, j));
            }
        }

        Collections.shuffle(availablePositions); // Перемешиваем позиции

        Set<Integer> selectedNumbers = new HashSet<>();
        for (Pair<Integer, Integer> position : availablePositions) {
            int row = position.getKey();
            int col = position.getValue();
            int number = defaultGrid[row][col];

            // Выбираем только уникальные числа
            if (!selectedNumbers.contains(number)) {
                selectedNumbers.add(number);
                Button button = (Button) inputGrid.getChildren().get(row * gridSize * 2 + col);
                handleGridSelection(button, row, col);

                if (selectedNumbers.size() == gridSize * gridSize) {
                    break;
                }
            }
        }
    }

    // Логика выбора ячеек и их валидация
    private void handleGridSelection(Button button, int row, int col) {
        int currentSelection = grid[row][col];

        if (currentSelection == 0) {
            button.setStyle("-fx-background-color: lightblue;");
            Pair<Integer, Button> previousButton = selectedButtons.get(defaultGrid[row][col]);
            if (previousButton != null) {
                previousButton.getValue().setStyle("");
                int previousIndex = previousButton.getKey();
                int previousRow = previousIndex / (gridSize * 2);
                int previousCol = previousIndex % (gridSize * 2);
                grid[previousRow][previousCol] = 0;
            }
            grid[row][col] = defaultGrid[row][col];
            int index = row * gridSize * 2 + col;
            selectedButtons.put(grid[row][col], new Pair<>(index, button));
        }
    }

    // Валидация, что выбраны все числа от 1 до k
    private boolean validateSelection() {
        for (int i = 1; i <= gridSize; i++) {
            if (!selectedButtons.containsKey(i)) {
                return false;  // Число не выбрано
            }
        }
        return true;
    }

    private int[][] generateNumberGrid(int k) {
        int n = k * 2;  // Размер матрицы: 2k x 2k
        int[][] grid = new int[n][n];  // Инициализация матрицы

        // Создаем базовый квадрат k x k
        int[][] baseSquare = new int[k][k];
        int number = 1;
        for (int i = 0; i < k; i++) {
            for (int j = 0; j < k; j++) {
                baseSquare[i][j] = number++;
            }
        }

        // Копируем базовый квадрат в левый верхний угол (поворот 0°)
        copySubMatrix(grid, baseSquare, 0, 0);

        // Поворачиваем базовый квадрат на 90 градусов для правого верхнего квадрата
        int[][] rotated90 = rotateMatrix90(baseSquare);
        copySubMatrix(grid, rotated90, 0, k);

        // Поворачиваем базовый квадрат на 180 градусов для правого нижнего квадрата
        int[][] rotated180 = rotateMatrix90(rotated90);
        copySubMatrix(grid, rotated180, k, k);

        // Поворачиваем базовый квадрат на 270 градусов для левого нижнего квадрата
        int[][] rotated270 = rotateMatrix90(rotated180);
        copySubMatrix(grid, rotated270, k, 0);

        return grid;
    }

    // Метод для поворота матрицы на 90 градусов по часовой стрелке
    private int[][] rotateMatrix90(int[][] matrix) {
        int size = matrix.length;
        int[][] rotated = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                rotated[j][size - 1 - i] = matrix[i][j];
            }
        }
        return rotated;
    }

    // Копирование подматрицы в матрицу grid по указанным координатам
    private void copySubMatrix(int[][] grid, int[][] subMatrix, int rowOffset, int colOffset) {
        int size = subMatrix.length;
        for (int i = 0; i < size; i++) {
            System.arraycopy(subMatrix[i], 0, grid[rowOffset + i], colOffset, size);
        }
    }

    @FXML
    public void encrypt() {
        if (!validateInputs()) {
            return;
        }

        String inputText = inputTextArea.getText();
        FleissnerCipher fleissnerCipher = new FleissnerCipher(grid, gridSize, passwordField.getText());  // Передаем прорези и размер k
        String result = fleissnerCipher.encrypt(inputText);
        resultTextArea.setText(result);

        // Отображаем результат в таблице
        drawGridWithText(result, outputGrid);
    }

    @FXML
    public void decrypt() {
        if (!validateInputs()) {
            return;
        }

        String inputText = inputTextArea.getText();
        FleissnerCipher fleissnerCipher = new FleissnerCipher(grid, gridSize, passwordField.getText());
        String result = fleissnerCipher.decrypt(inputText);
        resultTextArea.setText(result);

        // Отображаем результат в таблице
        drawGridWithText(result, outputGrid);
    }

    // Валидация входных данных
    private boolean validateInputs() {
        // Проверка выбора всех необходимых клеток
        if (!validateSelection()) {
            resultTextArea.setText("Ошибка: выберите все прорези.");
            return false;
        }

        // Проверка наличия пароля
        String password = passwordField.getText();
        if (password == null || password.isEmpty()) {
            resultTextArea.setText("Ошибка: введите пароль.");
            return false;
        }

        // Проверка соответствия длины пароля размеру k * 2
        if (password.length() != gridSize * 2) {
            resultTextArea.setText("Ошибка: длина пароля должна быть равна " + (gridSize * 2) + " символам.");
            return false;
        }

        // Проверка наличия текста для шифрования/дешифрования
        String inputText = inputTextArea.getText();
        if (inputText == null || inputText.isEmpty()) {
            resultTextArea.setText("Ошибка: введите текст для шифрования/дешифрования.");
            return false;
        }

        // Проверка длины текста
        if (inputText.length() > gridSize * 2 * gridSize * 2) {
            resultTextArea.setText("Ошибка: текст не должен превышать " + (gridSize * 2 * gridSize * 2) + " символов.");
            return false;
        }

        return true;
    }

    // Отображение текста в таблице
    private void drawGridWithText(String text, GridPane gridPane) {
        gridPane.getChildren().clear();
        int textIndex = 0;
        for (int i = 0; i < gridSize * 2; i++) {
            for (int j = 0; j < gridSize * 2; j++) {
                Button button = new Button();
                button.setMaxSize(50, 50);

                // Если символов в тексте больше нет — заполняем пробелами
                if (textIndex < text.length()) {
                    button.setText(String.valueOf(text.charAt(textIndex++)));
                } else {
                    button.setText(" ");
                }

                gridPane.add(button, i, j);
            }
        }
    }
}