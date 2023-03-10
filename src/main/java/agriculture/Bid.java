/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture;

import agriculture.Users.AgricultureAgency;
import agriculture.Users.FarmingAgency;
import agriculture.Users.Miller;

public class Bid {
    private int productID;
    private int auctionID;
    private double buyerBid;
    private String buyerName;
    private String buyerType;
    private Buyer buyer;
    private Product product;
    private int bidID;
    private String name;

    public Bid(int selectedProductID, double price) {
        this.productID = selectedProductID;
        this.buyerBid = price;
    }

    public Bid(int BuyerID,int selectedProductID, double price) {
        this.product = DatabaseConnection.getSpesificProduct(selectedProductID);
        this.productID = selectedProductID;
        this.name = product.getName();
        this.buyer = (Buyer) DatabaseConnection.getUser(BuyerID);
        this.buyerBid = price;
        if (buyer instanceof Miller){
            this.buyerName = ((Miller) buyer).getName();
            this.buyerType = "Miller";
        }
        if (buyer instanceof FarmingAgency){
            this.buyerName = ((FarmingAgency) buyer).getName();
            this.buyerType = "Farming Agency";
        }
        if (buyer instanceof AgricultureAgency){
            this.buyerName = ((AgricultureAgency) buyer).getName();
            this.buyerType = "Agriculture Agency";
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getBidID() {
        return bidID;
    }

    public void setBidID(int bidID) {
        this.bidID = bidID;
    }

    public Buyer getBuyer() {
        return buyer;
    }

    public void setBuyer(Buyer buyer) {
        this.buyer = buyer;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public int getAuctionID() {
        return auctionID;
    }

    public void setAuctionID(int auctionID) {
        auctionID = auctionID;
    }

    public double getBuyerBid() {
        return buyerBid;
    }

    public void setBuyerBid(double buyerBid) {
        this.buyerBid = buyerBid;
    }

    public String getBuyerName() {
        return buyerName;
    }

    public void setBuyerName(String buyerName) {
        this.buyerName = buyerName;
    }

    public String getBuyerType() {
        return buyerType;
    }

    public void setBuyerType(String buyerType) {
        this.buyerType = buyerType;
    }

    public Bid(int productID,int auctionID, double buyerBid, Buyer buyer) {
        this.productID= productID;
        this.auctionID = auctionID;
        this.buyerBid = buyerBid;
        this.buyer = buyer;
        if(buyer instanceof Miller){
            buyerType = "Miller";
            this.buyerName = ((Miller) buyer).getName();
        }
        if(buyer instanceof FarmingAgency){
            buyerType = "Farming Agency";
            this.buyerName = ((FarmingAgency) buyer).getName();
        }
        if(buyer instanceof AgricultureAgency){
            buyerType = "Agriculture Agency";
            this.buyerName = ((AgricultureAgency) buyer).getName();
        }
    }
}
