package Lesson4;


public class ClientTest {

    public static void delay(int ms)
    {
        try {
            Thread.sleep(ms);
            // any action
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)  {

        //с целью удобства тестирования экземпляров ввод сообщений осуществляется не из одной консоли, а из кода
        //Client client1 = new Client("localhost", 8189);

        //авторизуем и запускаем

        Client client2 = new Client("localhost", 8189);
        Thread x2 = new Thread(client2);
        x2.start();
        delay(2000);
        client2.login("login2", "pass2");
        delay(2000);
        client2.sendMessage("001");
        delay(2000);
        client2.logout();
        delay(2000);
        client2.PrintMessage("тестирование завершено");

    }
}
