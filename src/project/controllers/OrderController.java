package project.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXToggleButton;
import com.sun.org.apache.xpath.internal.operations.Or;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class OrderController implements Initializable {
    private static CashierController cashierController;
    public BorderPane OrderScreenPane;
    public JFXButton confirmButton, cancelButton;
    public JFXToggleButton addOnToggle;
    public JFXToggleButton discountToggle;

    private  static OrderController instanceOf;

    /*
    * These are the group of RadioButtons
    * in OrderScreen.fxml
    */
    public ToggleGroup cupSize;
    public ToggleGroup iceSize;
    public ToggleGroup sugarLevel;

    /*
    * orderPriceLabel -> Updates when you choose other options
    * orderNameLabel -> Gets text from the selectedItem name
    * itemImage -> Gets image from the selectedItem image
    */
    public Label orderPriceLabel;
    public Label orderNameLabel;
    public ImageView itemImage;


    /*
    * addOnListView -> displays the add on items added to the cart
    * addOnHashMap -> stores the add on orders, updated while addOn button is toggled
    * itemAddOnObservableList_String -> items of addOnListView
    * */
    public JFXListView addOnListView;
    private HashMap<Item,Integer> addOnHashMap;
    private ObservableList<String> itemAddOnObservableList_String;

    /*
    * addOnDisplayListView -> displays the add on items available in the shop
    *
    *
    * */
    public JFXListView addOnDisplayListView;
    private ObservableList<HBox> itemAddOnDisplayObservableList;

    private ArrayList<Item> itemAddOnList;
    private ObservableList<Order> orderList;
    private HashMap<Item,String> itemHashMap;
    private Set<String> itemHashSet;
    private HashMap<Integer,Integer> cupsOrderedHashMap = new HashMap<>();
    private HashMap<Item,Integer> itemOrderedHashMap = new HashMap<>();
    private Item selectedItem;
    private boolean newOrder;

    // CURRENT ORDER DETAILS (resets every order)
    private static final double discount_rate = 0.25;
    private int cup, sugar, ice;
    private boolean isAddOn, isDiscount;
    private double price;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addOnHashMap = new HashMap<>();

        instanceOf = this;

        itemAddOnDisplayObservableList = FXCollections.observableArrayList();
        itemAddOnObservableList_String = FXCollections.observableArrayList();
        orderList = FXCollections.observableArrayList();

        addOnListView.setItems(itemAddOnObservableList_String);
        addOnListView.setMaxSize(120,260);

        addOnDisplayListView.setItems(itemAddOnDisplayObservableList);
        addOnDisplayListView.setOrientation(Orientation.HORIZONTAL);
        addOnDisplayListView.setMaxSize(600, 70);

        OrderScreenPane.getTop().setVisible(false);

        buttonActions();


    }

    /* This method handles cancel button — just close the stage without return value */
    public void setCancelButton(ActionEvent e) {
        if(!ErrorPrompts.order_void(new ActionEvent())) return;
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /* This method handles the confirm button of this window
    *       After confirming the final order adds order to the list
    *       and pass it to the CashierScreen controller
    */
    public void setConfirmOrderButton() {
        /*
         * Guide for argument values
         * For order -> just the Item
         * For size
         *   1 ----- small
         *   2 ----- medium
         *   3 ----- large
         *             /*
         * For sugar level
         *  input    sugar level
         *   0 ----- 0%
         *   1 ----- 25%
         *   2 ----- 50%
         *   3 ----- 75%
         *   4 ----- 100%
         *            /*
         * For ice
         *   1 ----- regular
         *   2 ----- less
         *   3 ----- no ice
         */

        if(!ErrorPrompts.order_confirmation(new ActionEvent())) return;

        // for cup size (wink wink)
        RadioButton rb = (RadioButton) cupSize.getSelectedToggle();
        String rb_txt = rb.getText();
        switch(rb_txt) {
            case "S":
                cup = 1;
                break;
            case "M":
                cup = 2;
                break;
            case "L":
                cup = 3;
                break;
        }

        // for ice size
        RadioButton rb2 = (RadioButton) iceSize.getSelectedToggle();
        String rb_txt2 = rb2.getText();
        switch(rb_txt2) {
            case "Regular":
                ice = 1;
                break;
            case "Less ice":
                ice = 2;
                break;
            case "No ice":
                ice = 3;
                break;
        }

        // for sugar level
        RadioButton rb3 = (RadioButton) sugarLevel.getSelectedToggle();
        String rb_txt3 = rb3.getText();
        switch(rb_txt3) {
            case "0":
                sugar = 0;
                break;
            case "25":
                sugar = 1;
                break;
            case "50":
                sugar = 2;
                break;
            case "75":
                sugar = 3;
                break;
            case "100":
                sugar = 4;
                break;
        }

        Order order = new Order(selectedItem,cup,sugar,ice,isAddOn,addOnHashMap,isDiscount);

        // Add order to the list (displayed in table view in Cashier Screen)
        orderList.add(order);

        // Updates the cup counter hash map
        updateCupsOrderedHashMap(order);

        // Add item ordered in the item ordered hash map (used in statistics)
        updateItemOrderedHashMap(selectedItem);

        // For each add on ordered append all to item ordered hash map (used in statistics)
        for(Item item : addOnHashMap.keySet()) {
            for(int i = 0 ; i < addOnHashMap.get(item); i++)
                updateItemOrderedHashMap(item);
        }

        cashierController.catchOrderDetails(orderList,itemOrderedHashMap,cupsOrderedHashMap, false);
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
    }

    // This method is used to update cupsOrderedHashMap (used for statistics)
    private void updateCupsOrderedHashMap(Order order) {
        switch(order.getSize()) {
            case 1: // SMALL
                if(cupsOrderedHashMap.containsKey(1)) {
                    cupsOrderedHashMap.replace(1,cupsOrderedHashMap.get(1)+1);
                } else {
                    cupsOrderedHashMap.put(1,1);
                }
                break;
            case 2: // MEDIUM
                if(cupsOrderedHashMap.containsKey(2)) {
                    cupsOrderedHashMap.replace(2,cupsOrderedHashMap.get(2)+1);
                } else {
                    cupsOrderedHashMap.put(2,1);
                }
                break;
            case 3: // LARGE
                if(cupsOrderedHashMap.containsKey(3)) {
                    cupsOrderedHashMap.replace(3,cupsOrderedHashMap.get(3)+1);
                } else {
                    cupsOrderedHashMap.put(3,1);
                }
                break;
        }
    }

    // This method is used to add an item to the item ordered hash map (used in statistics)
    private void updateItemOrderedHashMap(Item itemOrdered) {
        if(itemHashMap.containsKey(itemOrdered)) {
            if(itemOrderedHashMap.containsKey(itemOrdered)) {
                itemOrderedHashMap.replace(itemOrdered,itemOrderedHashMap.get(itemOrdered)+1);
            }
            else {
                itemOrderedHashMap.put(itemOrdered,1);
            }
        }
    }

    /* This method handles the changes to the display of the
    *   price based from the buttons being clicked by the user
    *   which is used in this controller's initialize function
    * */
    private void buttonActions() {

        cupSize.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                orderPriceLabel.setText(Double.toString(getCalculatedPrice()));
            }
        });

        sugarLevel.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                orderPriceLabel.setText(Double.toString(getCalculatedPrice()));
            }
        });

        addOnToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                isAddOn = addOnToggle.isSelected();
                OrderScreenPane.getTop().setVisible(isAddOn);
                if(isAddOn) {
                    showAddOnList();
                }
            }
        });

        discountToggle.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(discountToggle.isSelected()) {
                    isDiscount = true;
                    orderPriceLabel.setText(Double.toString(getCalculatedPrice()));
                }
                else {
                    isDiscount = false;
                    orderPriceLabel.setText(Double.toString(getCalculatedPrice()));
                }
            }
        });
    }

    /* This method calculates the price of the order based on
    *   the current state / settings of the buttons
    *   Updates every time user clicks different button
    *  Used in buttonsAction in initialize function
    * */
    private double getCalculatedPrice() {
        double temp = price;
        // calculate based on cup size
        RadioButton rb = (RadioButton) cupSize.getSelectedToggle();
        String rb_txt = rb.getText();
        switch(rb_txt) {
            /*
             *   NOTE
             *   S ----- small      + 0
             *   M ----- medium     + 20
             *   L ----- large      + 45
             */
            case "S":
                temp += 0;
                break;
            case "M":
                temp += 20;
                break;
            case "L":
                temp += 45;
                break;
            default:
                break;
        }
        // calculate based on sugar level
        RadioButton rb2 = (RadioButton) sugarLevel.getSelectedToggle();
        String rb_txt2 = rb2.getText();
        switch(rb_txt2) {
            /*
             *   NOTE
             *  add price    sugar level
             *   0 ----- 0%
             *   5 ----- 25%
             *   10 ----- 50%
             *   15 ----- 75%
             *   20 ----- 100%
             */
            case "0":
                temp += 0;
                break;
            case "25":
                temp += 5;
                break;
            case "50":
                temp += 10;
                break;
            case "75":
                temp += 15;
                break;
            case "100":
                temp += 20;
            default:
                break;
        }

        // Calculates the add on price then add to temp
        if(isAddOn) {
            for(Item item : addOnHashMap.keySet()) {
                int qty = addOnHashMap.get(item);
                temp += (item.item_price * qty);
            }
        }

        if(isDiscount) temp = temp - (temp * discount_rate);

        return temp;
    }

    /* This method is used to get the add ons per order */
    private void showAddOnList() {
        // Add listener to the list view
        addOnDisplayListView.setOnMouseReleased(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                if (e.isPrimaryButtonDown() && e.isSecondaryButtonDown()) {
                    // Do nothing if user is still holding the button
                    return;
                }

                // Add item to the orderList and update the price label
                else if (e.getButton() == MouseButton.PRIMARY) {
                    // This is the add on item that is selected
                    Item currentItem = getAddOnItemSelected();

                    // If nothing is selected do nothing
                    if(currentItem == null) {
                        return;
                    }

                    // Update item details to the Add On Hash map
                    if(addOnHashMap.keySet().contains(currentItem)) {
                        int qty = addOnHashMap.get(currentItem);
                        addOnHashMap.replace(currentItem,qty+1);
                    } else {
                        addOnHashMap.put(currentItem,1);
                    }

                    itemAddOnObservableList_String.add(getAddOnItemSelected().item_name);
                    orderPriceLabel.setText(Double.toString(getCalculatedPrice()));

                // Remove the item from the orderList if exist and update the price label
                } else if (e.getButton() == MouseButton.SECONDARY) {
                    // This is the add on item that is selected
                    Item currentItem = getAddOnItemSelected();

                    // If nothing is selected do nothing
                    if(getAddOnItemSelected() == null) {
                        return;
                    }

                    // Update item details to the Add On Hashmap
                    if(addOnHashMap.keySet().contains(currentItem)) {
                        int qty = addOnHashMap.get(currentItem);
                        if(qty != 0) addOnHashMap.replace(currentItem,qty-1);
                    }

                    itemAddOnObservableList_String.remove(getAddOnItemSelected().item_name);
                    orderPriceLabel.setText(Double.toString(getCalculatedPrice()));
                }
            }
        });
    }

    /* This method is used to generate the add on display items
    *  at the top of the border pane */
    private void initAddOnItemsDisplay() {
        addOnDisplayListView.setOrientation(Orientation.HORIZONTAL);
        for(Item item : itemAddOnList) {
            itemAddOnDisplayObservableList.add(item.item_display);
        }
    }

    /*  This method is used to check if an item is selected or not
     *  Returns Item if it exist
     */
    private Item getAddOnItemSelected() {
        // gets the particular HBox container of the selected item
        HBox box = (HBox) addOnDisplayListView.getSelectionModel().getSelectedItem();
        // if an item is really selected, check if it is registered in the itemHashMap
        for(Item key : itemAddOnList) {
            // if the item_display of 'key' is the same as the selected item display 'box'
            // returns isSelected as true, meaning an item is selected
            if (key.item_display.equals(box)) {
                return key;
            }
        }
        return null;
    }

    /* This method is used to inject cashier controller here */
    static void injectCashierController(CashierController cc) {
        cashierController = cc;
    }

    /* This method is used to catch information from CashierScreen */
    static void catchInformation(Item selectedItem, HashMap<Item,String> ihm,
                  HashMap<Integer, Integer> ohm, HashMap<Item, Integer> iohm, ObservableList<Order> orderList,
                  ArrayList<Item> itemAddOnList, boolean newOrder) {
        instanceOf.selectedItem = selectedItem;
        instanceOf.orderList = FXCollections.observableArrayList(orderList);
        instanceOf.itemHashMap = new HashMap<>(ihm);
        instanceOf.cupsOrderedHashMap = new HashMap<>(ohm);
        instanceOf.itemOrderedHashMap = new HashMap<>(iohm);
        instanceOf.itemHashSet = new HashSet<>(ihm.values());
        instanceOf.itemAddOnList = new ArrayList<>(itemAddOnList);
        instanceOf.itemImage.setImage(selectedItem.item_image.getImage());
        instanceOf.orderPriceLabel.setText(Double.toString(selectedItem.item_price));
        instanceOf.orderNameLabel.setText(selectedItem.item_name);
        instanceOf.price = selectedItem.item_price;
        instanceOf.newOrder = newOrder;

        instanceOf.initAddOnItemsDisplay();
    }
}
