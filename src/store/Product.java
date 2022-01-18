package store;
//Aditya Wiwekananda//
//101147416//

import javax.swing.*;

/**
 * Class for storing the name, id, and price of a product
 * @author Aditya
 * @version 1.0
 */
public class Product {
    private String name;
    private int id;
    private double price;
    private String picturePath;

    /**
     * The default constructor of store.Product.
     * @param name String, the name of the product.
     * @param id int, the product ID of the product.
     * @param price double, the price of the product.
     */

    public Product(String name, int id, double price){
        this(name, id, price, "");
    }

    public Product(String name, int id, double price, String picturePath){
        this.name = name;
        this.id = id;
        this.price = price;
        this.picturePath = picturePath;
    }

    /**
     * Getter method for the name attribute
     * @return String, the name of the product.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Getter method for the ID attribute
     * @return int, the id of the product.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Getter method for the price attribute
     * @return double, the price of the product.
     */
    public double getPrice() {
        return this.price;
    }

    public String getPicturePath(){
        return this.picturePath;
    }
}
