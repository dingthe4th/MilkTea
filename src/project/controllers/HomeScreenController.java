package project.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {
    /*
    * All item details will come from
    * @param default_file_path
    * TODO transferring it to CSV file may be better
    *  If desired, please edit all functions related with file
    *  reading, editing, overwriting, etc.
    *  Don't worry, all functions are fully commented.
    * */
    private static final File default_file_path = new File("src/project/text/item_info/item_info.txt");
    public GridPane HomeScreenPane;
    public HashMap<Item,String> itemHashMap = new HashMap<>();
    private HashMap<Integer, Integer> cupsOrderedHashMap = new HashMap<>();
    private HashMap<Item,Integer> main_ItemOrderedHashMap = new HashMap<>();
    private double totalSalesAmount;


    /*
    * FXML : CashierScreen.fxml
    * Controller : CashierController
    * Handles the functions of the cashier mode
    * in the point of sale system
    * This is the main function of the program
    */
    public void window_cashier(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/CashierScreen.fxml"));
        Parent root = loader.load();
        CashierController cashierController = loader.getController();
        cashierController.catchInformation(itemHashMap);
        Scene scene = new Scene(root);
        Stage stage = (Stage) this.HomeScreenPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /*TODO
     * FXML : _____
     * Controller : _________
     * Displays the statistics of the sale of the store
     * */
    public void window_statistics(ActionEvent e) throws IOException {
        HomeScreenPane.setOpacity(.25);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/StatisticsScreen.fxml"));
        Parent root = loader.load();
        StatisticsController statisticsController = loader.getController();
        statisticsController.catchInformation(cupsOrderedHashMap,main_ItemOrderedHashMap,totalSalesAmount);
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        HomeScreenPane.setOpacity(1.0);
    }

    /*
     * FXML : EditModeScreen.fxml
     * Controller : EditModeController
     * Handles the functions of the item editing mode
     * in the point of sale system
     * User can choose to add/edit/delete item from the list of items
     */
    public void window_edit_mode(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditModeScreen.fxml"));
        Parent root = loader.load();
        EditModeController editModeController = loader.getController();
        editModeController.catchInformation(itemHashMap);
        Scene scene = new Scene(root);
        Stage stage = (Stage) this.HomeScreenPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    /* Handles program termination */
    public void window_exit(ActionEvent e) {
        // TODO This is function 5 stated above
        boolean confirm = ErrorPrompts.warning_confirmation(new ActionEvent());
        if(confirm) System.exit(0);
    }

    /* This method reads all items from the text
    * file file_info.txt.
    * TODO protect this .txt file
    */
    public void loadAllItems() throws IOException {
        FileReader fr = new FileReader(default_file_path);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) !=null) {
            String[] words = line.split(",");
            try {
                String name = words[0];
                String type = words[1];
                double price = Double.parseDouble(words[2]);
                String path = words[3];
                Item item = new Item(name,type,price,path);
                itemHashMap.put(item,type);
            }
            catch (ArrayIndexOutOfBoundsException ignored) {
            }
        }
    }

    /* Catch information from CashierScreen */
    public void catchStatisticsInformation(HashMap<Integer,Integer> cups, HashMap<Item,Integer> itemOrders, double totalSalesAmount) {
        main_ItemOrderedHashMap = new HashMap<>(itemOrders);
        cupsOrderedHashMap = new HashMap<>(cups);
        this.totalSalesAmount = totalSalesAmount;
    }

    /* calls loadAllItems() */
    @Override public void initialize(URL location, ResourceBundle resources) {
        try {
            loadAllItems();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
