package project.controllers;

import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTabPane;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Orientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class CashierController {
    /*
    * Previous Screen: HomeScreen.fxml | Controller: HomeScreenController
    * This class handles the order management of the program
    */
    public BorderPane CashierPane;
    public JFXTabPane tabPane;
    private HashMap<Item,String> itemHashMap;
    private Set<String> itemHashSet;

    // catch information from HomeScreen
    void catchInformation(HashMap<Item, String> hm) {
        this.itemHashMap = new HashMap<>(hm);
        this.itemHashSet = new HashSet<>(hm.values());

        // checker
        System.out.println(itemHashMap.size());            // Expected : 5
        System.out.println(itemHashSet.size());            // Expected : 4

        // generate tabs
        generateTabs();
    }

    // go back to home screen
    public void goToHomeScreen() throws IOException {
        boolean confirm = ErrorPrompts.warning_home_screen(new ActionEvent());
        if (!confirm) return;

        // loads new fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/HomeScreen.fxml"));
        Parent root = loader.load();

        // fxmlloader -> parent -> controller -> scene -> stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) CashierPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    // generate tabs to tab pane
    public void generateTabs() {
        /*
        * Tab names are based from the item type of the shop
        * e.g. Red, Black, Blue, ... , AddOns.
        * see main folder <MilkTea> -> 'models' folder for information
        *
        * TODO:
        *  choose how to click an item registered | tilepane is not working as is
        *  experimenting list view
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
            TilePane tilePane = new TilePane(50,50);
            tilePane.setOrientation(Orientation.HORIZONTAL);
            tilePane.setPrefColumns(4);

            /* get all items for specific item type in the hash set*/
            for(Item item: itemHashMap.keySet()) {
                if(item.getItem_type().equals(currentIterator)) {
//                  tilePane.getChildren().add(item.item_display);
                    tempItemListView.add(item.item_display);
                }
            }

            itemListView.setItems(tempItemListView);
//            tab.setContent(tilePane); // FOR TILE PANE | NOT WORKING
            tab.setContent(itemListView);
            tabPane.getTabs().add(tab);
        }
    }
}
