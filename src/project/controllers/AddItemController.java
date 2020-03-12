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

public class AddItemController {
    // FXML FILE: AddItemScreen
    private static final String default_init_dir = "src/project/image";
    public AnchorPane AddItemPane;
    public ImageView itemImageView;
    public JFXTextField itemNameField, itemTypeField, itemPriceField;
    public Label itemPathField, screenStatus;
    public JFXButton cancelButton, confirmButton;

    public void selectItemImage(ActionEvent e) throws IOException {
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(default_init_dir));
        file = fileChooser.showOpenDialog(confirmButton.getScene().getWindow());
        /* check if file is a valid file */
        if(!file.isFile()) return;
        /* only accept image files */
        if(!ErrorPrompts.isPicture(file)) return;
        itemPathField.setText(file.getPath());
        itemImageView.setImage(new Image(file.getName()));
    }

    public void setCancelButton(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setConfirmButton(ActionEvent e) {
        // @TODO
    }
}
