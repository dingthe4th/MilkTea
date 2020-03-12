package project.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class HomeScreenController implements Initializable {
    private static final File default_file_path = new File("src/project/text/item_info/item_info.txt");
    public GridPane HomeScreenPane;
    public HashMap<Item,String> itemHashMap = new HashMap<>();

    /*
    *@TODO
    *   Function 4 : What is it?
    *   Function 5 : What is it? ==> TEMPORARILY QUITS PROGRAM
    *   @function_format
    *   window_xxx <- opening new window about 'xxx'
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

    public void window_statistics(ActionEvent e) {
        // TODO
    }

    public void window_edit_mode(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditModeScreen.fxml"));
        Parent root = loader.load();
        EditModeController editModeController = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = (Stage) this.HomeScreenPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void window_function4(ActionEvent e) {
        // TODO
    }

    public void window_exit(ActionEvent e) {
        // TODO This is function 5 stated above
        boolean confirm = ErrorPrompts.warning_confirmation(new ActionEvent());
        if(confirm) System.exit(0);
    }

    public void loadAllItems() throws IOException {
        FileReader fr = new FileReader(default_file_path);
        BufferedReader br = new BufferedReader(fr);
        String line;
        while((line = br.readLine()) !=null) {
            String[] words = line.split(" ");
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

    @Override public void initialize(URL location, ResourceBundle resources) {
        try {
            loadAllItems();

            // checker
            // System.out.println("Size of hash map: " + itemHashMap.size());
            // System.out.println("Set of values: " + itemHashMap.values());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
