/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture;
import java.sql.*;
import java.util.ArrayList;

import agriculture.Users.*;
import agriculture.Controllers.SessionSelectionController;
import org.sqlite.*;
public class DatabaseConnection {
    static final SQLiteDataSource dataSource = new SQLiteDataSource();
    static Connection con;
    static Statement stmt;

    public static void createDB(){
        try{
            dataSource.setUrl("jdbc:sqlite:Auction.db");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public static void createDefaultTable(){
        String userQuery = "CREATE TABLE IF NOT EXISTS Users ( "+
                "ID INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "USERNAME TEXT, "
                +"SURNAME TEXT, "
                +"PASSWORD TEXT, "
                +"CONTACTNO BIGINT, "
                +"ADDRESS TEXT, " +
                "USERTYPE TEXT" +
                ")";
        String auctionQuery= "CREATE TABLE IF NOT EXISTS Auction("+
                "PRODUCT TEXT,  "
                +"PRICE DOUBLE, "
                +"BUYER TEXT, "
                +"USERTYPE TEXT"+
                ")";

        String auctionSessions = "CREATE TABLE IF NOT EXISTS Sessions("+
                "AUCTIONID INTEGER PRIMARY KEY AUTOINCREMENT, "
                +"DATE TEXT "+
                ")";
        String productsTable = "CREATE TABLE IF NOT EXISTS Products("+
                "NAME TEXT, "
                +"PRICE DOUBLE, "
                +"AUCTIONID INTEGER, "
                +"PRODUCTID INTEGER PRIMARY KEY AUTOINCREMENT,"
                +"PRODUCTSELLERID INTEGER"+
                ")";
        String bidderTable = "CREATE TABLE IF NOT EXISTS Bidders("+
                "PRODUCTBIDDERID INTEGER, "
                +"PRODUCTID INTEGER, "
                +"BUYERBID DOUBLE, "
                +"BIDID INTEGER PRIMARY KEY AUTOINCREMENT"+
                ")";
        try{
            con = dataSource.getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(userQuery);
            stmt.executeUpdate(auctionQuery);
            stmt.executeUpdate(auctionSessions);
            stmt.executeUpdate(productsTable);
            stmt.executeUpdate(bidderTable);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    public static void deleteAuction(int auctionID){
        String query = "DELETE FROM Sessions WHERE AUCTIONID = "+auctionID;
        String deleteProduct ="DELETE FROM Products WHERE AUCTIONID = "+auctionID;

        try{
            con = dataSource.getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            stmt.executeUpdate(deleteProduct);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public static void deleteUser(User user){
        String query = "DELETE FROM Users WHERE ID = "+user.getID();
        String deleteProduct = "DELETE FROM Products WHERE PRODUCTSELLERID = "+user.getID();
        String deleteBid = "DELETE FROM Bidders WHERE PRODUCTBIDDERID = "+user.getID();
        try{
            con = dataSource.getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
            stmt.executeUpdate(deleteBid);
            stmt.executeUpdate(deleteProduct);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public static void editUser(User user){
        String query = "UPDATE Users SET USERNAME = '"+user.getName()+"', SURNAME = '"+user.getSurname()+"', PASSWORD = '"+user.getPassword()+"', CONTACTNO = "+user.getContactNo()+", ADDRESS = '"+user.getAddress()+"' WHERE ID = "+user.getID();
        tryConnection(query);
    }
    public static ArrayList<User> getUsersToList(User user){
        ArrayList<User> users = new ArrayList<>();
        String query = "SELECT * FROM Users";
        int id;
        String username,surname,password,address,usertype = null;
        long contactNo = 0;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                id = rs.getInt("ID");
                username = rs.getString("USERNAME");
                surname = rs.getString("SURNAME");
                password = rs.getString("PASSWORD");
                address = rs.getString("ADDRESS");
                usertype = rs.getString("USERTYPE");
                contactNo = rs.getLong("CONTACTNO");
                User user1 = new User(id,username,surname,password,contactNo,address);
                users.add(user1);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return users;
    }
    public static ArrayList<Bid> getBidsToList(int selectedIndex){
        ArrayList<Bid> bids = new ArrayList<>();
        String query = "SELECT * FROM Bidders WHERE PRODUCTID = "+selectedIndex;
        int productID,auctionID = 0;
        double bidPrice = 0;
        Buyer buyer = null;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                bidPrice = rs.getDouble("BUYERBID");
                productID = rs.getInt("PRODUCTID");
                buyer = (Buyer) getUser(rs.getInt("PRODUCTBIDDERID"));
                auctionID = SessionSelectionController.ID;
                Bid bid = new Bid(rs.getInt("PRODUCTBIDDERID"),productID,bidPrice);
                bid.setBidID(rs.getInt("BIDID"));
                bids.add(bid);
            }
        }catch(SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return bids;
    }
    public static void addBidToList(Bid bid){
        String query = "INSERT INTO Bidders(PRODUCTID,BUYERBID,PRODUCTBIDDERID) VALUES("+""+bid.getProduct().getID()+","+ bid.getBuyerBid()+",";
        if(bid.getBuyer() instanceof Miller){
            query += "'"+((Miller) bid.getBuyer()).getID()+"')";
        }
        if(bid.getBuyer() instanceof FarmingAgency){
            query += "'"+((FarmingAgency) bid.getBuyer()).getID()+"')";
        }
        if(bid.getBuyer() instanceof AgricultureAgency){
            query += "'"+((AgricultureAgency) bid.getBuyer()).getID()+"')";
        }
        tryConnection(query);
    }
    public static ArrayList<Product> getProductsToList(){
        ArrayList<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products WHERE AUCTIONID = "+ SessionSelectionController.ID;
        String name;
        double price;
        int id;
        Seller seller;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                name = rs.getString("NAME");
                price = rs.getDouble("PRICE");
                id = rs.getInt("PRODUCTID");
                seller = (Seller) getUser(rs.getInt("PRODUCTSELLERID"));

                Product product1 = new Product(name,price,id,seller);
                products.add(product1);
            }
        }catch(SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return products;
    }
    public static void addProductToList(Product product){
        String query = "INSERT INTO Products(NAME,PRICE,AUCTIONID,PRODUCTSELLERID) VALUES("+"'"+product.getName()+"',"+product.getPrice()+","+ SessionSelectionController.ID+",";
        if(product.getSeller() instanceof Farmer){
            query+= "'"+((Farmer)product.getSeller()).getID()+"')";
        }
        if(product.getSeller() instanceof FarmingAgency){
            query+= "'"+((FarmingAgency)product.getSeller()).getID()+"')";
        }
        if(product.getSeller() instanceof Miller){
            query+= "'"+((Miller)product.getSeller()).getID()+"')";
        }
        tryConnection(query);
    }
    public static ArrayList<Auction> getSessions(){
        ArrayList<Auction> auctions = new ArrayList<>();
        String query = "SELECT AUCTIONID,DATE FROM Sessions";
        String date = null;
        int id = 0;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                date = rs.getString("DATE");
                id = rs.getInt("AUCTIONID");
                Auction auction = new Auction(date);
                auction.setID(id);
                auctions.add(auction);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return auctions;
    }
    public static void addSession(String date){
        String query = "INSERT INTO Sessions (DATE) VALUES ("+"'"+date+"')";
        tryConnection(query);
    }
    public static void addToAuction(User user,Product product){
        String userType = null;
        if(user instanceof Miller)
            userType = "Miller";
        if(user instanceof Farmer)
            userType = "Farmer";
        if(user instanceof FarmingAgency)
            userType = "Farming Agency";
        if( user instanceof AgricultureAgency)
            userType = "Agriculture Agency";
        String query = "INSERT INTO Auction (PRODUCT,PRICE,BUYER,USERTYPE) VALUES ("+"'"+product.getName()+"',"+product.getPrice()+",'"+user.getName()+"','"+userType+"')";
        tryConnection(query);
    }
    public static void addUser(User user){
        String userType = null;
        if(user instanceof Miller)
            userType = "Miller";
        if(user instanceof Farmer)
            userType = "Farmer";
        if(user instanceof FarmingAgency)
            userType = "Farming Agency";
        if( user instanceof AgricultureAgency)
            userType = "Agriculture Agency";

        String query = "INSERT INTO Users (USERNAME,SURNAME,PASSWORD,CONTACTNO,ADDRESS,USERTYPE) VALUES ("+"'"+user.getName() +"','"+user.getSurname()+ "','"+user.getPassword()
                +"',"+ user.getContactNo()+",'" + user.getAddress()+ "','"+userType+"')";

        tryConnection(query);
    }
    public static ArrayList<User> getUsers(){
        String query = "SELECT * FROM Users";
        ArrayList<User> users = new ArrayList<>();
        tryConnection(query);
        return users;
    }
    public static int getLastBidID(){
        String query = "SELECT BIDID From Bidders";

        int id = 0;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                id = rs.getInt("BIDID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }
    public static int getLastID(){
        String query = "SELECT ID FROM Users";
        int id = -1;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                id = rs.getInt("ID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }
    public static User getUser(int ID, String password){
        String query = "SELECT * FROM Users WHERE ID = "+ID+" AND PASSWORD = '"+password+"'";

        try{
            con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.isClosed())
                return null;
            rs.next();
            String type = rs.getString("USERTYPE");
            switch(type){
                case "Farmer" -> {
                    return new Farmer(rs.getInt("ID"),rs.getString("USERNAME"),rs.getString("SURNAME"),
                            rs.getString("PASSWORD"),rs.getLong("CONTACTNO"),rs.getString("ADDRESS"));
                }
                case "Miller" ->{
                    return new Miller(rs.getInt("ID"),rs.getString("USERNAME"),rs.getString("SURNAME"),
                            rs.getString("PASSWORD"),rs.getLong("CONTACTNO"),rs.getString("ADDRESS"));
                }
                case "Farming Agency" ->{
                    return new FarmingAgency(rs.getInt("ID"),rs.getString("USERNAME"),rs.getString("SURNAME"),
                            rs.getString("PASSWORD"),rs.getLong("CONTACTNO"),rs.getString("ADDRESS"));
                }
                case "Agriculture Agency" ->{
                    return new AgricultureAgency(rs.getInt("ID"),rs.getString("USERNAME"),rs.getString("SURNAME"),
                            rs.getString("PASSWORD"),rs.getLong("CONTACTNO"),rs.getString("ADDRESS"));
                }

            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static User getUser(int ID){
        String query = "SELECT * FROM Users WHERE ID = "+ID;

        try{
            con = dataSource.getConnection();
            PreparedStatement preparedStatement = con.prepareStatement(query);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.isClosed())
                return null;
            rs.next();
            String type = rs.getString("USERTYPE");
            switch(type){
                case "Farmer" -> {
                    return new Farmer(rs.getInt("ID"),rs.getString("USERNAME"),rs.getString("SURNAME"),
                            rs.getString("PASSWORD"),rs.getLong("CONTACTNO"),rs.getString("ADDRESS"));
                }
                case "Miller" ->{
                    return new Miller(rs.getInt("ID"),rs.getString("USERNAME"),rs.getString("SURNAME"),
                            rs.getString("PASSWORD"),rs.getLong("CONTACTNO"),rs.getString("ADDRESS"));
                }
                case "Farming Agency" ->{
                    return new FarmingAgency(rs.getInt("ID"),rs.getString("USERNAME"),rs.getString("SURNAME"),
                            rs.getString("PASSWORD"),rs.getLong("CONTACTNO"),rs.getString("ADDRESS"));
                }
                case "Agriculture Agency" ->{
                    return new AgricultureAgency(rs.getInt("ID"),rs.getString("USERNAME"),rs.getString("SURNAME"),
                            rs.getString("PASSWORD"),rs.getLong("CONTACTNO"),rs.getString("ADDRESS"));
                }
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
    public static int getProductID(){
        String query = "SELECT PRODUCTID FROM Products";
        int id = -1;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while (rs.next()){
                id = rs.getInt("PRODUCTID");
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return id;
    }
    public static Product getSpesificProduct(int ID){
        Product product = null;
        String query = "SELECT * FROM Products WHERE PRODUCTID = "+ID;
        double price;
        int productID;
        try{
            con = dataSource.getConnection();
            PreparedStatement statement = con.prepareStatement(query);
            ResultSet rs = statement.executeQuery();
            while(rs.next()){
                price = rs.getDouble("PRICE");
                productID = rs.getInt("PRODUCTID");
                product = new Product(productID,price);
                product.setName(rs.getString("NAME"));
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
        return product;
    }
    private static void tryConnection(String query) {
        try{
            con = dataSource.getConnection();
            stmt = con.createStatement();
            stmt.executeUpdate(query);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
