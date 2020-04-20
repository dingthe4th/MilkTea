package project.controllers;

import com.opencsv.CSVWriter;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import java.util.Scanner;

public class StatisticsController implements Initializable {

    private static StatisticsController instanceOf;

    public AnchorPane statsPane;
    public Label dateOfReportLabel;
    public Label totalAmountSoldLabel;
    public Label smallCupsQtyLabel;
    public Label mediumCupsQtyLabel;
    public Label largeCupsQtyLabel;
    public Label totalItemsSoldLabel;
    public Label totalAddOnSoldLabel;
    public Label statusMessage;

    // Table view for TEA TYPE statistics
    public TableView<ItemSold.PerType> teaTypeTableView;
    private ObservableList<ItemSold.PerType> teaTypeObservableList;
    public TableColumn<ItemSold.PerType,String> teaTypeColumn;
    public TableColumn<ItemSold.PerType, Integer> teaTypeQuantityColumn;

    // Table view for TEA NAME statistics
    public TableView<ItemSold> teaNameTableView;
    private ObservableList<ItemSold> teaNameObservableList;
    public TableColumn<ItemSold, String> teaNameColumn;
    public TableColumn<ItemSold, Integer> teaNameQuantityColumn;

    // Necessary information containers
    private HashMap<Integer, Integer> cupsOrderedHashMap = new HashMap<>();
    private HashMap<Item,Integer> main_ItemOrderedHashMap = new HashMap<>();
    private double totalSalesAmount;

