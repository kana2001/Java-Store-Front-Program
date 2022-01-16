package store;
//Aditya Wiwekananda//
//101147416//

import java.util.ArrayList;

/**
 * A class for the shopping cart
 * @author Aditya Wiwekananda
 * @version 1.0
 */
public class ShoppingCart implements ProductStockContainer {

    private ArrayList<Product> shoppingCart = new ArrayList<>();
    private ArrayList<Integer> quantityOfProductList = new ArrayList<>();



    /**
     * Helper method. Returns the index of the specified product in the ArrayList<> attributes
     * @param id int, the product ID
     * @return int, the index of the specified product in the shoppingCart attribute.
     */
    private int getIndex(int id){
        int indexOfProduct = -1;

        for(int i = 0; i < shoppingCart.size(); i++ ){
            if(shoppingCart.get(i).getId() == id){
                indexOfProduct = i;
            }
        }
        return indexOfProduct;
    }

    @Override
    /**
     * Returns the quantity of the specified product
     * @param product int, the ID of the product.
     * @return returns -1 if the specified product is not in the shoppingCart, otherwise returns its stock
     */
    public int getProductQuantity(Product product){
        int indexOfProduct = getIndex(product.getId());
        int quantity = -1;

        if (indexOfProduct != -1){
            quantity = quantityOfProductList.get(indexOfProduct);
        }

        return quantity;

    }

    @Override
    /**
     * Adds a specified amount of stock for the specified product
     * @param product store.Product, the item to add.
     * @param quantity int, the amount of the item to add.
     */
    public void addProductQuantity(Product product, int quantity){
        int indexOfProduct = getIndex(product.getId());

        if (quantity < 0){
            throw new IllegalArgumentException();
        }

        if (indexOfProduct == -1){
            shoppingCart.add(product);
            quantityOfProductList.add(quantity);
        }

        else{
            quantityOfProductList.set(indexOfProduct, quantityOfProductList.get(indexOfProduct) + quantity);
        }

    }

    @Override
    /**
     * Removes a specified amount of a specified item from the shoppingCart
     * @param product Product, the product object of the item
     * @param quantity int, the amount of the item to remove
     */
    public void removeProductQuantity(Product product, int quantity){
        int indexOfProduct = getIndex(product.getId());

        if (quantity < 0){
            throw new IllegalArgumentException();
        }

        if(indexOfProduct != -1){
            int updatedStock = quantityOfProductList.get(indexOfProduct) - quantity;

            if (updatedStock < 0){
                quantityOfProductList.set(indexOfProduct, 0);
            }

            else{
                quantityOfProductList.set(indexOfProduct, updatedStock);
            }
        }

        else{
            System.out.println("No product available with given ID");
        }
    }

    @Override
    /**
     * Returns the number of unique products in the shoppingCart.
     * @return int, the number of unique products.
     */
    public int getNumOfProducts() {
        int num = 0;
        for(int i = 0; i < this.shoppingCart.size(); i++){
            if (this.quantityOfProductList.get(i) > 0 ){
                num++;
            }
        }
        return num;
    }


    /**
     * Getter method for the shoppingCart list
     * @return ArrayList<store.Product>, the shoppingCart list that includes the available stock.
     */
    public ArrayList<Product> getShoppingCart(){
        return this.shoppingCart;
    }

}
