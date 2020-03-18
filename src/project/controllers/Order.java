package project.controllers;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

import java.util.List;

public class Order {
    /*
    * Parameters per order
    * @param
    *   size = cup size (wink wink)
    *   sugar = sugar level
    *   ice = ice level
    *   addOnQty TODO
    *       should be in a list
    *   isAddOn -> is there an addOn
    *   discount -> is it discounted
    *   order = item that is ordered
    *   addOn TODO
    *       should be in a list from CashierScreen
    */
    private final static double discount_rate = 0.25;
    private int size, sugar, ice, addOnQty;
    private boolean isAddOn, discount;
    private Item order, addOn;

    // TableView details
    SimpleStringProperty orderDetails;
    SimpleDoubleProperty orderPrice;

    /*
    * Guide for argument values
    * For order -> just the Item
    * For size
    *    1 ----- small
     *   2 ----- medium
     *   3 ----- large
    *             /*
     *   NOTE
     *  input    sugar level
     *   0 ----- 0%
     *   1 ----- 25%
     *   2 ----- 50%
     *   3 ----- 75%
     *   4 ----- 100%
     *            /*
     *   NOTE
     *   1 ----- regular
     *   2 ----- less
     *   3 ----- no ice
     */
    Order(Item order, int size, int sugar, int ice, boolean isAddOn, Item addOn, int addOnQty, boolean discount) {
        this.order = order;
        this.size = size;
        this.sugar = sugar;
        this.ice = ice;
        this.isAddOn = isAddOn;
        this.addOnQty = addOnQty;
        this.addOn = addOn;
        this.discount = discount;

        orderDetails = new SimpleStringProperty(orderFullDetails(this));
        orderPrice = new SimpleDoubleProperty(calculateOrder(this));
    }

    static double calculateOrder(Order item) {
        // initial price is the main item price
        double total = item.order.item_price;

        // add to total based on size
        switch(item.size) {
            /*
            *   NOTE
            *   1 ----- small
            *   2 ----- medium
            *   3 ----- large
            */
            case 1:
                total += 0;
                break;
            case 2:
                total += 20;
                break;
            case 3:
                total += 45;
                break;
            default:
                break;
        }

        // add to total based on sugar level
        switch(item.sugar) {
            /*
            *   NOTE
            *  input    sugar level
            *   0 ----- 0%
            *   1 ----- 25%
            *   2 ----- 50%
            *   3 ----- 75%
            *   4 ----- 100%
            */
            case 0:
                total += 0;
                break;
            case 1:
                total += 5;
                break;
            case 2:
                total += 10;
                break;
            case 3:
                total += 15;
                break;
            case 4:
                total += 20;
                break;
            default:
                break;
        }

        // add to total if there is addOn order
        if(item.isAddOn) {
            total += (item.addOn.item_price*item.addOnQty);
        }

        // apply discount if applicable
        if(item.discount) {
            total = total - (total * discount_rate);
        }

        // return total amount of the order
        return total;
    }

    static String orderFullDetails(Order item) {
        String order = item.order.item_name;
        // add to order details based on size
        switch(item.size) {
            /*
             *   NOTE
             *   1 ----- small
             *   2 ----- medium
             *   3 ----- large
             */
            case 1:
                order = order.concat(" S");
                break;
            case 2:
                order = order.concat(" M");
                break;
            case 3:
                order = order.concat(" L");
                break;
            default:
                break;
        }

        // add ice details
        switch(item.ice) {
            /*
             *   NOTE
             *   1 ----- regular
             *   2 ----- less
             *   3 ----- no ice
             */
            case 1:
                order = order.concat(" R.I.");
                break;
            case 2:
                order = order.concat(" L.I.");
                break;
            case 3:
                order = order.concat(" N.I.");
                break;
            default:
                break;
        }

        // add to order details on sugar level
        switch(item.sugar) {
            /*
             *   NOTE
             *  input    sugar level
             *   0 ----- 0%
             *   1 ----- 25%
             *   2 ----- 50%
             *   3 ----- 75%
             *   4 ----- 100%
             */
            case 0:
                order = order.concat(" 0%\n");
                break;
            case 1:
                order = order.concat(" 25%\n");
                break;
            case 2:
                order = order.concat(" 50%\n");
                break;
            case 3:
                order = order.concat(" 75%\n");
                break;
            case 4:
                order = order.concat(" 100%\n");
                break;
            default:
                break;
        }
        // add to total if there is addOn order
        if(item.isAddOn) {
            String temp = Integer.toString(item.addOnQty);
            order = order.concat(temp + " pc/s " + item.addOn.item_name);
        }
        return order;
    }

    public Item getItem() {
        return this.order;
    }

    /*
    * Setter and Getters
    * of @param orderDetails & orderPrice
    * for the TableView
    */

    public String getOrderDetails() {
        return orderDetails.get();
    }

    public SimpleStringProperty orderDetailsProperty() {
        return orderDetails;
    }


    public SimpleDoubleProperty orderPriceProperty() {
        return orderPrice;
    }

    public double getOrderPrice() {
        return orderPrice.get();
    }


    public void setOrderDetails(String orderDetails) {
        this.orderDetails.set(orderDetails);
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice.set(orderPrice);
    }

}
