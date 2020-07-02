package Lesson5;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;

public class Car implements Runnable {
    private static int CARS_COUNT;
    private CountDownLatch startLatch;
    private CountDownLatch stopLatch;
    static {
        CARS_COUNT = 0;
    }
    private Race race;
    private int speed;
    private String name;
    public String getName() {
        return name;
    }
    public int getSpeed() {
        return speed;
    }
    public Car(Race race, int speed
            , CountDownLatch _startlatch, CountDownLatch _stoplatch
    ) {
        this.race = race;
        this.speed = speed;
        this.startLatch = _startlatch;
        this.stopLatch = _stoplatch;
        CARS_COUNT++;
        this.name = "Участник #" + CARS_COUNT;
        try {
            PrintMessage(this.name + " готовится");
            Thread.sleep(500 + (int)(Math.random() * 800));
            PrintMessage(this.name + " готов");
            startLatch.countDown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {

        try {
            startLatch.await();  // wait until latch counted down to 0
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        for (int i = 0; i < race.getStages().size(); i++) {
            race.getStages().get(i).go(this);
        }
        stopLatch.countDown();
        PrintMessage(this.name + " закончил гонку");
    }
    public void PrintMessage(String str){
        System.out.println( new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:ms").format(Calendar.getInstance().getTime())+" "+str);
    }
}