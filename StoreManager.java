package store;
//Aditya Wiwekananda//
//101147416//

import java.util.ArrayList;

/**
 * Represents a manager of a store
 * @author Aditya Wiwekananda
 * @version 2.0
 */
public class StoreManager {
    private Inventory inventory = new Inventory();

    private ArrayList<Integer> listOfUsers = new ArrayList<>();
    private ArrayList<ShoppingCart> userCarts = new ArrayList<>();

    public StoreManager(){}

    /**
     * Method to assign a new cart ID
     * @return int, the ID of the new cart
     */
    public int assignNewCartID(){
        this.listOfUsers.add(this.listOfUsers.size());
        this.userCarts.add(new ShoppingCart());
        return this.listOfUsers.get(this.listOfUsers.size()-1);
    }

    /**
     * Method to return the list of users
     * @return ArrayList<Integer>, the list of users
     */
    public ArrayList<Integer> getListOfUsers() {
        return this.listOfUsers;
    }

    /**
     * Getter method to return the list of store.ShoppingCart objects
     * @return ArrayList<store.ShoppingCart>, the list of store.ShoppingCart objects.
     */
    public ArrayList<ShoppingCart> getUserCarts() {
        return this.userCarts;
    }

    /**
     * Getter method to return the stock of the requested product.
     * @param product store.Product, the product
     * @return int, the stock of the product.
     */
    public int getStock(Product product){
        return this.inventory.getProductQuantity(product);
    }

    /**
     * Getter method to return the inventory attribute
     * @return store.Inventory, the inventory attribute
     */
    public Inventory getInventory() {
        return this.inventory;
    }

    /**
     * Processes the transaction by calculating the total cost of the user's purchases
     * @param cartID int, the ID of the cart
     * @return double, the total price of the user's purchases
     */
    public double processTransaction(int cartID){
        double total = 0;

        ShoppingCart userCart = this.userCarts.get(cartID);
        int productID;

        for(Product p : userCart.getShoppingCart()){
            //productID = p.getId();
            total += p.getPrice() * userCart.getProductQuantity(p);
        }

        return total;
    }

    /**
     * Adds an item(s) to a user's cart
     * @param qty int, the amount of product that the user wants
     * @param productID int, the ID of the product
     * @param cartID int, the ID of the user's cart
     */
    public void addToUserCart(int qty, int productID, int cartID){
        //Only adds items to cart if there is sufficient quantity
        ShoppingCart userCart = this.userCarts.get(cartID);
        Product p = this.inventory.getInformation(productID);
        if (qty <=  this.inventory.getProductQuantity(p) && qty >= 0){
            userCart.addProductQuantity(this.inventory.getInformation(productID), qty);
            getInventory().removeProductQuantity(p, qty);
        }

        else{
            throw new IllegalArgumentException("The qty argument is invalid.");
        }
    }

    /**
     * Removes an item(s) to a user's cart
     * @param qty int, the amount of product that the user wants
     * @param productID int, the ID of the product
     * @param cartID int, the ID of the user's cart
     */
    public void removeFromUserCart(int qty, int productID, int cartID){
        ShoppingCart userCart = this.userCarts.get(cartID);
        Product p = this.inventory.getInformation(productID);
        if (qty <=  userCart.getProductQuantity(p) && qty >= 0){
            this.inventory.addProductQuantity(this.inventory.getInformation(productID), qty);
            userCart.removeProductQuantity(p, qty);
        }

        else{
            throw new IllegalArgumentException("The qty argument is invalid.");
        }
    }

}


