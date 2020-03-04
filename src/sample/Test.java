package sample;

public class Test {
    public static void main(String[] args) {
        final int[] sample = {1, 3, 2, 4, 5, 6};
        int[] a = Test.getIndex(sample,4);
        for(int i : a) System.out.println(i);
    }

    public static int[] getIndex(int[] list, int target) {
        int[] index = new int[2];
        for(int i=0; i<list.length; i++) {
            for(int j=0; j<list.length; j++) {
                if(i!=j) {
                    int sum = list[i] + list[j];
                    if(sum == target) {
                        index[0] = i;
                        index[1] = j;
                        break;
                    }
                }
            }
        }
        return index;
    }
}
