package project.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeScreenController {
    public GridPane HomeScreenPane;
    /*
    *@TODO
    *   Function 4 : What is it?
    *   Function 5 : What is it?
    *   @function_format
    *   window_xxx <- opening new window about 'xxx'
    */

    public void window_cashier(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/CashierScreen.fxml"));
        Parent root = loader.load();
        CashierController cashierController = loader.getController();
        Scene scene = new Scene(root);
        Stage stage = (Stage) this.HomeScreenPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void window_statistics(ActionEvent e) {

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

    }

    public void window_function5(ActionEvent e) {

    }

}
