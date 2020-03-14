package project.controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.*;

public class EditModeController {
    /*
    * @TODO REWRITE THE TEXT FILE OF ALL ITEMS
    *   ALL ITEMS FROM HASH MAP -> item_info.txt
    * EditModePane - main pane of this window
    * tabPane - contains all the items
    * screenStatus - label that state the status of the screen TODO can be deleted
    * itemHashMap - hash map of all items in the shop (caught from previous window)
    * itemHashSet - hash set of all item types in the shop
    */

    public BorderPane EditModePane;
    public JFXTabPane tabPane;
    public Label screenStatus;
    private HashMap<Item,String> itemHashMap;
    private Set<String> itemHashSet;
    private Item selectedItem;

    public void addItem(ActionEvent e) throws IOException {
        EditModePane.setOpacity(.25);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AddItemScreen.fxml"));
        Parent root = loader.load();
        AddItemController addItemController = loader.getController();
        AddItemController.injectEditModeController(this);
        addItemController.catchInformation(itemHashMap);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        EditModePane.setOpacity(1.0);
    }

    public void deleteItem(ActionEvent e) throws IOException {
        if (isItemSelected()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/DeleteItemScreen.fxml"));
            Parent root = loader.load();
            DeleteItemController.injectEditModeController(this);
            DeleteItemController deleteItemController = loader.getController();
            deleteItemController.catchInformation(itemHashMap,selectedItem);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }

        else  screenStatus.setText("Please select an item to be edited!");
    }

    public void editItem(ActionEvent e) throws IOException {
        if (isItemSelected()) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditItemScreen.fxml"));
            Parent root = loader.load();
            EditItemController editItemController = loader.getController();
            EditItemController.injectEditModeController(this);
            editItemController.catchInformation(itemHashMap, selectedItem);
            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.initStyle(StageStyle.UNDECORATED);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.showAndWait();
        }
        else  screenStatus.setText("Please select an item to be edited!");
    }

    public void goToHomeScreen() throws IOException {
        boolean confirm = ErrorPrompts.warning_home_screen(new ActionEvent());
        if (!confirm) return;

        // loads new fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/HomeScreen.fxml"));
        Parent root = loader.load();

        // fxmlloader -> parent -> controller -> scene -> stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) EditModePane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public boolean isItemSelected() {
       // isSelected - flag if an item is selected or not
       boolean isSelected = false;
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
               isSelected = true;
               break;
           }
       }

       // checker
       if(isSelected) screenStatus.setText(selectedItem.item_name + " is selected.");
       else screenStatus.setText("No item is selected.");

       return isSelected;
    }

    // catch information from HomeScreenController
    void catchInformation(HashMap<Item, String> hm) {
        this.itemHashMap = new HashMap<>(hm);
        this.itemHashSet = new HashSet<>(hm.values());

        // checker
        System.out.println(itemHashMap.size());            // Expected : X
        System.out.println(itemHashSet.size());            // Expected : X

        // generate tabs
        generateTabs();
    }

    // generate tabs to tab pane
    public void generateTabs() {
        /*
         * Tab names are based from the item type of the shop
         * e.g. Red, Black, Blue, ... , AddOns.
         * see main folder <MilkTea> -> 'models' folder for information
         *
         * REMINDER FOR ITEM DISPLAYING:
         *  TILE PANE : NOT USED (commented out)
         *  LIST VIEW : USED
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
            Tab tab = new Tab(currentIterator);
//            Currently commented out since tile pane does not work with the author's taste
//            TilePane tilePane = new TilePane(50,50);
//            tilePane.setOrientation(Orientation.HORIZONTAL);
//            tilePane.setPrefColumns(4);

            /* get all items for specific item type in the hash set*/
            for(Item item: itemHashMap.keySet()) {
                if(item.getItem_type().equals(currentIterator)) {
//                  tilePane.getChildren().add(item.item_display);
                    tempItemListView.add(item.item_display);
                }
            }


            itemListView.setItems(tempItemListView);
//            tab.setContent(tilePane); // FOR TILE PANE
            tab.setContent(itemListView); // FOR LIST VIEW
            tabPane.getTabs().add(tab);
        }
    }

    // catch information from EditMode-CHILDREN (Add/Edit/Delete)
    void catchAndUpdateInformation(HashMap<Item, String> hm) {
        this.itemHashMap = new HashMap<>(hm);
        this.itemHashSet = new HashSet<>(hm.values());

        // clear current tab panes
        tabPane.getTabs().clear();

        // generate updated tab panes
        generateTabs();
    }
}
