package store;
//Aditya Wiwekananda//
//101147416//

import java.util.ArrayList;

/**
 * Stores the item and stock information
 * @author Aditya Wiwekananda
 * @version 2.0
 */
public class Inventory implements ProductStockContainer {
    private ArrayList<Product> inventory = new ArrayList<>();
    private ArrayList<Integer> quantityOfProductList = new ArrayList<>();

    //initialize the inventory with default values
    public Inventory(){
        Product iPhone6 = new Product("iPhone 6", 1013324, 249.99, "iPhone6.jpg");
        Product galaxyS8 = new Product("Samsung Galaxy S8", 231332, 349.24, "samsungGalaxyS8.png");
        Product n95 = new Product("Nokia N95", 3123123, 899.99, "NokiaN95.jpg");
        Product oplusonepro = new Product("OnePlus 9 Pro", 243019, 1069.99, "oneplus9pro.jpg");
        Product iPhone12ProMax = new Product("iPhone 12 Pro Max", 102399, 1099, "iphone-12-pro-max-gold-hero.png");
        Product galaxyS21U = new Product("Samsung Galaxy S21 Ultra", 123123, 999.99, "SamsungGalaxyS21Ultra.jpg");
        Product iPhone12Pro = new Product("iPhone 12 Pro", 21441, 999, "iphone-12-pro-blue-hero.png");
        Product pixel4a = new Product("Google Pixel 4a", 124515, 349, "pixel4a.jpg");
        Product motoGPower = new Product("Moto G Power", 124551, 179.99, "motoGPower.jpg");

        inventory.add(iPhone6);
        inventory.add(galaxyS8);
        inventory.add(n95);
        inventory.add(oplusonepro);
        inventory.add(iPhone12ProMax);
        inventory.add(galaxyS21U);
        inventory.add(iPhone12Pro);
        inventory.add(pixel4a);
        inventory.add(motoGPower);

        quantityOfProductList.add(4);
        quantityOfProductList.add(2);
        quantityOfProductList.add(99);
        quantityOfProductList.add(169);
        quantityOfProductList.add(421);
        quantityOfProductList.add(72);
        quantityOfProductList.add(32);
        quantityOfProductList.add(424);
        quantityOfProductList.add(99);

        //The inventory looks like {product, quantity} = { {iPhone6,4}, {galaxyS8,2}, {n95, 99} }

    }

    /**
     * A constructor for store.Inventory to create an empty inventory
     * @param empty String, a redundant string that overrides the method and allows other objects to create an
     *              store.Inventory object with an empty ArrayList<store.Product>, inventory.
     */
    public Inventory(String empty){

    }

    /**
     * Helper method. Returns the index of the specified product in the ArrayList<> attributes
     * @param id int, the product ID
     * @return int, the index of the specified product in the inventory attribute.
     */
    private int getIndex(int id){
        int indexOfProduct = -1;

        for(int i = 0; i < inventory.size(); i++ ){
            if(inventory.get(i).getId() == id){
                indexOfProduct = i;
            }
        }
        return indexOfProduct;
    }

    @Override
    /**
     * Returns the quantity of the specified product
     * @param product int, the ID of the product.
     * @return returns -1 if the specified product is not in the inventory, otherwise returns its stock
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
            inventory.add(product);
            quantityOfProductList.add(quantity);
        }

        else{
            quantityOfProductList.set(indexOfProduct, quantityOfProductList.get(indexOfProduct) + quantity);
        }

    }

    @Override
    /**
     * Removes a specified amount of a specified item from the inventory
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
     * Returns the number of unique products in the inventory.
     * @return int, the number of unique products.
     */
    public int getNumOfProducts() {
        int num = 0;
        for(int i = 0; i < this.inventory.size(); i++){
            if (this.quantityOfProductList.get(i) > 0 ){
                num++;
            }
        }
        return num;
    }

    /**
     * Returns the information of a product given its product ID
     * @param id int, the product ID
     * @return store.Product, the corresponding product object
     */
    public Product getInformation(int id){
        int indexOfProduct = getIndex(id);

        if(indexOfProduct == -1){
            System.out.println("No product with the given product ID exists.\nReturning null.");
            return null;
        }

        return inventory.get(indexOfProduct);
    }

    /**
     * Getter method for the inventory list
     * @return ArrayList<store.Product>, the inventory list that includes the available stock.
     */
    public ArrayList<Product> getInventory(){
        return this.inventory;
    }

    /**
     * Getter method for the quantityOfProductsList
     * @return ArrayList<Integer>, the list of product quantities.
     */
    public ArrayList<Integer> getQuantityOfProductList(){
        return this.quantityOfProductList;
    }
}
