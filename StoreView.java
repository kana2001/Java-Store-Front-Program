package store;
//Aditya Wiwekananda//
//101147416//

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import java.io.IOException;
import java.util.ArrayList;

import static java.lang.Integer.parseInt;

/**
 * Class used to display store information
 * @author Aditya Wiwekananda
 * @version 1.0
 */
public class StoreView {
    private int cartID;
    private StoreManager sm;
    private JFrame frame;


    /**
     * A constructor for store.StoreView
     * @param cartID int, the unique cart ID of each storeView object
     * @param sm store.StoreManager, the storeManager object
     */
    public StoreView(StoreManager sm, int cartID){
        this.cartID = cartID;
        this.sm = sm;
        this.frame = new JFrame();
    }

    /**
     * Provides a button for the view cart function. Allows user to view their cart including the total and
     * total quantities.
     * @return JButton, the view cart function
     */
    private JButton viewCartButton(){
        JButton button = new JButton("View Cart");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String message;
                message = "";
                ShoppingCart userCart = sm.getUserCarts().get(cartID);
                ArrayList<Product> products = userCart.getShoppingCart();
                for (Product p : products){
                    if(userCart.getProductQuantity(p) != 0) {
                        //int productID = p.getId();
                        int productStock = userCart.getProductQuantity(p);
                        message += productStock + "\t | \t" + p.getName() + "\t | \t $" + String.format("%.2f", p.getPrice()) + "\n";
                    }
                }
                message += "Total: $" + String.format("%.2f", sm.processTransaction(cartID));

                //default title and icon
                JOptionPane.showMessageDialog(frame, message, "My Cart", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        return button;
    }

    /**
     * Provides the checkout button. Allows user to checkout showing their order summary, total. Quits the program
     * afterwards.
     * @return JButton, the checkout button.
     */
    private JButton checkoutButton(){
        JButton button = new JButton("Checkout");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                String message;
                message = "";
                ShoppingCart userCart = sm.getUserCarts().get(cartID);
                ArrayList<Product> products = userCart.getShoppingCart();
                for (Product p : products){
                    if(userCart.getProductQuantity(p) != 0) {
                        //int productID = p.getId();
                        int productStock = userCart.getProductQuantity(p);
                        message += productStock + "\t | \t" + p.getName() + "\t | \t $" + String.format("%.2f", p.getPrice()) + "\n";
                    }
                }
                message += "Your total is: $" + String.format("%.2f", sm.processTransaction(cartID)) + "\n Thank you for shopping at MOBILE CITY! \n Press OK to close the program.";

                JOptionPane.showMessageDialog(frame, message, "Checkout", JOptionPane.INFORMATION_MESSAGE);

                frame.setVisible(false);
                frame.dispose();
            }
        });
        return button;
    }

    /**
     * Provides the quit button. When clicked, it will confirm if the user wants to quit.
     * @return JButton, the quit button.
     */
    private JButton quitButton(){
        JButton button = new JButton("Quit");

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {

                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });
        return button;
    }

    /**
     * Updates the Add and Remove buttons enabling and disabling them when necessary.
     * @param product Product, the item to check
     * @param cartID int, the individual cart ID
     * @param quantity JLabel, the quantity value to check
     * @param addButton JButton, the add button to update
     * @param removeButton JButton, the remove button to update
     */
    private void updateAddRemButton(Product product, int cartID, JLabel quantity, JButton addButton, JButton removeButton){
        quantity.setText(String.valueOf(sm.getStock(product)));
        addButton.setEnabled(parseInt(quantity.getText()) != 0);

        int quantityInCart = sm.getUserCarts().get(cartID).getProductQuantity(product);
        removeButton.setEnabled(quantityInCart != 0);
    }

    /**
     * A method to display the GUI
     * @return returns true if the user has entered 'checkout' or 'quit'
     */
    private boolean displayGUI(){

        this.frame.setTitle("Frame");

        JLabel headerLabel = new JLabel("Welcome to Mobile City! (ID: " + this.cartID + ")");
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel headerPanel = new JPanel();
        JPanel bodyPanel = new JPanel(new GridBagLayout());

        headerPanel.add(headerLabel);
        mainPanel.add(headerPanel, BorderLayout.PAGE_START);
        mainPanel.add(bodyPanel, BorderLayout.CENTER);

        //The Body Panel
        GridBagConstraints c1 = new GridBagConstraints();
        c1.fill = GridBagConstraints.BOTH;
        c1.weightx = 0.9;
        c1.weighty = 1;
        c1.gridx = 0;
        c1.gridy = 0;
        c1.gridheight = 3;
        c1.gridwidth = 2;
        JPanel items = new JPanel(new GridLayout(0, 2));
        JScrollPane scrollFrame = new JScrollPane(items);
        items.setAutoscrolls(true);
        bodyPanel.add(scrollFrame, c1);

        //The side Buttons
        GridBagConstraints c2 = new GridBagConstraints();
        c2.fill = GridBagConstraints.HORIZONTAL;
        c2.weightx = 0.1;
        c2.weighty = 0.5;
        c2.gridx = 2;
        c2.gridy = 0;
        c2.anchor = GridBagConstraints.FIRST_LINE_END;

        JPanel sideButtons = new JPanel(new GridLayout(0, 1));
        sideButtons.add(viewCartButton());
        sideButtons.add(checkoutButton());
        sideButtons.add(quitButton());
        bodyPanel.add(sideButtons, c2);


        //Creating panels for every item
        Inventory inventory = this.sm.getInventory();
        ArrayList<Product> products = inventory.getInventory();
        JPanel[] itemPanels = new JPanel[products.size()];
        for (int i = 0; i < products.size(); i++){
            itemPanels[i] = new JPanel(new BorderLayout());
            JPanel itemPanel = itemPanels[i];
            Product product = products.get(i);

            // Header of each item
            JPanel itemHeadPanel = new JPanel(new BorderLayout());
            JPanel itemHeader = new JPanel();
            JLabel itemHeaderText = new JLabel(product.getName());
            itemHeader.add(itemHeaderText);

            JLabel itemPriceStock = new JLabel( "$" + String.format("%.2f", product.getPrice()) + " - Stock:");
            JLabel quantity = new JLabel(String.valueOf(inventory.getProductQuantity(product)));
            JPanel itemInfo = new JPanel();
            itemInfo.add(itemPriceStock);
            itemInfo.add(quantity);


            itemHeadPanel.add(itemHeader, BorderLayout.PAGE_START);
            itemHeadPanel.add(itemInfo, BorderLayout.CENTER);


            //Body of each Item
            JPanel itemBodyPanel = new JPanel(new BorderLayout());


            try {
                BufferedImage myPicture = ImageIO.read(this.getClass().getResource(product.getPicturePath()));
                JLabel picLabel = new JLabel(new ImageIcon(myPicture.getScaledInstance(150, 200, Image.SCALE_FAST)));
                picLabel.setPreferredSize(new Dimension(150,200));
                itemBodyPanel.add(picLabel, BorderLayout.PAGE_START);
            } catch (IOException e) {
                e.printStackTrace();
            }

            //Add and remove buttons
            JPanel buttons = new JPanel();
            JButton addButton = new JButton("+");
            JButton removeButton = new JButton("-");
            if (sm.getUserCarts().get(cartID).getProductQuantity(product) == -1){
                removeButton.setEnabled(false); //Remove button should be disabled if user does not have an instance of the item
            }

            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    sm.addToUserCart(1, product.getId(), cartID);
                    updateAddRemButton(product, cartID, quantity, addButton, removeButton);

                }
            });

            removeButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    sm.removeFromUserCart(1, product.getId(), cartID);
                    updateAddRemButton(product, cartID, quantity, addButton, removeButton);
                }
            });


            buttons.add(addButton);
            buttons.add(removeButton);
            itemBodyPanel.add(buttons, BorderLayout.CENTER);


            //Add panels to each itemPanel
            itemPanel.add(itemHeadPanel, BorderLayout.PAGE_START);
            itemPanel.add(itemBodyPanel, BorderLayout.CENTER);
            items.add(itemPanel);

        }


        // pack
        frame.add(mainPanel);
        frame.pack();

        // add the window listener
        // we no longer want the frame to close immediately when we press "x"
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                if (JOptionPane.showConfirmDialog(frame, "Are you sure you want to quit?")
                        == JOptionPane.OK_OPTION) {
                    // close it down!
                    frame.setVisible(false);
                    frame.dispose();
                }
            }
        });

        // the frame is not visible until we set it to be so
        frame.setVisible(true);
        return false;
    }



    /**
     * The entry point of the program. Allows user to interact with the store.
     * @param args String, test arguments
     */
    public static void main(String[] args) {
        ShoppingCart sc = new ShoppingCart();
        ProductStockContainer sc1 = sc;
        StoreManager sm = new StoreManager();
        StoreView sv1 = new StoreView(sm, sm.assignNewCartID());
        sv1.displayGUI();

        //System.out.println("ALL STOREVIEWS DEACTIVATED");
    }
}
