/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture;

import agriculture.Users.Farmer;
import agriculture.Users.FarmingAgency;
import agriculture.Users.Miller;

public class Product {

    private String name;
    private double price;
    private int ID;
    private Seller seller;
    private String sellerType;
    private String sellerName;

    public String getSellerType() {
        return sellerType;
    }

    public void setSellerType(String sellerType) {
        this.sellerType = sellerType;
    }

    public String getSellerName() {
        return sellerName;
    }

    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    public Seller getSeller() {
        return seller;
    }

    public void setSeller(Seller seller) {
        this.seller = seller;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public Product( int ID,double price) {
        this.price = price;
        this.ID = ID;
    }

    public Product(String name, double price, int ID) {
        this.name = name;
        this.price = price;
        this.ID = ID;
    }
    public Product(String name, double price, int ID, Seller seller){
        this.name = name;
        this.price = price;
        this.ID = ID;
        this.seller = seller;
        if(seller instanceof Farmer){
            sellerType = "Farmer";
            this.sellerName = ((Farmer) seller).getName();
        }
        if(seller instanceof Miller){
            sellerType = "Miller";
            this.sellerName = ((Miller) seller).getName();
        }
        if(seller instanceof FarmingAgency){
            sellerType = "Farming Agency";
            this.sellerName = ((FarmingAgency) seller).getName();
        }
    }
    public Product(String name, double price,Seller seller){
        this.name = name;
        this.price = price;
        this.seller = seller;
        if(seller instanceof Farmer){
            sellerType = "Farmer";
            this.sellerName = ((Farmer) seller).getName();
        }
        if(seller instanceof Miller){
            sellerType = "Miller";
            this.sellerName = ((Miller) seller).getName();
        }
        if(seller instanceof FarmingAgency){
            sellerType = "Farming Agency";
            this.sellerName = ((FarmingAgency) seller).getName();
        }
    }
}
