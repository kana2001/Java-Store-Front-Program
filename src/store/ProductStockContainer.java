package store;

public interface ProductStockContainer {

    /**
     * Returns the quantity of the specified product
     * @param product int, the ID of the product.
     * @return returns -1 if the specified product is not in the shoppingCart, otherwise returns its stock
     */
    public int getProductQuantity(Product product);

    /**
     * Adds a specified amount of stock for the specified product
     * @param product store.Product, the item to add.
     * @param quantity int, the amount of the item to add.
     */
    public void addProductQuantity(Product product, int quantity);

    /**
     * Removes a specified amount of a specified item from the shoppingCart
     * @param product Product, the product object of the item
     * @param quantity int, the amount of the item to remove
     */
    public void removeProductQuantity(Product product, int quantity);

    /**
     * Returns the number of unique products in the shoppingCart.
     * @return int, the number of unique products.
     */
    public int getNumOfProducts();

}
