/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture.Controllers;

import agriculture.*;
import agriculture.Users.AgricultureAgency;
import agriculture.Users.FarmingAgency;
import agriculture.Users.Miller;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class AuctionMenuController implements Initializable {

    public TableColumn productName;
    public TableColumn productPrice;
    public TableColumn productSeller;
    public TableColumn productType;
    public TableColumn productBidder;
    public TableColumn productID;
    public TableColumn bidID;
    public TableColumn bidderType;

    public Label lblHighestPaidUser;
    public Button buttonBack;

    private int selectedProductID = 0;
    private double selectedProductPrice = 0;
    private String selectedProductSellerType = null;
    @FXML
    private Button btnBid;

    @FXML
    private Button btnSell;

    @FXML
    private Label lblLoggedUser;

    @FXML
    private TableView<Product> tableAuction;

    @FXML
    private TableView<Bid> tableSpesific;

    public void bidProduct(){
        double price = 0;
        TextInputDialog dialog = new TextInputDialog("Price");
        dialog.setTitle("Bid Product");
        dialog.setHeaderText("Enter Product Price To Bid.");
        dialog.setContentText("Bid Price: ");

        Optional<String> result = dialog.showAndWait();
        if(result.isPresent()){
            price = Double.parseDouble(result.get());
            if(price< selectedProductPrice){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText("Bidding price must be higher than the initial price.");
                alert.showAndWait();
            }
            else{
                Bid bid = new Bid(LoginController.user.getID(),selectedProductID,price);
                DatabaseConnection.addBidToList(bid);
                bid.setBidID(DatabaseConnection.getLastBidID());
                tableSpesific.getItems().add(bid);
            }
        }
    }
    public void sellProduct(){
        Dialog<Pair<String,Double>> dialog = new Dialog<>();
        dialog.setTitle("Product Info");
        dialog.setHeaderText("Enter Product Info To Put Up For Auction");
        ButtonType sellOk = new ButtonType("Confirm", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(sellOk,ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20,150,10,10));

        TextField productName = new TextField();
        productName.setPromptText("Product Name: ");
        TextField productPrice = new TextField();
        productPrice.setPromptText("Product Price: ");

        grid.add(new Label("Product Name: "),0,0);
        grid.add(productName,1,0);
        grid.add(new Label("Product Price: "),0,1);
        grid.add(productPrice,1,1);

        dialog.getDialogPane().setContent(grid);

        //Convert the result to a product name- price pair when the sell button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if(dialogButton == sellOk){
                return new Pair<String,Double>(productName.getText(),Double.parseDouble(productPrice.getText()));
            }
            return null;
        });
        Optional<Pair<String, Double>> result = dialog.showAndWait();
       final Product[] product = new Product[1];
        result.ifPresent(productNamePrice -> {
            product[0] = new Product(productNamePrice.getKey(),productNamePrice.getValue(),(Seller) LoginController.user);
        });
         DatabaseConnection.addProductToList(product[0]);
         product[0].setID(DatabaseConnection.getProductID());
         tableAuction.getItems().add(product[0]);

    }
    public void sellAction(ActionEvent actionEvent) {
        sellProduct();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        if (!(LoginController.user  instanceof Buyer))
            btnBid.setDisable(true);
        if (!(LoginController.user  instanceof  Seller))
            btnSell.setDisable(true);
        String userType = LoginController.user.getClass().toString();
        String[] userTypeArray = userType.split(Pattern.quote("."));
        String part = userTypeArray[2];
        //
        //
        //
        //
        //
        //
        //4
        lblLoggedUser.setText("Logged in user is: "+LoginController.user.getName()+" User Type: "+part);
        tableAuction.getColumns().clear();

        productName = new TableColumn("Product");
        productPrice = new TableColumn("Price");
        productID = new TableColumn("Product ID");
        productSeller = new TableColumn("Seller");
        productType = new TableColumn("Type");

        tableAuction.getColumns().addAll(productID, productName, productPrice, productSeller, productType);

        final ObservableList<Product> data = FXCollections.observableArrayList(
                DatabaseConnection.getProductsToList()
        );
        productID.setCellValueFactory(new PropertyValueFactory<>("ID"));
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        productSeller.setCellValueFactory(new PropertyValueFactory<>("sellerName"));
        productType.setCellValueFactory(new PropertyValueFactory<>("sellerType"));

        for (Product product : data) {
            tableAuction.getItems().add(product);
        }
        //-----------------------------------------------------------
        tableSpesific.getColumns().clear();

        bidID = new TableColumn("Bid ID");
        productName = new TableColumn("Product");
        productPrice = new TableColumn("Price");
        productBidder = new TableColumn("Bidder");
        bidderType = new TableColumn("Type");

        tableSpesific.getColumns().addAll(bidID, productName, productPrice, productBidder, bidderType);
        //-----------------------------------------------------------
        tableAuction.addEventHandler(MouseEvent.MOUSE_CLICKED,(e) -> {
            Product product = tableAuction.getSelectionModel().getSelectedItems().get(0);
            selectedProductID = product.getID();
            selectedProductPrice = product.getPrice();
            selectedProductSellerType = product.getSellerType();
            tableSpesific.getItems().clear();

            double highestprice = 0;
            String buyerName = null;
            for(Bid bid : DatabaseConnection.getBidsToList(selectedProductID)){
                if(highestprice<bid.getBuyerBid()){
                    highestprice = bid.getBuyerBid();
                    buyerName = bid.getBuyerName();
                }
            }
            if(highestprice!=0) {
                lblHighestPaidUser.setVisible(true);
                lblHighestPaidUser.setText("Highest bidder in this auction is: "+buyerName+" Offered Price: "+highestprice);
            }
            else
                lblHighestPaidUser.setVisible(false);
            final ObservableList<Bid> dataToBidList = FXCollections.observableArrayList(
                    DatabaseConnection.getBidsToList(selectedProductID)
            );
            bidID.setCellValueFactory(new PropertyValueFactory<>("bidID"));
            productName.setCellValueFactory(new PropertyValueFactory<>("name"));
            productPrice.setCellValueFactory(new PropertyValueFactory<>("buyerBid"));
            productBidder.setCellValueFactory(new PropertyValueFactory<>("buyerName"));
            bidderType.setCellValueFactory(new PropertyValueFactory<>("buyerType"));

            for (Bid bid : dataToBidList) {
                tableSpesific.getItems().add(bid);

            }
            if(LoginController.user instanceof Miller){
                if(selectedProductSellerType.equals("Miller") || selectedProductSellerType.equals("Farming Agency") || selectedProductSellerType.equals("Agriculture Agency"))
                    btnBid.setDisable(true);
            }
            if(LoginController.user instanceof FarmingAgency){
                if(selectedProductSellerType.equals("Farming Agency") || selectedProductSellerType.equals("Farmer") || selectedProductSellerType.equals("Agriculture Agency"))
                    btnBid.setDisable(true);
                else
                    btnBid.setDisable(false);
            }
            if(LoginController.user instanceof AgricultureAgency){
                if(selectedProductSellerType.equals("Agriculture Agency") || selectedProductSellerType.equals("Farmer") || selectedProductSellerType.equals("Miller"))
                    btnBid.setDisable(true);
                else{
                    btnBid.setDisable(false);
                }
            }
        });
    }
    public void bidAction(ActionEvent actionEvent) {
        bidProduct();
    }

    public void buttonBackAction(ActionEvent actionEvent) {
        Main.enterScene("login",575,450,buttonBack);
    }
}

