package sample;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    /*
    * @param
    * adminUsername is the username of admin
    * adminPassword is the password of admin
    * statusMsg is a status message for the current screen
     */
    private static final String adminUsername = "username";
    private static final String adminPassWord = "password";
    public TextField usernameField;
    public PasswordField passwordField;
    public Label statusMsg;
    public ImageView programLogo;
    public VBox loginPrompt;

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
    public void adminLogin(ActionEvent e) {
        // login successfully!
        String id = usernameField.getText();
        String pw = passwordField.getText();

        if(tryLogin(id,pw)) {
            statusMsg.setText("Login successful!");
        }
        // login error
        else {
            statusMsg.setText("Incorrect username or password.");
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        GraphicsDevice gd = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice();
        int width = gd.getDisplayMode().getWidth();
        int height = gd.getDisplayMode().getHeight();
//        loginPrompt.setLayoutX((double) width/2 - loginPrompt.getWidth());
//        programLogo.setLayoutX((double) width/2 - programLogo.getFitWidth());
    }
}
