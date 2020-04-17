package project.services;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.IOException;
import java.net.URL;

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

    public static void openMainScreen() throws IOException{

        URL resource = new File("src/project/fxml/MainScreen.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(resource);
        Scene scene = new Scene(fxmlLoader.load());
        Stage primaryStage = new Stage();
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(750);
        primaryStage.setTitle("Taste from the Greens");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    static void openConfirmOrderWindow() throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Initialize.class.getResource("\\project\\fxml\\ConfirmOrderWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
    }

    public static void openAddToCartWindow() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader();
        URL resource = new File("src/project/fxml/AddToCartWindow.fxml").toURI().toURL() ;
        fxmlLoader.setLocation(resource);
        Scene scene = new Scene(fxmlLoader.load());
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("New Window");
        stage.setScene(scene);
        stage.show();
    }

    static void openLogin(Stage primaryStage, Main main) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("\\project\\fxml\\LoginScreen.fxml"));
        URL resource = new File("src/project/fxml/LoginScreen.fxml").toURI().toURL();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(resource);
        Scene scene = new Scene(fxmlLoader.load());
        primaryStage.setTitle("Taste from the Greens");
        primaryStage.setScene(scene);
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();

    }

}
