package Lesson5;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Road extends Stage {
    public Road(int length) {
        this.length = length;
        this.description = "Дорога " + length + " метров";
    }
    @Override
    public void go(Car c) {
        try {
            PrintMessage(c.getName() + " начал этап: " + description);
            Thread.sleep(length / c.getSpeed() * 1000);
            PrintMessage(c.getName() + " закончил этап: " + description);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void PrintMessage(String str){
        System.out.println( new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:ms").format(Calendar.getInstance().getTime())+" "+str);
    }
}