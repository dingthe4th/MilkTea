package project.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

public class DeleteItemController implements Initializable {
    // FXML FILE: DeleteItemScreen
    private static DeleteItemController instanceOf;
    private static EditModeController editModeController;
    private HashMap<Item, String> itemHashMap;
    private Set<String> itemHashSet;
    public ImageView itemImageView;
    public Label itemNameField, itemTypeField, itemPriceField;
    public Label itemPathField, screenStatus;
    public JFXButton cancelButton, confirmButton;
    private Item selectedItem;
    public AnchorPane DeleteItemPane;

    public void initialize(URL location, ResourceBundle resources) {
        instanceOf = this;
    }

    // handles cancel button — just close the stage without return value
    public void setCancelButton(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    /*
     * This method handles the confirm button
     * Once confirming, the selected item will be deleted to the item hash map
     * and return to the EditModeScreen
    */
    public void setConfirmButton(ActionEvent e) throws IOException {
        if (ErrorPrompts.warning_delete_item(new ActionEvent())) {
            deleteCurrentItem();
            screenStatus.setText("Item deleted successfully!");
            // this line returns the updated hash map to the previous screen
            // which is edit mode screen
            editModeController.catchAndUpdateInformation(itemHashMap);
            Stage stage = (Stage) confirmButton.getScene().getWindow();
            stage.close();
        }
    }

    // handles the deletion of the selected item
    private void deleteCurrentItem()  {
        itemHashMap.remove(selectedItem);
    }

    /*
     *   catch information from EditModeController
     *   gets the hash map and selected item from the EditModeScreen
     *   displays the selected item details to this .fxml window
     */
    public static void catchInformation(HashMap<Item, String> hm, Item item) {
        instanceOf.itemHashMap = new HashMap<>(hm);
        instanceOf.itemHashSet = new HashSet<>(hm.values());
        instanceOf.selectedItem = item;

        // display selected item details
        instanceOf.itemNameField.setText(item.item_name);
        instanceOf.itemTypeField.setText(item.item_type);
        String priceText = Double.toString(item.item_price);
        instanceOf.itemPriceField.setText(priceText);
        instanceOf.itemPathField.setText(item.item_path);
        instanceOf.itemImageView.setImage(item.item_image.getImage());
        instanceOf.screenStatus.setText("Deleting item: " + item.item_name);
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

