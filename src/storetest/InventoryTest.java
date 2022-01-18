//Aditya Wiwekananda
//101147416

package storetest;
import org.junit.jupiter.api.*;
import store.*;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author Aditya Wiwekananda
 * @version 1.0
 * A class to test the Inventory class.
 */
public class InventoryTest {

    private static Inventory inv;
    private static Product p1;
    private static Product p2;
    private static Product p3;

    @BeforeAll
    /**
     * Initializes test attributes.
     */
    public static void init(){
        inv = new Inventory("");
        p1 = new Product("p1", 1, 19.99);
        p2 = new Product("p2", 2, 192.99);
        p3 = new Product("p3", 3, 9.49);
    }

    @Test
    /**
     * Tests the operation of getStock().
     */
    public void testGetStock(){
        //Replace the old inv with an Inventory created from the default constructor
        /*
        The default items in Inventory() are:
        Product iPhone6 = new Product("iPhone 6", 1013324, 249.99); -> quantity = 4
        Product galaxyS8 = new Product("Samsung Galaxy S8", 231332, 349.24); -> quantity = 2
        Product n95 = new Product("Nokia N95", 3123123, 899.99); -> quantity = 99
         */
        inv = new Inventory();

        int expStock = 4;
        assertEquals(expStock, inv.getStock(1013324));

        expStock = 2;
        assertEquals(expStock, inv.getStock(231332));

        expStock = 99;
        assertEquals(expStock, inv.getStock(3123123));
    }

    @Test
    /**
     * Tests the operation of addStock()
     */
    public void testAddStock(){
        inv.addStock(new Product("p1", 1, 19.99), 5);
        int expQuantity = 5;
        assertEquals(expQuantity, inv.getStock(1), "addStock() is not adding stock properly.");

        inv.addStock(p1, 7);
        expQuantity += 7;
        assertEquals(expQuantity, inv.getStock(1), "addStock() is not adding stock properly.");

        //Try passing a negative quantity
        assertThrows(IllegalArgumentException.class, () -> {inv.addStock(p1, -7);},"addStock() is not adding stock properly.");
    }

    @Test
    /**
     * Tests the operation of removeStock(). Note that if the specified quantity to remove is greater than the
     * stock available, the method will remove all the stock available regardless.
     */
    public void testRemoveStock(){
        inv.addStock(p1, 5);
        inv.addStock(p2, 8);

        int expStock = 3;
        inv.removeStock(1, 2);
        assertEquals(expStock, inv.getStock(1), "removeStock() is not removing stock properly.");

        //When more stock is removed than available. The stock should be set to 0.
        expStock = 0;
        inv.removeStock(2, 9);
        assertEquals(expStock, inv.getStock(2), "removeStock() is not removing stock properly.");

        expStock = 1;
        inv.removeStock(1,2);
        assertEquals(expStock, inv.getStock(1), "removeStock() is not removing stock properly.");
    }

    @Test
    /**
     * Tests the operation of getInformation().
     */
    public void testGetInformation(){
        p1 = new Product("testProduct", 4, 59.99);
        inv.addStock(p1, 1);
        assertEquals(p1, inv.getInformation(4), "getInformation() is not returning the right info.");

        p1 = new Product("testProduct", 69, 429.99);
        inv.addStock(p1, 214124);
        assertEquals(p1, inv.getInformation(69), "getInformation() is not returning the right info.");

        p1 = new Product("testProduct", 412125125, 429.99);
        inv.addStock(p1, 214124);
        assertEquals(p1, inv.getInformation(412125125), "getInformation() is not returning the right info.");
    }

    @Test
    /**
     * Tests the operation of getInventory
     */
    public void testGetInventory(){
        Product p4 = new Product("Nokia N31", 04140, 4124.99);

        ArrayList<Product> expInv = new ArrayList<>();
        assertEquals(expInv, inv.getInventory(),"getInventory() is not returning the correct arrayList.");

        expInv.add(p1);
        inv.addStock(p1, 2);
        assertEquals(expInv, inv.getInventory(),"getInventory() is not returning the correct arrayList.");

        expInv.add(p2);
        inv.addStock(p2, 2);
        assertEquals(expInv, inv.getInventory(),"getInventory() is not returning the correct arrayList.");

        expInv.add(p3);
        inv.addStock(p3, 2);
        assertEquals(expInv, inv.getInventory(),"getInventory() is not returning the correct arrayList.");

        expInv.add(p4);
        inv.addStock(p4, 2);
        assertEquals(expInv, inv.getInventory(),"getInventory() is not returning the correct arrayList.");
    }

    @Test
    /**
     * Tests the operation of getQuantityOfProductList()
     */
    public void testGetQuantityOfProductList(){
        Product p4 = new Product("Nokia N31", 04140, 4124.99);

        ArrayList<Integer> expQuantities = new ArrayList<>();
        assertEquals(expQuantities, inv.getQuantityOfProductList());

        expQuantities.add(2);
        inv.addStock(p1,2);
        assertEquals(expQuantities, inv.getQuantityOfProductList());

        expQuantities.add(3);
        inv.addStock(p2,3);
        assertEquals(expQuantities, inv.getQuantityOfProductList());

        expQuantities.add(5);
        inv.addStock(p3,5);
        assertEquals(expQuantities, inv.getQuantityOfProductList());

        expQuantities.add(2913);
        inv.addStock(p4,2913);
        assertEquals(expQuantities, inv.getQuantityOfProductList());
    }

    @AfterEach
    /**
     * Cleans up after each test.
     */
    public void cleanUpEach(){
        inv = new Inventory("");
        p1 = new Product("p1", 1, 19.99);
        p2 = new Product("p2", 2, 192.99);
        p3 = new Product("p3", 3, 9.49);
    }

}
