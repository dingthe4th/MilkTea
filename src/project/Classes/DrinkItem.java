package project.Classes;

/*
    Class Author: Isaiah Tupal
    This is a  class for a generic drink

    todo: constructors
 */

public class DrinkItem {
    public String name;
    public DrinkBase base;
    public Sweetness sweetness;
    public String imgPath = "src/project/image/default.png";
    public int id;
    float cost;
    public DrinkSize size = DrinkSize.MEDIUM;

    public DrinkItem(int id, String name, String drinkBase, String imgpath, float cost ){
        this.id = id;
        this.name = name;
        this.base = DrinkBase.getDrinkBase(drinkBase);
        this.imgPath = imgpath;
        this.cost = cost;
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
