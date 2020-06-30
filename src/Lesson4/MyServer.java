package Lesson4;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MyServer {
    private final int PORT = 8189;
    private final int poolSize=5;
    private List<ClientHandler> clients;
    private AuthService authService;
    private ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
    private ServerSocket server;

    public AuthService getAuthService() {
        return authService;
    }

    public MyServer() {
        try {
            this.server = new ServerSocket(PORT);
            authService = new BaseAuthService();
            authService.start();
            clients = new ArrayList<>();
            while (true) {
                PrintMessage("Сервер ожидает подключения");
                Socket socket = server.accept();
                executorService.submit(new ClientHandler(this, socket));
                PrintMessage("Клиент подключился");
            }
        } catch (Exception e)
        {
            PrintMessage("Ошибка в работе сервера");
        }
        finally {
            if (authService != null) {
                for (ClientHandler clientHandler:clients){
                    clientHandler.sendMsg("/end");
                }
                authService.stop();
                stopServer();
            }
        }
    }
    private void stopServer() {
        executorService.shutdownNow();
        try {
            server.close();
        } catch (IOException e) {
            System.out.println("Error in server shutdown");
            e.printStackTrace();
        }
        System.exit(0);
    }

    public void PrintMessage(String msg){
        System.out.println( new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+" "+msg);
    }

    public synchronized boolean isNickBusy(String nick) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) {
                return true;
            }
        }
        return false;
    }

    public synchronized void broadcastMsg(String msg) {
        for (ClientHandler o : clients) {
            o.sendMsg(msg);
        }
    }

    public synchronized void unsubscribe(ClientHandler o) {
        clients.remove(o);
    }

    public synchronized void subscribe(ClientHandler o) {
        clients.add(o);
    }

    public void sendMsgToClient(ClientHandler clientHandler, String nick, String msg) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) {
                o.sendMsg("личное сообщение от "+clientHandler.getName()+ ":"+msg);
            }
        }
    }
    public void sendServiceMsgToClient(ClientHandler clientHandler, String serviceid,String nick, String msg) {
        for (ClientHandler o : clients) {
            if (o.getName().equals(nick)) {
                o.sendMsg(serviceid+" "+clientHandler.getName()+ " "+msg);
            }
        }
    }
}





