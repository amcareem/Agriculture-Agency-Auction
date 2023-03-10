/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture.Controllers;

import agriculture.Auction;
import agriculture.DatabaseConnection;
import agriculture.Main;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class SessionSelectionController implements Initializable {
    public static int ID = 0;
    public TableColumn auctionID;
    public TableColumn auctionDate;
    public Button btnBack;
    public ImageView imageView;
    @FXML
    private Button enterButton;

    @FXML
    private TableView<Auction> sessionTable;

    @FXML
    void enterButtonAction(ActionEvent event) {
        ID = sessionTable.getSelectionModel().getSelectedItem().getID();
       Main.enterScene("auctionMenu",750,584,enterButton);
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle){
        File file = new File("auction.png");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);
        sessionTable.getColumns().clear();

        auctionID = new TableColumn("Auction ID");
        auctionDate = new TableColumn("Auction Date");

        sessionTable.getColumns().addAll(auctionID,auctionDate);

        final ObservableList<Auction> data = FXCollections.observableArrayList(
                DatabaseConnection.getSessions()
        );

        auctionID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        auctionDate.setCellValueFactory(new PropertyValueFactory<>("date"));

        for(Auction auction : data){
            sessionTable.getItems().add(auction);
        }
    }

    public void btnBackAction(ActionEvent actionEvent) {
        Main.enterScene("login",575,450,btnBack);
    }
}


