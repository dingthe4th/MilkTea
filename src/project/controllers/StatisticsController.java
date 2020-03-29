package project.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;

public class StatisticsController implements Initializable {
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
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
    public void catchInformation(HashMap<Integer, Integer> cupsOrderedHashMap, HashMap<Item,Integer> itemOrderedHashMap, double totalSalesAmount) {
        this.cupsOrderedHashMap = cupsOrderedHashMap;
        this.main_ItemOrderedHashMap = itemOrderedHashMap;
        this.totalSalesAmount = totalSalesAmount;

        populateTeaNameTableView();
        populateTeaTypeTableView();
        displayOverallStatistics();
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
        int itemSold = 0;
        int addOnSold = 0;
        for(Item item : main_ItemOrderedHashMap.keySet()) {
            itemSold += main_ItemOrderedHashMap.get(item);
            if(item.getItem_type().equalsIgnoreCase("add on"))
                addOnSold += main_ItemOrderedHashMap.get(item);
        }
        totalItemsSoldLabel.setText(Integer.toString(itemSold));
        totalAddOnSoldLabel.setText(Integer.toString(addOnSold));
    }
}
