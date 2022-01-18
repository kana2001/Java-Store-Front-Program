//Aditya Wiwekananda
//101147416

package storetest;
import org.junit.jupiter.api.*;


import store.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Aditya Wiwekananda
 * @version 1.0
 * A test class for StoreManager
 */
public class StoreManagerTest {

    private static StoreManager sm;

    private static ShoppingCart sc;
    private static Product p1;
    private static Product p2;
    private static Product p3;

    @BeforeAll
    /**
     * Initializes test attributes.
     */
    public static void init(){
        sm = new StoreManager();
        sc = new ShoppingCart();
        p1 = new Product("Product 1",1,20.1);
        p2 = new Product("Product 2",2,20.3);
        p3 = new Product("Product 3",3,312.3);
    }

    @Test
    /**
     * Tests the operation of assignNewCartID(). The tests check if the new ID that is created is equal to the
     * size of the the list of users. Note, new ID should always be equal to the size of the list.
     */
    public void testAssignNewCartID(){
        int numUsers = sm.getListOfUsers().size();
        int newID = sm.assignNewCartID();
        assertEquals(numUsers, newID, "StoreManager is not properly adding new users");

        numUsers = sm.getListOfUsers().size();
        newID = sm.assignNewCartID();
        assertEquals(numUsers, newID, "StoreManager is not properly adding new users");

        numUsers = sm.getListOfUsers().size();
        newID = sm.assignNewCartID();
        assertEquals(numUsers, newID, "StoreManager is not properly adding new users");
    }

    @Test
    /**
     * Tests the operation of getListOfUsers
     */
    public void testGetListOfUsers(){
        ArrayList<Integer> expUsers = new ArrayList<>(Arrays.asList(0,1,2));
        for(int i = 0; i < 3; i++){
            sm.assignNewCartID();
        }
        assertEquals(expUsers, sm.getListOfUsers(), "StoreManager is not properly getting the list of users");

        for(int i = 0; i < 4; i++) {
            expUsers.add(i+3);
            sm.assignNewCartID();
            assertEquals(expUsers, sm.getListOfUsers(), "StoreManager is not properly getting the list of users");
        }
    }

    @Test
    /**
     * Tests the operation of getUserCarts
     */
    public void testGetUserCarts(){

        for(int i = 0; i < 5; i++){
            sm.assignNewCartID();
        }

        ArrayList<ShoppingCart> expUserCarts = new ArrayList<>();
        for(int i = 0; i<5; i++){
            expUserCarts.add(new ShoppingCart());
        }

        assertEquals(expUserCarts.size(), sm.getUserCarts().size(), "StoreManager is not properly getting the list of shopping carts" );
    }

    @Test
    /**
     * Tests the operation of getStock
     */
    public void testGetStock(){
        sm.getInventory().addStock(p1, 2);
        sm.getInventory().addStock(p2, 3);
        sm.getInventory().addStock(p3, 321);

        assertEquals(2, sm.getStock(p1));
        assertEquals(3, sm.getStock(p2));
        assertEquals(321, sm.getStock(p3));
    }

    @Test
    /**
     * Tests the operation of getInventory()
     */
    public void testGetInventory(){
        Inventory expInv = new Inventory();

        for(int i = 0; i < sm.getInventory().getInventory().size(); i++){
            assertEquals(expInv.getInventory().get(i).getId(), sm.getInventory().getInventory().get(i).getId(), "getInventory " +
                    "is not properly retrieving the Inventory object");
        }


        expInv.addStock(p1, 2);
        sm.getInventory().addStock(p1, 2);
        for(int i = 0; i < sm.getInventory().getInventory().size(); i++){
            assertEquals(expInv.getInventory().get(i).getId(), sm.getInventory().getInventory().get(i).getId(), "getInventory " +
                    "is not properly retrieving the Inventory object");
        }

        expInv.addStock(p2, 3);
        sm.getInventory().addStock(p2, 3);
        for(int i = 0; i < sm.getInventory().getInventory().size(); i++){
            assertEquals(expInv.getInventory().get(i).getId(), sm.getInventory().getInventory().get(i).getId(), "getInventory " +
                    "is not properly retrieving the Inventory object");
        }

        expInv.addStock(p3, 312);
        sm.getInventory().addStock(p3, 512);
        for(int i = 0; i < sm.getInventory().getInventory().size(); i++){
            assertEquals(expInv.getInventory().get(i).getId(), sm.getInventory().getInventory().get(i).getId(), "getInventory " +
                    "is not properly retrieving the Inventory object");
        }

    }

    /**
     * Tests the operation of addToUserCart
     */
    @Test
    public void testAddToUserCart(){


        sm.getInventory().addStock(p1,5);
        sm.getInventory().addStock(p2,2);
        sm.getInventory().addStock(p3,9);

        sm.assignNewCartID();
        sm.assignNewCartID();


        //Check if addToUserCart throws an IllegalArgumentException when an invalid quantity is entered.
        assertThrows(IllegalArgumentException.class, () -> {sm.addToUserCart(7, 1, 0);},
                "StoreManager is not properly throwing an Illegal Argument Exception.");

        assertThrows(IllegalArgumentException.class, () -> {sm.addToUserCart(-32, 2, 1);},
                "StoreManager is not properly throwing an Illegal Argument Exception.");

        for (Integer I  : sm.getUserCarts().get(1).getUserCart().getQuantityOfProductList() ){
            System.out.println(I);
        }



        sm.addToUserCart(2, 1, 1);
        assertEquals(2, sm.getUserCarts().get(1).getUserCart().getStock(1), "StoreManager is" +
                " not properly adding to userCart");

        //Testing to see if addToUserCart returns -1 if an invalid productID is given
        assertEquals(-1, sm.getUserCarts().get(1).getUserCart().getStock(41), "StoreManager is" +
                " not properly adding to userCart");
    }

    @Test
    /**
     * Tests the operation of processTransaction
     */
    public void testProcessTransaction(){

        sm.getInventory().addStock(p1,5);
        sm.getInventory().addStock(p2,2);
        sm.getInventory().addStock(p3,9);

        sm.assignNewCartID();

        sm.addToUserCart(3, 1, 0);
        sm.addToUserCart(2, 2, 0);
        sm.addToUserCart(3, 3, 0);


        double expTotal = 3 * p1.getPrice() + 2 * p2.getPrice() + 3 * p3.getPrice();

        assertEquals(expTotal, sm.processTransaction(0), "processTransaction is not finalizing " +
                " the transaction properly.");

        //Reset the UserCart to test again
        sm.getUserCarts().get(0).getUserCart().removeStock(1,3);
        sm.getUserCarts().get(0).getUserCart().removeStock(2,2);
        sm.getUserCarts().get(0).getUserCart().removeStock(3,3);

        sm.addToUserCart(2, 1, 0);
        sm.addToUserCart(5, 3, 0);

        expTotal = 2 * p1.getPrice() + 5 * p3.getPrice();

        assertEquals(expTotal, sm.processTransaction(0), "processTransaction is not finalizing " +
                " the transaction properly.");
    }


    @AfterEach
    /**
     * Cleans up the objects after each test
     */
    public void cleanUpEach(){
        sm = new StoreManager();
    }

}
