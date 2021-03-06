package project.controllers;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import javax.print.DocFlavor;
import java.io.*;


public class Item {
    /*
     * @param
     * default_item_path = default path of item
     * image in case declared as null
     * item_name = name of item
     * item_type = type of item
     * item_price = price of item
     * item_path = path of image of item
     * item_image = image of the item
     * */
    public final static String default_item_path = "/project/image/default.png";
    private static final double image_size = 100;
    public String item_name;
    public String item_type;
    public double item_price;
    public String item_path;
    public ImageView item_image = new ImageView();
    public HBox item_display = new HBox(30);

    // Constructor
    Item(String a, String b, double c, String d) throws IOException {
        double img_size = image_size;
        if(b.equalsIgnoreCase("add on")) {
            img_size /= 2;
        }
        if(d.equals("default")) d = default_item_path;


        InputStream is = getClass().getResourceAsStream(d);
        if(is == null){
            is = new FileInputStream(d);
        }

//        InputStreamReader isr = new InputStreamReader(is);
//        BufferedReader bfr = new BufferedReader(isr);
        Image image = new Image(is);
        this.item_name = a;
        this.item_type = b;
        this.item_price = c;
        this.item_path = d;
        this.item_image.setImage(image);
        this.item_image.setFitWidth(img_size);
        this.item_image.setFitHeight(img_size);
        create_item_display();
    }

    // creates an HBox to be displayed to the G.U.I.
    private void create_item_display() {
        if(!item_type.equalsIgnoreCase("add on")) {
            item_display.setMaxSize(750,200);
            Label a = new Label(item_name);
            Label b = new Label(Double.toString(item_price));
            a.setPrefWidth(180);
            b.setPrefWidth(180);
            VBox vBox = new VBox(20);
            vBox.getChildren().addAll(a,b);
            vBox.setPrefWidth(180);
            item_display.getChildren().addAll(item_image,vBox);
        }
        else {
            item_display.setMaxSize(750,200);
            Label a = new Label(item_name);
            Label b = new Label(Double.toString(item_price));
            a.setPrefWidth(100);
            b.setPrefWidth(100);
            VBox vBox = new VBox(10);
            vBox.getChildren().addAll(a,b);
            vBox.setPrefWidth(100);
            item_display.getChildren().addAll(item_image,vBox);
            item_display.setSpacing(10);
        }
    }

    // edits the display for add on items for EditModeScreen
    public void set_addon_displays() {
        if(!item_type.equalsIgnoreCase("add on")) return;
        item_display.getChildren().clear();
        item_image.setFitHeight(100);
        item_image.setFitWidth(100);
        item_display.setMaxSize(750,200);
        Label a = new Label(item_name);
        Label b = new Label(Double.toString(item_price));
        a.setPrefWidth(180);
        b.setPrefWidth(180);
        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(a,b);
        vBox.setPrefWidth(180);
        item_display.getChildren().addAll(item_image,vBox);
        item_display.setSpacing(30);
    }

    public HBox getItem_display() { return item_display; }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_type() {
        return item_type;
    }

    public void setItem_type(String item_type) {
        this.item_type = item_type;
    }

    public double getItem_price() {
        return item_price;
    }

    public void setItem_price(double item_price) {
        this.item_price = item_price;
    }


    public String getItem_path() {
        return item_path;
    }

    public void setItem_path(String item_path) {
        this.item_path = item_path;
        this.item_image.setImage(new Image(item_path));
    }

    public Image getItem_image() {
        return item_image.getImage();
    }
}
