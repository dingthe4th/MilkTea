package project.controllers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

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
    private final static String default_item_path = "../resources/assets/default.png";
    public String item_name;
    public String item_type;
    public double item_price;
    public String item_path;
    public ImageView item_image;

    Item(String a, String b, double c, String d) {
        if(d == null) d = default_item_path;
        this.item_name = a;
        this.item_type = b;
        this.item_price = c;
        this.item_path = d;
        this.item_image = new ImageView(new Image(item_path));
    }

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
