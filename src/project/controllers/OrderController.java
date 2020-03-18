package project.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXToggleButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
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
    public ToggleGroup cupSize;
    public ToggleGroup iceSize;
    public ToggleGroup sugarLevel;
    public Label orderPriceLabel,orderNameLabel;
    public ImageView itemImage;

    /*
     * @param
     *  orderHashMap
     *      maps the order to its quantity (will be used in the statistics function)
     *      e.g. Item -> chocolate, 1 when ordered at instance 1
     *      When another order has chocolate drink, the 1 will increment to 2
     *  itemAddOnList TODO
     *      this is the array list of the add on items
     *      should be caught from CashierScreen
     *  itemHashMap
     *      hash map of all items in the shop
     *  itemHashSet
     *      set that contains the item types found in the shop
     *  selectedItem
     *      the item that is selected / highlighted
     * */
    private ObservableList<Order> orderList;
    private ArrayList<Item> itemAddOnList = new ArrayList<>();
    private HashMap<Order,Integer> orderHashMap;
    private HashMap<Item,String> itemHashMap;
    private Set<String> itemHashSet;
    private Item selectedItem;

    // CURRENT ORDER DETAILS (resets every order)
    private static final double discount_rate = 0.25;
    private int cup, sugar, ice;
    private boolean isAddOn, isDiscount;
    private double price;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        orderList = FXCollections.observableArrayList();
        buttonActions();
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

        if(!ErrorPrompts.warning_confirmation(new ActionEvent())) return;

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

        Order order = new Order(selectedItem,cup,sugar,ice,isAddOn,selectedItem,0,isDiscount);

        // check if order already existed
        boolean found = false;
        for(Order ord : orderHashMap.keySet()) {
            if(ord.getItem().equals(selectedItem)) {
                found = true;
                break;
            }
        }
        if(found) {
            orderList.add(order);
            orderHashMap.replace(order,orderHashMap.get(order));
        }
        else {
            orderList.add(order);
            orderHashMap.put(order,1);
        }

        cashierController.catchOrderDetails(orderList,orderHashMap);
        Stage stage = (Stage) confirmButton.getScene().getWindow();
        stage.close();
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

        if(isDiscount) temp = temp - (temp * discount_rate);

        return temp;
    }

    // This method is used to inject cashier controller here
    static void injectCashierController(CashierController cc) {
        cashierController = cc;
    }

    // This method is used to catch information from CashierScreen
    void catchInformation(Item selectedItem, HashMap<Item,String> ihm,
                  HashMap<Order, Integer> ohm, ObservableList<Order> orderList) {
        this.selectedItem = selectedItem;
        this.orderList = FXCollections.observableArrayList(orderList);
        this.itemHashMap = new HashMap<>(ihm);
        this.orderHashMap = new HashMap<>(ohm);
        this.itemHashSet = new HashSet<>(ihm.values());
        itemImage.setImage(selectedItem.item_image.getImage());
        orderPriceLabel.setText(Double.toString(selectedItem.item_price));
        orderNameLabel.setText(selectedItem.item_name);
        price = selectedItem.item_price;
    }

}
