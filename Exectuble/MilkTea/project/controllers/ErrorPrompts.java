package project.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import javax.imageio.ImageIO;
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
        Alert exitPrompt = new Alert(Alert.AlertType.CONFIRMATION);
        exitPrompt.setHeaderText("Voiding Selected Transaction.");
        exitPrompt.setContentText("Proceed?");
        Optional<ButtonType> userChoice = exitPrompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static boolean order_confirmation(ActionEvent e) {
        Alert exitPrompt = new Alert(Alert.AlertType.CONFIRMATION);
        exitPrompt.setHeaderText("Confirming Current Order.");
        exitPrompt.setContentText("Are you sure of this order?");
        Optional<ButtonType> userChoice = exitPrompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static boolean order_void(ActionEvent e) {
        Alert exitPrompt = new Alert(Alert.AlertType.CONFIRMATION);
        exitPrompt.setHeaderText("Voiding Current Order.");
        exitPrompt.setContentText("Are you sure?");
        Optional<ButtonType> userChoice = exitPrompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static boolean warning_home_screen(ActionEvent e) {
        Alert exitPrompt = new Alert(Alert.AlertType.CONFIRMATION);
        exitPrompt.setHeaderText("Going Back to the Main Menu.");
        exitPrompt.setContentText("Proceed?");
        Optional<ButtonType> userChoice = exitPrompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static boolean warning_confirmation(ActionEvent e) {
        Alert exitPrompt = new Alert(Alert.AlertType.CONFIRMATION);
        exitPrompt.setHeaderText("Exiting Program.");
        exitPrompt.setContentText("Are you sure?");
        Optional<ButtonType> userChoice = exitPrompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    static boolean warning_delete_item(ActionEvent e) {
        Alert exitPrompt = new Alert(Alert.AlertType.CONFIRMATION);
        exitPrompt.setHeaderText("This item will be deleted in the list.");
        exitPrompt.setContentText("Continue?");
        Optional<ButtonType> userChoice = exitPrompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static boolean warning_logout(ActionEvent e) {
        Alert exitPrompt = new Alert(Alert.AlertType.CONFIRMATION);
        exitPrompt.setHeaderText("Logging Out of Current User.");
        exitPrompt.setContentText("Are you sure?");
        Optional<ButtonType> userChoice = exitPrompt.showAndWait();
        return userChoice.get() == (ButtonType.OK);
    }

    public static boolean isPicture(File file) throws IOException {
        /* returns true if file is a picture or not */
        return (ImageIO.read(file) != null);
    }
}
