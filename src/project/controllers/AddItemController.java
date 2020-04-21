package project.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class AddItemController implements Initializable {
    // FXML FILE: AddItemScreen

    /*
    * @param
    *   editModeController -> controller from the previous window
    *   itemHashMap -> hash map of all items in the shop (caught from previous window)
    *   itemHashSet -> hash set of all item types in the shop
    *   AddItemPane -> main panel window of this .fxml window
    *   itemImageView -> store the image of the item if prompted otherwise store default image
    *   itemNameField -> stores the name of the new item
    *   itemTypeField -> stores the type of the new item
    *   itemPriceField -> stores the price of the new item
    *   itemPathField -> stores the path of the item image
    *   screenStatus -> displays the status of the screen , TODO can be removed later
    *   cancelButton -> go back to previous screen (Edit mode controller)
    *   confirmButton -> go back to previous screen (EMC -> update current hash map)
    * */
    private static AddItemController instanceOf;
    private static EditModeController editModeController;
    private HashMap<Item, String> itemHashMap;
    private Set<String> itemHashSet;
    private static final String default_init_dir = "src/project/image";
    public AnchorPane AddItemPane;
    public ImageView itemImageView;
    public JFXTextField itemNameField, itemTypeField, itemPriceField;
    public Label screenStatus;
    public Label itemPathField;
    public JFXButton cancelButton, confirmButton;

    public void initialize(URL location, ResourceBundle resources) {
        instanceOf = this;
    }

    // gets the itemImage -> itemImageView
    public void selectItemImage(ActionEvent e) throws IOException {
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(System.getProperty("user.dir")));
        file = fileChooser.showOpenDialog(confirmButton.getScene().getWindow());
        /* check if file is a valid file */
        if(!file.isFile()) return;
        /* only accept image files */
        if(!ErrorPrompts.isPicture(file)) return;
        itemPathField.setText(file.getPath());
        itemImageView.setImage(new Image(file.toURI().toURL().toExternalForm()));
    }

    // handles cancel button — just close the stage without return value
    public void setCancelButton(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /*
    * This method handles the confirm button
    * Given that the inputs are valid (e.g. no blank entries, invalid entries)
    * a new item will be added to the current item hash map
     */
    public void setConfirmButton(ActionEvent e) throws IOException {
        if (isValidInput()) {
            createNewItem();
            screenStatus.setText("Item added successfully!");
            EditModeController.catchAndUpdateInformation(itemHashMap);
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        }
    }

    // handles new item creation
    void createNewItem() throws IOException {
        String a = itemNameField.getText();
        String b = itemTypeField.getText();
        double c = Double.parseDouble(itemPriceField.getText());
        String d = itemPathField.getText();
        ImageView e = itemImageView;
        InputStream is = getClass().getResourceAsStream(d);
        // creates new item
        Item item = new Item(a,b,c,d);
        // appends item to the hash map
        this.itemHashMap.put(item,b);
        // appends item type to the hash set (values <- types)
        this.itemHashSet.add(b);

    }

    // checks if inputs in the entries are valid (blank entries, invalid inputs, etc)
    private boolean isValidInput() {
        try {
            Double.parseDouble(itemPriceField.getText());
            if(itemNameField.getText().isEmpty()) return false;
            if(itemPathField.getText().isEmpty()) return false;
            if(itemTypeField.getText().isEmpty()) return false;
        }
        catch (NumberFormatException ignore) {
            return false;
        }
        return true;
    }

    // catch information from EditModeController
    static void catchInformation(HashMap<Item, String> hm) {
        instanceOf.itemHashMap = new HashMap<>(hm);
        instanceOf.itemHashSet = new HashSet<>(hm.values());

        // checker
        System.out.println(instanceOf.itemHashMap.size());            // Expected : X
        System.out.println(instanceOf.itemHashSet.size());            // Expected : X
    }

    /*
       This function injects EditModeController into this controller
       < Gets called from EditModeController to be injected>
       Confused about what this function does? So am I!
       Go to this link and study them:
       https://www.java-forums.org/javafx/97288-how-return-value-modal-window-back-parent.html
    */
    static void injectEditModeController(EditModeController emc) {
        editModeController = emc;
    }
}
