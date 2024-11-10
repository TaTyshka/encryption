package ru.sortix.encryption.controller.pollard;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.commons.jexl3.*;
import ru.sortix.encryption.algorithm.pollard.PollardsRhoAlgorithm;

import java.math.BigInteger;

public class PollardsRhoController {

    @FXML
    private TextField inputN;

    @FXML
    private TextField inputC;

    @FXML
    private TextField inputFunction;

    @FXML
    private TextArea resultTextArea;

    @FXML
    private TableView<PollardsRhoAlgorithm.PollardStep> stepsTable;

    @FXML
    private TableColumn<PollardsRhoAlgorithm.PollardStep, Integer> columnI;

    @FXML
    private TableColumn<PollardsRhoAlgorithm.PollardStep, String> columnA;

    @FXML
    private TableColumn<PollardsRhoAlgorithm.PollardStep, String> columnB;

    @FXML
    private TableColumn<PollardsRhoAlgorithm.PollardStep, String> columnD;

    private ObservableList<PollardsRhoAlgorithm.PollardStep> steps = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        columnI.prefWidthProperty().bind(stepsTable.widthProperty().divide(4));
        columnA.prefWidthProperty().bind(stepsTable.widthProperty().divide(4));
        columnB.prefWidthProperty().bind(stepsTable.widthProperty().divide(4));
        columnD.prefWidthProperty().bind(stepsTable.widthProperty().divide(4));
        columnI.setCellValueFactory(new PropertyValueFactory<>("i"));
        columnA.setCellValueFactory(new PropertyValueFactory<>("a"));
        columnB.setCellValueFactory(new PropertyValueFactory<>("b"));
        columnD.setCellValueFactory(new PropertyValueFactory<>("d"));

        stepsTable.setItems(steps);
    }

    @FXML
    public void startPollardsRho() {
        try {
            steps.clear();

            BigInteger n = new BigInteger(inputN.getText());
            BigInteger c = new BigInteger(inputC.getText());
            String functionInput = inputFunction.getText();

            if (n.compareTo(BigInteger.ONE) <= 0) {
                resultTextArea.setText("Ошибка: n должно быть больше 1.");
                return;
            }

            JexlEngine jexl = new JexlBuilder().create();
            JexlExpression expression = jexl.createExpression(functionInput);
            PollardsRhoAlgorithm pollardsRho = initAlgoritm(expression, n, c);
            BigInteger divisor = pollardsRho.run(steps);

            if (divisor.equals(n)) {
                resultTextArea.setText("Делитель не найден.");
            } else {
                resultTextArea.setText("Найден нетривиальный делитель: " + divisor);
            }

        } catch (NumberFormatException e) {
            resultTextArea.setText("Ошибка: Введите корректные значения.");
        } catch (Exception e) {
            resultTextArea.setText("Ошибка: Неправильный формат функции.");
        }
    }

    private static PollardsRhoAlgorithm initAlgoritm(JexlExpression expression, BigInteger n, BigInteger c) {
        JexlContext context = new MapContext();

        PollardsRhoAlgorithm.Function f = (x) -> {
            context.set("x", x.doubleValue());
            context.set("Math", Math.class);
            Object result = expression.evaluate(context);
            if (result instanceof Long l) {
                return BigInteger.valueOf(l).mod(n);
            } else if (result instanceof Double d) {
                return BigInteger.valueOf((d).longValue()).mod(n);
            } else {
                throw new RuntimeException("Неподдерживаемый тип результата: " + result.getClass());
            }
        };

        PollardsRhoAlgorithm pollardsRho = new PollardsRhoAlgorithm(n, c, f);
        return pollardsRho;
    }
}