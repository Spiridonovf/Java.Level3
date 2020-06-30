package Lesson4;

public class hw1 {
    private final Object mon = new Object();
    private volatile char currentLetter = 'A';

    private int count=5;

    public static void main(String[] args) {
        hw1 w = new hw1();
        Thread t1 = new Thread(() -> {
            w.printLetter('A');
        });
        Thread t2 = new Thread(() -> {
            w.printLetter('B');
        });
        Thread t3 = new Thread(() -> {
            w.printLetter('C');
        });
        t1.start();
        t2.start();
        t3.start();
    }

    public void printLetter(char letter) {
        synchronized (mon) {
            try {
                for (int i = 0; i < count; i++) {
                    while (currentLetter != letter) {
                        mon.wait();
                    }
                    System.out.print(letter);

                    changeLetter();
                    mon.notifyAll();
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
    public void changeLetter()
    {

        switch (currentLetter) {
            case  ('A'):
                currentLetter='B';
                break;
            case ('B'):
                currentLetter='C';
                break;
            case ('C'):
                currentLetter='A';
                break;
        }
    }

}
