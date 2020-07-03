package Lesson3;


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
        client2.sendMessage("002");
        delay(2000);
        client2.sendMessage("003");
        delay(2000);
        client2.sendMessage("004");
        delay(2000);
        client2.sendMessage("005");
        delay(2000);
        client2.sendMessage("006");
        delay(2000);
        client2.sendMessage("007");
        delay(2000);
        client2.sendMessage("008");
        delay(2000);
        client2.sendMessage("009");
        delay(2000);
        client2.sendMessage("010");
        delay(2000);
        client2.sendMessage("011");
        delay(2000);
        client2.sendMessage("012");
        delay(2000);
        client2.printCurrentMessageHistory();
        delay(2000);
        client2.logout();
        delay(2000);
        client2.PrintMessage("тестирование завершено");

    }
}
