package project.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class CashierController {
    public BorderPane CashierPane;

    public void goToHomeScreen() throws IOException {
        boolean confirm = ErrorPrompts.warning_home_screen(new ActionEvent());
        if (!confirm) return;

        // loads new fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/HomeScreen.fxml"));
        Parent root = loader.load();

        // fxmlloader -> parent -> controller -> scene -> stage
        Scene scene = new Scene(root);
        Stage stage = (Stage) CashierPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
