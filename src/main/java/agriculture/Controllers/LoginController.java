/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture.Controllers;

import agriculture.DatabaseConnection;
import agriculture.Users.User;
import agriculture.Main;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    public TextField txtID;
    public Button btnLogin;
    public PasswordField txtPassword;
    public static User user = null;
    public AnchorPane backgroundPane;
    public ImageView imageView;

    public void loginbutton(){
        User user = login();
        if(user!=null){
            this.user = user;
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Login Successful!");
            alert.setHeaderText("You have successfully logged in");
            alert.showAndWait();
            Main.enterScene("sessionSelection",645,469,btnLogin);
        }else {
            String name = txtID.getText();
            String password = txtPassword.getText();
            if(name.equals("admin") && password.equals("admin")){
                Main.enterScene("registration",600,600,btnLogin);
                return;
            }
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setHeaderText("You could not log in.");
            alert.showAndWait();
        }
    }
    public User login(){
        String name = txtID.getText();
        String password = txtPassword.getText();
        if(name.equals("admin") && password.equals("admin")) return null;

        int id = Integer.parseInt(name);
        return DatabaseConnection.getUser(id,password);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("bid.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

    }
}
