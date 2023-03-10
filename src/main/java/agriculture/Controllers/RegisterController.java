/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture.Controllers;

import agriculture.DatabaseConnection;
import agriculture.Users.*;
import agriculture.Main;
import agriculture.Users.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.util.converter.IntegerStringConverter;
import javafx.util.converter.LongStringConverter;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RegisterController implements Initializable {

    public TableColumn id;
    public TableColumn username;
    public TableColumn surname;
    public TableColumn password;
    public TableColumn contactNo;
    public TableColumn address;

    public ComboBox comboBox;
    public Button registerButton;
    public TextField txtName;
    public TextField txtSurname;
    public TextField txtPassword;
    public TextField txtContact;
    public TextField txtAddress;
    public User user;
    public Button btnCreateSession;
    public Button backButton;
    public TableView<User> tableUsers;
    public Button btnCloseAuction;
    public ImageView imageView;

    public void createSession(ActionEvent actionEvent) {
        createSessionButton();
    }
    public void createSessionButton(){
        String date = null;
        TextInputDialog sessionDate = new TextInputDialog("Date");
        sessionDate.setTitle("Auction Session Creation");
        sessionDate.setHeaderText("Create a date of the session Format(dd.mm.yy)");
        sessionDate.setContentText("Enter Session Date: ");
        Optional<String> result = sessionDate.showAndWait();

        if(result.isPresent()){
            date = result.get();
        }
        DatabaseConnection.addSession(date);
    }
    public void registerButton(ActionEvent actionEvent) throws IOException {
        User user = register();
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Register Successful!");
        alert.setHeaderText("You have successfully registered");
        alert.setContentText("ID: "+user.getID()+" name: "+user.getName()+" password: "+user.getPassword()+" surname: "+user.getSurname()+
                " Contact no: "+user.getContactNo()+" address: "+user.getAddress());
        alert.showAndWait();
        fillTable();
    }
    public User register(){
        String name = txtName.getText();
        String surname = txtSurname.getText();
        String password = txtPassword.getText();
        Long contact = Long.valueOf(txtContact.getText());
        String address = txtAddress.getText();

        User user = new User(name,surname,password,contact,address);
        switch(comboBox.getSelectionModel().getSelectedIndex()){
            case 0 -> user = new Farmer(name,surname,password,contact,address);
            case 1 -> user = new Miller(name,surname,password,contact,address);
            case 2 -> user = new FarmingAgency(name,surname,password,contact,address);
            case 3 -> user = new AgricultureAgency(name,surname,password,contact,address);
        }
        DatabaseConnection.addUser(user);
        user.setID(DatabaseConnection.getLastID());
        return user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        File file = new File("form.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        comboBox.getItems().addAll("Farmer","Miller","Farming Agency","Agriculture Agency");
        comboBox.getSelectionModel().select("Farmer");
    }

    public void backButtonAction(ActionEvent actionEvent) {
        Main.enterScene("login",575,450,backButton);
    }
    public void btnLoadAction(ActionEvent actionEvent) {
        fillTable();
    }
    public void fillTable(){
        tableUsers.getColumns().clear();
        tableUsers.getItems().clear();
        tableUsers.setEditable(true);
        id = new TableColumn("ID");

        username = new TableColumn("Name");
        surname = new TableColumn("Surname");
        password = new TableColumn("Password");
        contactNo = new TableColumn("ContactNo");
        address = new TableColumn("Address");

        tableUsers.getColumns().addAll(id, username,surname, password,contactNo,address);

        final ObservableList<User> data = FXCollections.observableArrayList(
                DatabaseConnection.getUsersToList(user)
        );
        id.setCellValueFactory(new PropertyValueFactory<>("ID"));
        id.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        id.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User,Integer> cellEditEvent) {
                User user = cellEditEvent.getRowValue();
                user.setID(cellEditEvent.getNewValue());
                DatabaseConnection.editUser(user);
            }
        });
        username.setCellValueFactory(new PropertyValueFactory<>("name"));
        username.setCellFactory(TextFieldTableCell.forTableColumn());
        username.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User,String> cellEditEvent) {
                User user = cellEditEvent.getRowValue();
                user.setName(cellEditEvent.getNewValue());
                DatabaseConnection.editUser(user);
            }
        });

        surname.setCellValueFactory(new PropertyValueFactory<>("surname"));
        surname.setCellFactory(TextFieldTableCell.forTableColumn());
        surname.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User,String> cellEditEvent) {
                User user = cellEditEvent.getRowValue();
                user.setSurname(cellEditEvent.getNewValue());
                DatabaseConnection.editUser(user);
            }
        });

        password.setCellValueFactory(new PropertyValueFactory<>("password"));
        password.setCellFactory(TextFieldTableCell.forTableColumn());
        password.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User,String> cellEditEvent) {
                User user = cellEditEvent.getRowValue();
                user.setPassword(cellEditEvent.getNewValue());
                DatabaseConnection.editUser(user);
            }
        });

        contactNo.setCellValueFactory(new PropertyValueFactory<>("contactNo"));
        contactNo.setCellFactory(TextFieldTableCell.forTableColumn(new LongStringConverter()));
        contactNo.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,Long>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User,Long> cellEditEvent) {
                User user = cellEditEvent.getRowValue();
                user.setContactNo(cellEditEvent.getNewValue());
                DatabaseConnection.editUser(user);
            }
        });

        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        address.setCellFactory(TextFieldTableCell.forTableColumn());
        address.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<User,String>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<User,String> cellEditEvent) {
                User user = cellEditEvent.getRowValue();
                user.setAddress(cellEditEvent.getNewValue());
                DatabaseConnection.editUser(user);
            }
        });

        for (User user : data) {
            tableUsers.getItems().add(user);
        }
    }
    public void btnDeleteAction(ActionEvent actionEvent) {
        if(tableUsers.getSelectionModel().getSelectedIndex() != -1){
            User user = tableUsers.getSelectionModel().getSelectedItems().get(0);
            DatabaseConnection.deleteUser(user);
            fillTable();
        }

    }

    public void btnCloseAuctionAction(ActionEvent actionEvent) {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Close Auction");
        dialog.setHeaderText("Close an Auction");

        ButtonType enter = new ButtonType("Close Auction", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(enter,ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));
        TextField id = new TextField();
        id.setPromptText("ID");

        grid.add(new Label("Enter Auction ID:"), 0, 0);
        grid.add(id, 1, 0);
        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == enter) {
                int idToDatabase = Integer.parseInt(id.getText());
                DatabaseConnection.deleteAuction(idToDatabase);
            }
            return null;
        });
        Optional<String> result = dialog.showAndWait();

    }
}
