package project.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import project.Classes.DrinkItem;
import project.services.OrderHandler;

public class AddToCartWindowController {

    @FXML
    Label DrinkName;

    @FXML
    ImageView DrinkImage;

    @FXML
    ToggleGroup SizeGroup;

    @FXML
    ToggleGroup SweetnessGroup;

    @FXML
    ToggleGroup IceLevelGroup;


    public void initialize(){
        DrinkItem drinkItem = OrderHandler.getOrderedDrink();
        DrinkName.setText(drinkItem.name);
    }


}
