package ru.sortix.encryption;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class EncryptionsApplication extends Application {
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        mainStage = primaryStage;
        primaryStage.getIcons().add(new Image(Objects.requireNonNull(EncryptionsApplication.class.getResourceAsStream("/icon.png"))));
        setRoot("encryption-view");
        primaryStage.setTitle("Шифрование");
        primaryStage.show();
    }

    public static void setRoot(String fxml) throws IOException {

        FXMLLoader loader = new FXMLLoader(EncryptionsApplication.class.getResource(fxml + ".fxml"));
        Scene scene = new Scene(loader.load(), 600, 500);
        mainStage.setScene(scene);
    }

    public static void main(String[] args) {
        launch(args);
    }
}