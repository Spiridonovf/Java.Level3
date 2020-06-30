package Lesson1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class hw1_2 {
    private int size=5;
    private Object[] obj = new Object[size];
    private List<Object> list = new ArrayList<Object>();

    public void replace(int i, int j){
        Object el = obj[i];
        obj[i] = obj[j];
        obj[j] = el;
    }
    public void printArr() {
        System.out.println("====start_arr===");
        for (int i=0;i<obj.length;i++){
            System.out.println(obj[i]);
        }
        System.out.println("====end_arr======");
    }
    public void printList() {
        System.out.println("====start_list===");
        for (Object o : list)
            System.out.println(" " + o.toString());
        System.out.println("====end_list======");
    }
    public void copyArrToList() {
        list = Arrays.asList(obj);
    }
    public static void main(String[] args) {
        hw1_2 c = new hw1_2();
        for (int i=0;i<c.obj.length;i++){
            c.obj[i] = Math.random() * 100;
        }
        System.out.println("====1======");
        c.printArr();
        c.replace(1,2);
        c.printArr();
        System.out.println("====2======");
        c.copyArrToList();
        c.printList();
    }
}
