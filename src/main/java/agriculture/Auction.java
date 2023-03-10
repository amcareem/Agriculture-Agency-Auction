/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture;
import agriculture.Users.User;

import java.util.HashMap;

public class Auction {

    private int ID ;
    private String date;
    private double reservedPrice;

    private HashMap<User,Product> auction = new HashMap<>();

    public HashMap<User, Product> getAuction() {
        return auction;
    }
    public void setAuction(HashMap<User, Product> auction) {
        this.auction = auction;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        date = date;
    }

   public double getReservedPrice() {
        return reservedPrice;
    }

    public void setReservedPrice(int reservedPrice) {
        this.reservedPrice = reservedPrice;
    }

    public Auction(String date) {
        this.date = date;
    }
    public Auction(){

    }
}
