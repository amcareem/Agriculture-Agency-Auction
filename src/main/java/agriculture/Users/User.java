/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture.Users;

public class User {
    private int ID;
    private String name;
    private String surname;
    private String password;
    private Long contactNo;
    private String address;

    public void setID(int ID) {
        this.ID = ID;
    }


    public User(String name, String surname, String password, Long contactNo, String address) {
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.contactNo = contactNo;
        this.address = address;
    }

    public User(int ID, String name, String surname, String password, Long contactNo, String address) {
        this.ID = ID;
        this.name = name;
        this.surname = surname;
        this.password = password;
        this.contactNo = contactNo;
        this.address = address;

    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getContactNo() {
        return contactNo;
    }

    public void setContactNo(Long contactNo) {
        this.contactNo = contactNo;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String toString(){
        return name;
    }
}
