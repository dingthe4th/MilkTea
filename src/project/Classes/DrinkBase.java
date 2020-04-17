package project.Classes;

import java.util.HashSet;
import java.util.Hashtable;

/*
    This is a class for the drink base such as red milk tea, smoothies, fresh green tea etc

    Remark: wait im too high
    Todo: design class
 */


public class DrinkBase {
    String name;
    public int id;
    static Hashtable<String,DrinkBase> baseList = new Hashtable<>();
    /*
    baseList lists all of the bases that has been created so far
     */
    public DrinkBase(int id,String name) throws IllegalArgumentException{
       this.name = name;
       this.id = id;
       baseList.put(name,this);

    }

    public static DrinkBase getDrinkBase(String key){
        return baseList.get(key);
    }

    @Override
    public String toString(){
        return this.name;
    }

    public String toId(){
        return Integer.toString(this.id);
    }


}
