package project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class EditModeController {
    public BorderPane EditModePane;
    public void addItem(ActionEvent e) throws IOException {
        EditModePane.setOpacity(.25);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/AddItemScreen.fxml"));
        Parent root = loader.load();
        AddItemController addItemController = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
        EditModePane.setOpacity(1.0);
    }

    public void deleteItem(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/DeleteItemScreen.fxml"));
        Parent root = loader.load();
        DeleteItemController deleteItemController = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void editItem(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/EditItemScreen.fxml"));
        Parent root = loader.load();
        EditItemController editItemController = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public void goToHomeScreen() throws IOException {
        boolean confirm = ErrorPrompts.warning_home_screen(new ActionEvent());
        if (!confirm) return;

        // loads new fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/HomeScreen.fxml"));
        Parent root = loader.load();

        // fxmlloader -> parent -> controller -> scene -> stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) EditModePane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
