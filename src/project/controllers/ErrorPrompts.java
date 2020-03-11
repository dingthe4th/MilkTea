package project.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class ErrorPrompts {
    /*
     *@TODO
     *   @function_format
     *   warning_xxx <- opening new
     *   warning window about 'xxx'
     */

    public static void warning_void_transaction(ActionEvent e) {

    }

    public static boolean warning_home_screen(ActionEvent e) {
        Alert exitPrompt = new Alert(Alert.AlertType.CONFIRMATION);
        exitPrompt.setHeaderText("CONFIRM.");
        exitPrompt.setContentText("Back to main menu? \n TEXT TEXT TEXT.");
        Optional<ButtonType> userChoice = exitPrompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static void warning_confirmation(ActionEvent e) {

    }
}
