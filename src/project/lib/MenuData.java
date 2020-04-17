package project.lib;

import project.Classes.DrinkBase;
import project.Classes.DrinkItem;

import java.sql.*;
import java.util.ArrayList;

public class MenuData {

    static Connection c;
    static ArrayList<DrinkBase> BaseList = new ArrayList<>();
    static ArrayList<DrinkItem> DrinkList = new ArrayList<>();

    // connects application to menuDb
    public static void connnectDb(){

        c = null;
        try {
            String url = "jdbc:sqlite:src/project/lib/MenuData.db";
            c = DriverManager.getConnection(url);
        }
        catch(Exception e){
            System.out.println(e);
        }
    }

    static void loadDrinkBase(){
        connnectDb();
         BaseList = new ArrayList<>();
        try{
            String query = "SELECT*FROM Base";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                /*
                    While there is still an item in the drink base
                    table, iterate

                 */
                System.out.println(rs.getString("BaseName"));
                DrinkBase base = new DrinkBase(rs.getInt("id"),rs.getString("BaseName"));
                BaseList.add(base);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }

   static void loadDrinks(){
        connnectDb();
        DrinkList = new ArrayList<>();
        try{
            String query = "SELECT*FROM Menu_Items";
            Statement st = c.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()){
                /*
                    While there is still an item in the drink base
                    table, iterate
                 */
                DrinkItem drink  = new DrinkItem(rs.getInt("Id"),rs.getString("Tea_Name"),rs.getString("Drink_Base"),rs.getString("Image_Path"),rs.getFloat("Cost"));
                DrinkList.add(drink);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
    }


    public static void loadMenu(){
        loadDrinkBase();
        loadDrinks();
    }

    public static ArrayList<DrinkBase> getBaseList(){
        return BaseList;
    }

    public static ArrayList<DrinkItem> getDrinkList(){
        return DrinkList;
    }


}
