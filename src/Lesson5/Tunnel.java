package Lesson5;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.Semaphore;

public class Tunnel extends Stage {

    private Semaphore smp;


    public Tunnel(int carCount) {
        this.length = 80;
        this.description = "Тоннель " + length + " метров";
        this.smp =  new Semaphore(carCount/2);
    }
    @Override
    public void go(Car c) {
        try {
            try {
                PrintMessage(c.getName() + " готовится к этапу(ждет): " + description);
                smp.acquire();
                PrintMessage(c.getName() + " начал этап: " + description);
                Thread.sleep(length / c.getSpeed() * 1000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                PrintMessage(c.getName() + " закончил этап: " + description);
                smp.release();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void PrintMessage(String str){
        System.out.println( new SimpleDateFormat("dd.MM.yyyy HH:mm:ss:ms").format(Calendar.getInstance().getTime())+" "+str);
    }
}
