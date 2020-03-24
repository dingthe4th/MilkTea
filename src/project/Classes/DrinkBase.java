package project.Classes;

import java.util.HashSet;

/*
    This is a class for the drink base such as red milk tea, smoothies, fresh green tea etc

    Remark: wait im too high
    Todo: design class
 */


public class DrinkBase {
    String name;
    static HashSet<DrinkBase> baseList = new HashSet<>();
    /*
    baseLists all of the bases that has been created so far
     */
    public DrinkBase(String name) throws IllegalArgumentException{
        if(!baseList.contains(name)){
            this.name=name;
            baseList.add(this);
        }
        else
            throw new IllegalArgumentException("Base has been created, enter new drink base");
    }

    @Override
    public String toString() {
        return name;
    }

    public static boolean isDrinkBase(String base){
        return baseList.contains(base);
    }


}
