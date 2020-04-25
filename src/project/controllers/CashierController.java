package project.controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CashierController implements Initializable {

    /*
    * Current Screen: CashierScreen.fxml | Controller: CashierController
    * Previous Screen: HomeScreen.fxml | Controller: HomeScreenController
    * This class handles the order management of the program
    */

    private static CashierController instanceOf;

    public BorderPane CashierPane;
    public JFXTabPane tabPane;
    public Label orderNumberLabel, totalItemsSoldLabel, totalSalesLabel;
    public Label orderQuantityLabel, orderSubtotalLabel;
    public ImageView goToHomeImageButton;
    /*
     * @param
     *  orderHashMap
     *      maps the order to its quantity (will be used in the statistics function)
     *      e.g. Item -> chocolate, 1 when ordered at instance 1
     *      When another order has chocolate drink, the 1 will increment to 2
     *  itemAddOnList
     *      this is the array list of the add on items
     *      should be passed to order screen
     *  itemHashMap
     *      hash map of all items in the shop
     *  itemHashSet
     *      set that contains the item types found in the shop
     *  selectedItem
     *      the item that is selected / highlighted
     * */
    private ArrayList<Item> itemAddOnList = new ArrayList<>();
    private HashMap<Integer, Integer> cupsOrderedHashMap = new HashMap<>();
    private HashMap<Item,Integer> main_ItemOrderedHashMap = new HashMap<>();
    private HashMap<Item,Integer> current_itemOrderedHashMap = new HashMap<>();
    private HashMap<Item,String> itemHashMap;
    private Set<String> itemHashSet;
    private Item selectedItem;
    private boolean newOrder = true;
    private double totalSalesAmount = 0;

    /*
    * @param
    *   orderObservableList = list of all items that is ordered
    *   orderTableView = the main parent of the items ordered (for display)
    *   orderDetailsColumn, orderPriceColumn -> columns for orderTableView
    * */
    private ObservableList<Order> orderObservableList ;
    public TableView<Order> orderTableView;
    public TableColumn<Order, String> orderDetailsColumn;
    public TableColumn<Order, Double> orderPriceColumn;


    @Override public void initialize(URL location, ResourceBundle resources)    {
        instanceOf = this;
        selectedItem = null;

       // CashierPane.getStylesheets().add(getClass().getResource("/text/css/jfxStyle_2.css").toExternalForm());

        // initializing table view and its columns
        orderObservableList = FXCollections.observableArrayList();
        orderTableView.setItems(orderObservableList);
        orderDetailsColumn.setCellValueFactory(new PropertyValueFactory<>("orderDetails"));
        orderPriceColumn.setCellValueFactory(new PropertyValueFactory<>("orderPrice"));
        orderTableView.setPlaceholder(new Label("Click an item to start an order."));
        // Add mouse listener to goToHomeImageButton
        setGoToHomeImageButton();

    }

    private void setGoToHomeImageButton() {
        goToHomeImageButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                try {
                    goToHomeScreen();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //  This method is used to check if an item is selected or not
    private boolean isItemSelected() {
        // gets the list view of item display from the current tab in the tabPane
        JFXListView<HBox> list = (JFXListView<HBox>) tabPane.getSelectionModel().getSelectedItem().getContent();
        // gets the particular HBox container of the selected item
        HBox box = list.getSelectionModel().getSelectedItem();
        // if an item is really selected, check if it is registered in the itemHashMap
        for(Item key : itemHashMap.keySet()) {
            // if the item_display of 'key' is the same as the selected item display 'box'
            // returns isSelected as true, meaning an item is selected
            if (key.item_display.equals(box)) {
                selectedItem = key;
                return true;
            }
        }
        return false;
    }



    // This method is used to go to OrderScreen
    public void goToOrderScreen() throws IOException {
        if(!isItemSelected()) return;
        CashierPane.setOpacity(.25);
        Parent root = FXMLLoader.load(getClass().getResource("/project/fxml/OrderScreen.fxml"));
        OrderController.injectCashierController(this);

        if(newOrder) current_itemOrderedHashMap = new HashMap<>();
        OrderController.catchInformation(selectedItem,itemHashMap,cupsOrderedHashMap,current_itemOrderedHashMap,orderObservableList,itemAddOnList,newOrder);

        Scene scene = new Scene(root);
        Stage stage = new Stage();

        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        CashierPane.setOpacity(1.0);
    }

    // This method is used to go back to home screen
    public void goToHomeScreen() throws IOException {
        boolean confirm = ErrorPrompts.warning_home_screen(new ActionEvent());
        if (!confirm) return;

        // loads new fxml file

        Parent root = FXMLLoader.load(getClass().getResource("/project/fxml/HomeScreen.fxml"));

        HomeScreenController.catchStatisticsInformation(cupsOrderedHashMap,main_ItemOrderedHashMap,totalSalesAmount);

        // fxmlloader -> parent -> controller -> scene -> stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) CashierPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // This method is called if a new order is being made
    public void proceedToNextOrder() {
        if(orderObservableList.isEmpty()) return;

        // Updates the main item ordered HashMap (used in statistics)
        updateMainItemOrderedHashMap();

        // Updates the label of order number
        int orderNumber = Integer.parseInt(orderNumberLabel.getText());
        orderNumberLabel.setText(Integer.toString(orderNumber+1));

        // Updates the statistics variables
        calculationAfterEveryOrder();

        // Clear the table view
        orderObservableList.clear();

        // Resets the labels to 0
        orderSubtotalLabel.setText("0.00");
        orderQuantityLabel.setText("0");

        newOrder = true;
    }

    // This method is used to void an order
    public void voidSelectedItemOrder() {

        if(!ErrorPrompts.warning_void_transaction(new ActionEvent())) return;

        // Removes selected item to the current order list
        Order selectedOrder = orderTableView.getSelectionModel().getSelectedItem();

        // Warns the user that no item is selected
        if (selectedOrder == null) ErrorPrompts.order_void_error(new ActionEvent());

        // Void an order
        if(selectedOrder != null) {
            orderObservableList.remove(selectedOrder);


            // Updates the current_itemOrderedHashMap
            ArrayList<Item> itemsFromDeletedOrder = new ArrayList<>(Order.getAllItemsFromOrder(selectedOrder));

            for(Item item : itemsFromDeletedOrder) {
                if(current_itemOrderedHashMap.containsKey(item))
                    current_itemOrderedHashMap.replace(item,current_itemOrderedHashMap.get(item)-1);
            }
        }
        calculateCurrentOrderTotalPrice();
    }

    // This method is used to calculate the total amount of the current order
    private void calculateCurrentOrderTotalPrice() {
        double sum = 0;
        int qty = 0;
        sum = Order.getTotalOrderPrice(orderObservableList);
        qty = Order.getTotalOrderQuantity(current_itemOrderedHashMap);

        orderQuantityLabel.setText(Integer.toString(qty));
        orderSubtotalLabel.setText(Double.toString(sum));
    }

    // This method is a helper function to proceedToNextOrder function
    // It calculates the total sales amount and number of items sold
    private void calculationAfterEveryOrder() {

        // Counts the total items sold (appends/updates every order completion)
        int totalItemsSold = Order.getTotalOrderQuantity(main_ItemOrderedHashMap);
        // Calculates the total sales for the day (appends/updates every order completion)
        totalSalesAmount += Order.getTotalOrderPrice(orderObservableList);

        // Updates the label every order completion
        totalItemsSoldLabel.setText(Integer.toString(totalItemsSold));
        totalSalesLabel.setText(Double.toString(totalSalesAmount));

    }

    private void updateMainItemOrderedHashMap() {
        for(Item item : current_itemOrderedHashMap.keySet()) {
            if(main_ItemOrderedHashMap.containsKey(item)) {
                int updatedQty = main_ItemOrderedHashMap.get(item) + current_itemOrderedHashMap.get(item);
                main_ItemOrderedHashMap.replace(item,updatedQty);
            } else {
                main_ItemOrderedHashMap.put(item,current_itemOrderedHashMap.get(item));
            }
        }
    }

    // This method is used to catch information from OrderScreen
    void catchOrderDetails(ObservableList<Order> orderList, HashMap<Item,Integer> iohm, HashMap<Integer, Integer> cups, boolean newOrder) {
        cupsOrderedHashMap = new HashMap<>(cups);
        current_itemOrderedHashMap = new HashMap<>(iohm);
        orderObservableList = FXCollections.observableArrayList(orderList);
        orderTableView.setItems(orderObservableList);
        this.newOrder = newOrder;

        calculateCurrentOrderTotalPrice();
    }

    // This method is used to catch information from HomeScreen
    static void catchInformation(HashMap<Item, String> hm) {
        instanceOf.itemHashMap = new HashMap<>(hm);
        instanceOf.itemHashSet = new HashSet<>(hm.values());

        // generate tabs
        instanceOf.generateTabs();

        // Adds all add on items to itemAddOnList
        for(Item item : instanceOf.itemHashMap.keySet()) {
            if(item.item_type.equalsIgnoreCase("add on")) instanceOf.itemAddOnList.add(item);
        }
    }

    // This method is used to generate tabs to tab pane
    private void generateTabs() {
        /*
        * Tab names are based from the item type of the shop
        * e.g. Red, Black, Blue, ... , AddOns.
        * see main folder <MilkTea> -> 'models' folder for information
        * */

        Iterator iterator = itemHashSet.iterator();
        while (iterator.hasNext()) {

            /*
            * itemListView = list for all key items of the current iterator (item type value)
            * e.g.
            * Given hash map:
            *   Milktea_1 : RED
            *   Milktea_2 : BLUE
            *   Milktea_3 : RED
            * From the given, the while loop will have 2 runs, RED and BLUE
            * tempItemListView will store all keys in the map that contains
            * the value 'RED' in loop1 and 'BLUE' in loop2
            *
            * after each loop, itemListView will inherit all entries from tempItemListView
             */

            JFXListView<HBox> itemListView = new JFXListView<>();
            ObservableList<HBox> tempItemListView = FXCollections.observableArrayList();

            /*
            * Setting the list view to be horizontal for better user experience
            * Also the width and height can be fixed, currently the author does
            * not know how to dynamically program the size of the objects within
            * a given system.
             */
            itemListView.setOrientation(Orientation.VERTICAL);
            /*
            * For each loop in while loop, a tab will be generated (with the name of
            * the item_type stored in the HashSet)
             */


            String currentIterator = (String) iterator.next();

            /* prevent from creating a add-on tab */
            if(currentIterator.equalsIgnoreCase("add on")) continue;

            Tab tab = new Tab(currentIterator.toUpperCase());

            /* get all items for specific item type in the hash set*/
            for(Item item: itemHashMap.keySet()) {
                if(item.getItem_type().equals(currentIterator)) {
                    tempItemListView.add(item.item_display);
                }
            }

            /* populate itemListView */
            itemListView.setItems(tempItemListView);

            /* if item is clicked twice, opens order screen */
            itemListView.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent click) {
                    if (click.getClickCount() == 2) {
                        isItemSelected();
                        try {
                            goToOrderScreen();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });

            /* set up tab */
            tab.setContent(itemListView);
            tab.setStyle("-fx-background-color: #f4f4f4");
            tabPane.getTabs().add(tab);
        }
    }

}
