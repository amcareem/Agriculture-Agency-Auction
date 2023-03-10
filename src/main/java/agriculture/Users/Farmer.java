/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture.Users;

import agriculture.Seller;
import agriculture.*;

public class Farmer extends User implements Seller {

    public Farmer(String name, String surname, String password, Long contactNo, String address) {
        super(name, surname, password, contactNo, address);
    }
    public Farmer(int ID, String name, String surname, String password, Long contactNo, String address) {
        super(ID, name, surname, password, contactNo, address);
    }
    public String toString(){
        return "Farmer";
    }
}
