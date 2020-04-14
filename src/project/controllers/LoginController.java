package project.controllers;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    /*
    * @param
    * adminUsername = username of admin
    * adminPassword = password of admin
    * usernameField = username text field
    * passwordField = password text field
    * statusMsg is a status message for the current screen
    * programLogo = logo of this project
     */
    private static final String adminUsername = "username";
    private static final String adminPassWord = "password";
    public AnchorPane loginPane;
    public JFXTextField usernameField;
    public JFXPasswordField passwordField;
    public Label statusMsg;

    public void initialize(URL location, ResourceBundle resources)    {
        Font.loadFont(getClass().getResourceAsStream("../text/css/Montserrat-Bold.ttf"), 14);
        Font.loadFont(getClass().getResourceAsStream("../text/css/Montserrat-Regular.ttf"), 14);
        loginPane.getStylesheets().add(getClass().getResource("../text/css/jfxStyle_0.css").toExternalForm());
    }

    // helper function for adminLogin(AE e) //
    private boolean tryLogin(String id, String pw) {
        /*
        * @param
        * flag1 - checks if id is equal to admin username
        * flag2 - checks if pw is equal to admin password
        * login successfully if flag1 && flag2
         */
        boolean flag1 = id.equals(adminUsername);
        boolean flag2 = pw.equals(adminPassWord);
        return flag1 && flag2;
    }

    // checks if user has valid login credentials //
    public void adminLogin(ActionEvent e) throws IOException {

        String id = usernameField.getText();
        String pw = passwordField.getText();

        // login successfully!
        if(tryLogin(id,pw)) {
            statusMsg.setText("L O G I N   S U C C E S S F U L !");
            goToHomeScreen();
        }
        // login error
        else {
            statusMsg.setText("I N C O R R E C T   U S E R N A M E   O R   P A S S W O R D");
        }
    }

    // if login is successful, goes to the next screen (H0ME SCREEN)
    public void goToHomeScreen() throws IOException {
        // loads new fxml file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml/HomeScreen.fxml"));
        Parent root = loader.load();

        // pass login controller to home screen controller
        HomeScreenController homeScreenController= loader.getController();

        // fxmlloader -> parent -> controller -> scene -> stage

        Scene scene = new Scene(root);
        Stage stage = (Stage) loginPane.getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void window_exit(ActionEvent e) {
        // TODO This is function 5 stated above
        boolean confirm = ErrorPrompts.warning_confirmation(new ActionEvent());
        if(confirm) System.exit(0);
    }

}
