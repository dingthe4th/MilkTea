package project.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class EditItemController {
    // FXML FILE: EditItemScreen
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
    private static EditModeController editModeController;
    private HashMap<Item, String> itemHashMap;
    private Set<String> itemHashSet;
    private static final String default_init_dir = "src/project/image";
    public AnchorPane EditItemPane;
    public ImageView itemImageView;
    public JFXTextField itemNameField, itemTypeField, itemPriceField;
    public Label itemPathField, screenStatus;
    public JFXButton cancelButton, confirmButton;
    private Item selectedItem;

    // gets the itemImage -> itemImageView
    public void selectItemImage(ActionEvent e) throws IOException {
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(default_init_dir));
        file = fileChooser.showOpenDialog(confirmButton.getScene().getWindow());
        /* check if file is a valid file */
        if(!file.isFile()) return;
        /* only accept image files */
        if(!ErrorPrompts.isPicture(file)) return;
        itemPathField.setText(default_init_dir+"/"+file.getName());
        itemImageView.setImage(new Image(file.getName()));
    }

    // handles cancel button — just close the stage without return value
    public void setCancelButton(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /*
     * This method handles the confirm button
     * Given that the inputs are valid (e.g. no blank entries, invalid entries)
     * the selected item will be updated to the current item hash map
     */
    public void setConfirmButton(ActionEvent e) throws IOException {
        if (isValidInput()) {
            updateCurrentItem();
            screenStatus.setText("Item edited successfully!");

            // this line returns the updated hash map to the previous screen
            // which is edit mode screen
            editModeController.catchAndUpdateInformation(itemHashMap);
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        }
    }

    // handles the update of the selected item
    void updateCurrentItem() throws IOException {
        String a = itemNameField.getText();
        String b = itemTypeField.getText();
        double c = Double.parseDouble(itemPriceField.getText());
        String d = itemPathField.getText();
        ImageView e = itemImageView;

        itemHashMap.remove(selectedItem);
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

    /*
    *   catch information from EditModeController
    *   gets the hash map and selected item from the EditModeScreen
    *   displays the selected item details to this .fxml window
    */
    void catchInformation(HashMap<Item, String> hm, Item selectedItem) {
        this.itemHashMap = new HashMap<>(hm);
        this.itemHashSet = new HashSet<>(hm.values());
        this.selectedItem = selectedItem;

        // display selected item details
        itemNameField.setText(selectedItem.item_name);
        itemTypeField.setText(selectedItem.item_type);
        itemPriceField.setText(Double.toString(selectedItem.item_price));
        itemPathField.setText(selectedItem.item_path);
        itemImageView.setImage(selectedItem.item_image.getImage());
        screenStatus.setText("Editing item: " + selectedItem.item_name);

        // checker
        System.out.println(itemHashMap.size());            // Expected : X
        System.out.println(itemHashSet.size());            // Expected : X
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
