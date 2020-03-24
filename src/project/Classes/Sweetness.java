package project.Classes;

public enum Sweetness {
    QUARTER(25),
    HALF(50),
    LESS(75),
    FULL(100);

    private int val;
    Sweetness(int val){
        this.val = val;
    }
    public int toPercent(){
        return this.val;
    }

    @Override
    public String toString(){
        return val+"%";
    }

}
