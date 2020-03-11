package project.controllers;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class EditItemController {
    // FXML FILE: EditItemScreen

    public AnchorPane EditItemPane;
    public ImageView itemImageView;
    public JFXTextField itemNameField, itemTypeField, itemPriceField;
    public Label itemPathField, screenStatus;
    public JFXButton cancelButton, confirmButton;

    public void selectItemImage(ActionEvent e) throws IOException {
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("resources/assets"));
        file = fileChooser.showOpenDialog(confirmButton.getScene().getWindow());
        if(!file.isFile()) return;
        if(!file.getPath().endsWith(".png") || !file.getPath().endsWith(".jpg")
                || !file.getPath().endsWith(".gif")) return;
        itemPathField.setText(file.getPath());
    }

    public void setCancelButton(ActionEvent e) {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    public void setConfirmButton(ActionEvent e) {
        // @TODO
    }
}
