/*
    This class contains static helper functions needed for initializing
    scenes/windows
 */

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/*
    Author: Isaiah Tupal
    Purpose of this class: This class contains static functions that loads windows and scenes
 */

public class Initialize {



    /*
    This is for loading the fonts
     */
    static void loadFonts(Main main){
        Font.loadFont(main.getClass().getResourceAsStream("/project/css/fonts/Lato-Regular.ttf"), 18);
        Font.loadFont(main.getClass().getResourceAsStream("/project/css/fonts/futur.ttf"), 16);
        Font.loadFont(main.getClass().getResourceAsStream("/project/css/fonts/Futura Bold font.ttf"), 16);
        Font.loadFont(main.getClass().getResourceAsStream("/project/css/fonts/Helvetica-Bold.ttf"), 16);
    }

    static void openMainScreen(Stage primaryStage, Main main) throws Exception{

        Parent root = FXMLLoader.load(main.getClass().getResource("\\project\\fxml\\MainScreen.fxml"));
        primaryStage.setMinWidth(700);
        primaryStage.setTitle("Taste from the Greens");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();

    }

    static void openAddToCartWindow() throws  Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Initialize.class.getResource("\\project\\fxml\\AddToCartWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
    }

}
