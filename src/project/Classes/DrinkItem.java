package project.Classes;

/*
    Class Author: Isaiah Tupal
    This is a  class for a generic drink

    todo: constructors
 */

public class DrinkItem {
    public String name;
    public DrinkBase base;
    public DrinkSize size;
    public Sweetness sweetness;
    public String imgPath;
    float cost;

    public DrinkItem(String name, String stringBase, String size, String imgath){
        this.name = name;
    }

    public  DrinkItem(){
        name="Milk Tea";
        imgPath="src/project/image/default.png";
        size = DrinkSize.LARGE;
        sweetness = Sweetness.FULL;
    }

    public float getCost(){
        return 69;
    }

}
