package project.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class ErrorPrompts {
    /*
    This class handles all functions for error prompts,
    for example, if user leave window A, the program
    will display a warning window. This warning window
    will come from here to make each controller class
    not crowded.

     *@TODO
     *   @function_format
     *   warning_xxx <- opening new
     *   warning window about 'xxx'
     */

    public static boolean warning_void_transaction(ActionEvent e) {
        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION);
        prompt.setHeaderText("Voiding Selected Transaction.");
        prompt.setContentText("Proceed?");
        Optional<ButtonType> userChoice = prompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static boolean order_confirmation(ActionEvent e) {
        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION);
        prompt.setHeaderText("Confirming Current Order.");
        prompt.setContentText("Are you sure of this order?");
        Optional<ButtonType> userChoice = prompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static boolean order_void(ActionEvent e) {
        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION);
        prompt.setHeaderText("Voiding Current Order.");
        prompt.setContentText("Are you sure?");
        Optional<ButtonType> userChoice = prompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static void order_void_error(ActionEvent e) {
        Alert prompt = new Alert(Alert.AlertType.INFORMATION);
        prompt.setTitle("");
        prompt.setHeaderText("No order is selected");
        prompt.setContentText("Please select an order to void");
        prompt.showAndWait();
    }

    public static void order_edit_error(ActionEvent e) {
        Alert prompt = new Alert(Alert.AlertType.INFORMATION);
        prompt.setHeaderText("No item is selected");
        prompt.setContentText("Please select an item to edit");
        prompt.showAndWait();
    }

    public static void order_delete_error(ActionEvent e) {
        Alert prompt = new Alert(Alert.AlertType.INFORMATION);
        prompt.setHeaderText("No item is selected");
        prompt.setContentText("Please select an item to delete");
        prompt.showAndWait();
    }

    public static boolean warning_home_screen(ActionEvent e) {
        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION);
        prompt.setHeaderText("Going Back to the Main Menu.");
        prompt.setContentText("Proceed?");
        Optional<ButtonType> userChoice = prompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static boolean warning_confirmation(ActionEvent e) {
        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION);
        prompt.setHeaderText("Exiting Program.");
        prompt.setContentText("Are you sure?");
        Optional<ButtonType> userChoice = prompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    static boolean warning_delete_item(ActionEvent e) {
        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION);
        prompt.setHeaderText("This item will be deleted in the list.");
        prompt.setContentText("Continue?");
        Optional<ButtonType> userChoice = prompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static boolean warning_logout(ActionEvent e) {
        Alert prompt = new Alert(Alert.AlertType.CONFIRMATION);
        prompt.setHeaderText("Logging Out of Current User.");
        prompt.setContentText("Are you sure?");
        Optional<ButtonType> userChoice = prompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static boolean isPicture(File file) throws IOException {
        /* returns true if file is a picture or not */
        return (ImageIO.read(file) != null);
    }
}
