package project.controllers;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
    public final static String default_item_path = "src/project/image/default.png";
    private static final double image_size = 100;
    public String item_name;
    public String item_type;
    public double item_price;
    public String item_path;
    public ImageView item_image = new ImageView();
    public HBox item_display = new HBox(30);

    // constructor
    Item(String a, String b, double c, String d) throws IOException {
        double img_size = image_size;
        if(b.equalsIgnoreCase("add on")) {
            img_size /= 2;
        }
        if(d.equals("default")) d = default_item_path;
        Image image = new Image(new FileInputStream(d));

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
        item_display.setMaxSize(200,200);
        Label a = new Label(item_name);
        Label b = new Label(Double.toString(item_price));
        VBox vBox = new VBox(20);
        vBox.getChildren().addAll(a,b);
        item_display.getChildren().addAll(item_image,vBox);
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
