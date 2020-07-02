package Lesson5;

import Lesson4.ClientHandler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class hw5 {
    public static final int CARS_COUNT = 4;
    public static ExecutorService executorService = Executors.newFixedThreadPool(CARS_COUNT);
    public static CountDownLatch startlatch = new CountDownLatch(CARS_COUNT);
    public static CountDownLatch stoplatch = new CountDownLatch(CARS_COUNT);
    public static void main(String[] args) throws InterruptedException {
        PrintMessage("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Подготовка!!!");
        Race race = new Race(new Road(60), new Tunnel(CARS_COUNT), new Road(40));
        Car[] cars = new Car[CARS_COUNT];
        for (int i = 0; i < cars.length; i++) {
            executorService.submit((cars[i] = new Car(race, 20 + (int) (Math.random() * 10)
                    ,startlatch,stoplatch
            )));
        }
        executorService.shutdown();
        PrintMessage("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка началась!!!");
        try {
            stoplatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        PrintMessage("ВАЖНОЕ ОБЪЯВЛЕНИЕ >>> Гонка закончилась!!!");
    }
    public static void PrintMessage(String str){
        System.out.println( new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:ms").format(Calendar.getInstance().getTime())+" "+str);
    }
}
