package project.Classes;

public enum DrinkSize {
    LARGE("L"),
    MEDIUM("M");

    private String str;
    DrinkSize(String size){
        str = size;
    }


    @Override
    public String toString() {
        return str;
    }
}