    //information for generating and exporting report
    private int itemSold;
    private int addOnSold;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        instanceOf = this;
        // Initialize teaTypeTableView
        teaTypeObservableList = FXCollections.observableArrayList();
        teaTypeTableView.setItems(teaTypeObservableList);
        teaTypeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        teaTypeQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("qty"));

        // Initialize teaNameTableView
        teaNameObservableList = FXCollections.observableArrayList();
        teaNameTableView.setItems(teaNameObservableList);
        teaNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        teaNameQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    }

    // This method close the current window
    public void backToHomeScreen(ActionEvent e) {
        Stage stage = (Stage) teaTypeTableView.getScene().getWindow();
        stage.close();
    }

    // This method is used to get information from HomeScreen
    public static void catchInformation(HashMap<Integer, Integer> cupsOrderedHashMap, HashMap<Item,Integer> itemOrderedHashMap, double totalSalesAmount) {
        instanceOf.cupsOrderedHashMap = cupsOrderedHashMap;
        instanceOf.main_ItemOrderedHashMap = itemOrderedHashMap;
        instanceOf.totalSalesAmount = totalSalesAmount;

        instanceOf.populateTeaNameTableView();
        instanceOf.populateTeaTypeTableView();
        instanceOf.displayOverallStatistics();
    }

    // This method is used to populate teaNameTableView
    private void populateTeaNameTableView() {
        for(Item item : main_ItemOrderedHashMap.keySet()) {
            if(!item.getItem_type().equalsIgnoreCase("add on")) {
                ItemSold itemSold = new ItemSold(item.getItem_name(), item.getItem_type(), 0, main_ItemOrderedHashMap.get(item));
                teaNameObservableList.add(itemSold);
            }
        }
    }

    // This method is used to populate teaTypeTableView
    private void populateTeaTypeTableView() {
        HashMap<String, Integer> temp = new HashMap<>();
        for(Item item : main_ItemOrderedHashMap.keySet()) {
            if(temp.containsKey(item.getItem_type())) {
                temp.replace(item.getItem_type(),temp.get(item.getItem_type())+main_ItemOrderedHashMap.get(item));
            } else {
                temp.put(item.getItem_type(),main_ItemOrderedHashMap.get(item));
            }
        }

        for(String itemType : temp.keySet()) {
            ItemSold.PerType itemSoldPerType = new ItemSold.PerType(itemType,temp.get(itemType));
            teaTypeObservableList.add(itemSoldPerType);
        }
    }

    public void exportStatistic(){
        Stage chooser = new Stage();
        chooser.setTitle("Choose the directory to save your export");

        // Choose directory
        DirectoryChooser directoryChooser = new DirectoryChooser();


        directoryChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        File dir =  directoryChooser.showDialog(chooser);

        //create file
        File report = new File(dir.getAbsolutePath() + "/Report" +  java.time.LocalDate.now() + ".csv");
        try{
            FileWriter filewriter = new FileWriter(report);
            CSVWriter csvWriter = new CSVWriter(filewriter);
            //to space out tables
            String [] newLineRow= {""};
            // date
            String[] row1 = {"Date ", java.time.LocalDate.now()+""};
            csvWriter.writeNext(row1);
            //Total amount
            String[] row2= {"Total Amount", totalSalesAmount+""};
            csvWriter.writeNext(row2);
            //total cups sold

            int s=0,l=0,m=0;
            for(Integer cupSize : cupsOrderedHashMap.keySet()) {
                if(cupSize.equals(1)) {
                   s=cupsOrderedHashMap.get(cupSize);
                } else if (cupSize.equals(2)) {
                    m=cupsOrderedHashMap.get(cupSize);
                } else if (cupSize.equals(3)) {
                    l=cupsOrderedHashMap.get(cupSize);
                }
            }
            String[] row3 = {"Total Cups Sold",s+" s",m+" m", l+" l"};
            csvWriter.writeNext(row3);

            //total items sold
            String[] row4 = {"Total items sold", itemSold+""};
            csvWriter.writeNext(row4);

            //total add ons sold
            String[] row5 = {"Total add ons sold", addOnSold+""};
            csvWriter.writeNext(row5);

            csvWriter.writeNext(newLineRow);

            // tea type table
            String [] teaTypeHeader = {"Tea Category", "Quantity"};
            csvWriter.writeNext(teaTypeHeader);


            // add the tea type table to the file
            for(ItemSold.PerType perType: teaTypeObservableList) {

                String rowN[]={perType.type+"",perType.qty+""};
                csvWriter.writeNext(rowN);
            }
            csvWriter.writeNext(newLineRow);

            //adds the tea name table to the file
            String[] teaNameHeader = {"Tea Name","Quantity"};
            csvWriter.writeNext(teaNameHeader);
            for(ItemSold item: teaNameObservableList) {
                String rowN[]={item.name+"",item.quantity+""};
                csvWriter.writeNext(rowN);
            }
            csvWriter.close();
        }
        catch(Exception e){
            System.out.println(e);
        }

        /*
            status message saying that file has been generated
         */
        statusMessage.setText("File Report" +  java.time.LocalDate.now() + ".csv");




    }


    // This method is used to display the overall sale statistics
    private void displayOverallStatistics() {
        // Displaying total sales amount
        totalAmountSoldLabel.setText(Double.toString(totalSalesAmount));

        // Displaying date
        dateOfReportLabel.setText(String.valueOf(java.time.LocalDate.now()));

        // Displaying cups sold statistics
        for(Integer cupSize : cupsOrderedHashMap.keySet()) {
            if(cupSize.equals(1)) {
                smallCupsQtyLabel.setText(Integer.toString(cupsOrderedHashMap.get(cupSize)));
            } else if (cupSize.equals(2)) {
                mediumCupsQtyLabel.setText(Integer.toString(cupsOrderedHashMap.get(cupSize)));
            } else if (cupSize.equals(3)) {
                largeCupsQtyLabel.setText(Integer.toString(cupsOrderedHashMap.get(cupSize)));
            }
        }

        // Displaying total items sold
        itemSold = 0;
        addOnSold = 0;
        for(Item item : main_ItemOrderedHashMap.keySet()) {
            itemSold += main_ItemOrderedHashMap.get(item);
            if(item.getItem_type().equalsIgnoreCase("add on"))
                addOnSold += main_ItemOrderedHashMap.get(item);
        }
        totalItemsSoldLabel.setText(Integer.toString(itemSold));
        totalAddOnSoldLabel.setText(Integer.toString(addOnSold));
    }
}
