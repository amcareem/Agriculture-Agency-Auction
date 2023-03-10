/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public AnchorPane pane;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("login.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 575, 450);
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        Image icon = new Image("file:bid.png");
        stage.getIcons().add(icon);
        stage.show();
    }

    public static void main(String[] args) {
        DatabaseConnection.createDB();
        DatabaseConnection.createDefaultTable();
        launch();
    }
    public static void enterScene(String sceneName, int v, int v1, Button buttonName){
        Stage stage = (Stage) buttonName.getScene().getWindow();
        Scene scene = null;
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource(sceneName+".fxml"));
        try {
            scene = new Scene(fxmlLoader.load(), v, v1, Color.BEIGE);
        } catch (IOException e) {
            e.printStackTrace();
        }
        stage.setTitle("Welcome!");
        stage.setScene(scene);
        Image icon = new Image("file:bid.png");
        stage.getIcons().add(icon);
        stage.show();
    }
}