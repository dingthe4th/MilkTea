package project.services;

import project.Classes.DrinkItem;
import project.lib.MenuData;

public class OrderHandler {
    static int orderNum;

    public static void  setOrderNum(int oNum){
        orderNum = oNum;
    }

    public static DrinkItem getOrderedDrink(){
        for(int i = 0 ; i <MenuData.getDrinkList().size() ;i++){
            if(MenuData.getDrinkList().get(i).id==orderNum)
                return MenuData.getDrinkList().get(i);
        }
         return null;
    }

}
