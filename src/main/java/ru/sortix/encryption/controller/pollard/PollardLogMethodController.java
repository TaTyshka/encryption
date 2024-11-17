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
import ru.sortix.encryption.algorithm.pollard.PollardFunction;
import ru.sortix.encryption.algorithm.pollard.PollardLogMethod;

import java.math.BigInteger;

public class PollardLogMethodController {

    @FXML
    private TextField inputP;

    @FXML
    private TextField inputA;

    @FXML
    private TextField inputB;

    @FXML
    private TextField inputR;

    @FXML
    private TextField inputFunction;

    @FXML
    private TextArea resultTextArea;

    @FXML
    private TableView<PollardLogMethod.Step> stepsTable;

    @FXML
    private TableColumn<PollardLogMethod.Step, Integer> columnI;
    @FXML
    private TableColumn<PollardLogMethod.Step, String> columnC;
    @FXML
    private TableColumn<PollardLogMethod.Step, String> columnLogC;
    @FXML
    private TableColumn<PollardLogMethod.Step, String> columnD;
    @FXML
    private TableColumn<PollardLogMethod.Step, String> columnLogD;

    private ObservableList<PollardLogMethod.Step> steps = FXCollections.observableArrayList();

    @FXML
    public void initialize() {
        columnI.setCellValueFactory(new PropertyValueFactory<>("i"));
        columnC.setCellValueFactory(new PropertyValueFactory<>("c"));
        columnLogC.setCellValueFactory(new PropertyValueFactory<>("logC"));
        columnD.setCellValueFactory(new PropertyValueFactory<>("d"));
        columnLogD.setCellValueFactory(new PropertyValueFactory<>("logD"));

        stepsTable.setItems(steps);
    }

    @FXML
    public void startPollardPMethod() {
        try {
            BigInteger p = new BigInteger(inputP.getText());
            BigInteger a = new BigInteger(inputA.getText());
            BigInteger b = new BigInteger(inputB.getText());
            BigInteger r = new BigInteger(inputR.getText());

            PollardLogMethod pollardP = new PollardLogMethod(p, a, b, r);
            steps.clear();

            String functionInput = inputFunction.getText();
            JexlEngine jexl = new JexlBuilder().create();
            JexlExpression expression = jexl.createExpression(functionInput);
            JexlContext context = new MapContext();
            PollardFunction f = c -> {
                context.set("c", c.doubleValue());
                context.set("Math", Math.class);
                Object result = expression.evaluate(context);
                if (result instanceof Integer i) {
                    return BigInteger.valueOf(i);
                } else if (result instanceof Long l) {
                    return BigInteger.valueOf(l);
                } else if (result instanceof Double d) {
                    return BigInteger.valueOf((d).longValue());
                } else {
                    throw new RuntimeException("Неподдерживаемый тип результата: " + result.getClass());
                }
            };

            BigInteger result = pollardP.solve(f, steps);

            if (result != null) {
                resultTextArea.setText("Результат: x = " + result);
            } else {
                resultTextArea.setText("Решений нет.");
            }
        } catch (Exception e) {
            resultTextArea.setText("Ошибка: Неправильный ввод.");
            e.printStackTrace();
        }
    }
}