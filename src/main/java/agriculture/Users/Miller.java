/**
 * This program was created by Ahamed Careem (Github: amcareem, LinkedIn: https://www.linkedin.com/in/ahamedmusthafacareem/)
 *
 * All rights reserved. Copying or publishing this code anywhere else without permission is strictly prohibited.
 */
package agriculture.Users;

import agriculture.Buyer;
import agriculture.Seller;
import agriculture.*;

public class Miller extends User implements Buyer, Seller {

    public Miller(String name, String surname, String password, Long contactNo, String address) {
        super(name, surname, password, contactNo, address);
    }
    public Miller(int ID, String name, String surname, String password, Long contactNo, String address) {
        super(ID, name, surname, password, contactNo, address);
    }
}
