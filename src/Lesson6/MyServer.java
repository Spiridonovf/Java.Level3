package Lesson6;


import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;

public class MyServer {
    private final int PORT = 8189;
    private final int poolSize=5;
    private List<ClientHandler> clients;
    private AuthService authService;
    private ExecutorService executorService = Executors.newFixedThreadPool(poolSize);
    private ServerSocket server;
    private static final Logger logger = Logger.getLogger("ChatServerLogFile");

    public AuthService getAuthService() {
        return authService;
    }

    public MyServer() {
        try {
            //инициализация логирования
            FileHandler fh;
            fh = new FileHandler("ChatServer.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            this.server = new ServerSocket(PORT);
            authService = new BaseAuthService(this);
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
            ErrorMessage("Ошибка в работе сервера",e);
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
            ErrorMessage("Error in server shutdown",e);
        }
        System.exit(0);
    }

    public void PrintMessage(String msg){
        logger.log(Level.INFO, msg);
    }
    public void ErrorMessage(String msg, Exception e){
        //System.out.println(msg);
        //e.printStackTrace();
        logger.log(Level.SEVERE, msg, e);
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





