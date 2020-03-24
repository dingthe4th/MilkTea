import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception{
        Initialize.loadFonts(this);
        Initialize.openMainScreen(primaryStage,this);


        /*
       Parent root = FXMLLoader.load(getClass().getResource("\\project\\fxml\\LoginScreen.fxml"));
        //Parent root = FXMLLoader.load(getClass().getResource("\\project\\fxml\\MainScreen.fxml"));
        primaryStage.setTitle("Taste from the Greens");
        primaryStage.setScene(new Scene(root));
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.show();*/


    }

    public static void main(String[] args) {
        launch(args);
    }
}
