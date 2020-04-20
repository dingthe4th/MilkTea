package project.controllers;
import com.jfoenix.controls.JFXTextArea;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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

    private static HomeScreenController instanceOf;

    public AnchorPane HomeScreenPane;

    public JFXTextArea aboutTFTG;
    public ImageView TFTGLogo;
    public Rectangle palette00;
    public Rectangle palette01;
    public Rectangle palette02;
    public Rectangle palette03;
    public Rectangle palette04;

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


        Parent root = FXMLLoader.load(getClass().getResource("/project/fxml/CashierScreen.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) HomeScreenPane.getScene().getWindow();
        stage.setScene(scene);
        CashierController.catchInformation(itemHashMap);
        stage.show();
    }

    /*TODO
     * FXML : _____
     * Controller : _________
     * Displays the statistics of the sale of the store
     * */
    public void window_statistics(ActionEvent e) throws IOException {
        HomeScreenPane.setOpacity(.25);
        Parent root =FXMLLoader.load(getClass().getResource("/project/fxml/StatisticsScreen.fxml"));
        StatisticsController.catchInformation(cupsOrderedHashMap,main_ItemOrderedHashMap,totalSalesAmount);
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

        Parent root = FXMLLoader.load(getClass().getResource("/project/fxml/EditModeScreen.fxml"));
       EditModeController.catchInformation(itemHashMap);
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

        /*
        InputStream is = getClass().getResourceAsStream("3Columns.csv");
InputStreamReader isr = new InputStreamReader(is);
         */
        /*
        InputStream is = getClass().getResourceAsStream("/project/text/item_info/item_info.txt");*/
        File f = new File("null");
        try {

//            uncomment the following and comment the File statment
            File pfile = new File(HomeScreenController.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
            f = new File(pfile.getParentFile().getAbsolutePath()+"/project/text/item_info/item_info.txt");
          // f = new File("project/text/item_info/item_info.txt");
        }
        catch (Exception e){
            System.out.println(e);
        }

        FileReader fr = new FileReader(f);
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
    public static void catchStatisticsInformation(HashMap<Integer,Integer> cups, HashMap<Item,Integer> itemOrders, double totalSalesAmount) {
        instanceOf.main_ItemOrderedHashMap = new HashMap<>(itemOrders);
        instanceOf.cupsOrderedHashMap = new HashMap<>(cups);
        instanceOf.totalSalesAmount = totalSalesAmount;
    }

    public void logoutUser(ActionEvent e) throws IOException {
        boolean confirm = ErrorPrompts.warning_logout(new ActionEvent());
        if (!confirm) return;

        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/LoginScreen.fxml"));
        Parent root = loader.load();
        //HomeScreenController hsc = loader.getController();
        //hsc.catchStatisticsInformation(cupsOrderedHashMap,main_ItemOrderedHashMap,totalSalesAmount);

        // fxmlloader -> parent -> controller -> scene -> stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) HomeScreenPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void loadAnimations(){
        FadeTransition fadeInLogo = new FadeTransition(Duration.seconds(0.5), TFTGLogo);
        fadeInLogo.setFromValue(0.0);
        fadeInLogo.setToValue(1.0);
        fadeInLogo.play();

        FadeTransition fadeInAbout = new FadeTransition(Duration.seconds(0.5), aboutTFTG);
        fadeInAbout.setFromValue(0.0);
        fadeInAbout.setToValue(1.0);

        FadeTransition fadeInPalette00 = new FadeTransition(Duration.seconds(0.5), palette00);
        fadeInPalette00.setFromValue(0.0);
        fadeInPalette00.setToValue(1.0);
        FadeTransition fadeInPalette01 = new FadeTransition(Duration.seconds(0.5), palette01);
        fadeInPalette01.setFromValue(0.0);
        fadeInPalette01.setToValue(1.0);
        FadeTransition fadeInPalette02 = new FadeTransition(Duration.seconds(0.5), palette02);
        fadeInPalette02.setFromValue(0.0);
        fadeInPalette02.setToValue(1.0);
        FadeTransition fadeInPalette03 = new FadeTransition(Duration.seconds(0.5), palette03);
        fadeInPalette03.setFromValue(0.0);
        fadeInPalette03.setToValue(1.0);
        FadeTransition fadeInPalette04 = new FadeTransition(Duration.seconds(0.5), palette04);
        fadeInPalette04.setFromValue(0.0);
        fadeInPalette04.setToValue(1.0);

        SequentialTransition sequence = new SequentialTransition(fadeInLogo, fadeInAbout, fadeInPalette00, fadeInPalette01,fadeInPalette02,fadeInPalette03,fadeInPalette04);
        sequence.play();
    }

    /* calls loadAllItems() */
    @Override public void initialize(URL location, ResourceBundle resources) {
        instanceOf = this;

        try {
            loadAllItems();
            loadAnimations();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
