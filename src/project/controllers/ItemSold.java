package project.controllers;

public class ItemSold {
    public static class PerType {
        String type;
        int qty;

        PerType(String type, int qty) {
            this.type = type;
            this.qty = qty;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public int getQty() {
            return qty;
        }

        public void setQty(int qty) {
            this.qty = qty;
        }
    }

    String name;
    String type;
    int cup;
    int quantity;

    ItemSold(String name, String type, int cup, int qty) {
        this.name = name;
        this.type = type;
        this.cup = cup;
        this.quantity = qty;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getCup() {
        return cup;
    }

    public void setCup(int cup) {
        this.cup = cup;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }






}
