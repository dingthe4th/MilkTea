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

public class AddItemController {
    // FXML FILE: AddItemScreen

    public AnchorPane AddItemPane;
    public ImageView itemImageView;
    public JFXTextField itemNameField, itemTypeField, itemPriceField;
    public Label itemPathField, screenStatus;
    public JFXButton cancelButton, confirmButton;

    public void selectItemImage(ActionEvent e) throws IOException {
        File file;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("resources/assets"));
        file = fileChooser.showOpenDialog(confirmButton.getScene().getWindow());
        System.out.println(file.getName());
        //        if(!file.isFile()) return;
//        if(!file.getName().endsWith(".png") || !file.getName().endsWith(".jpg")
//            || !file.getName().endsWith(".gif")) return;
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
