package Lesson4;


import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Client implements Runnable {
    private Socket socket = null;
    private DataInputStream in;
    private DataOutputStream out;
    private String nick;
    private String login;
    private String password;
    private String serverName;
    private int port;

    private boolean connected = false;
    private boolean authok = false;
    private boolean stopped = false;

    private ClientMessageHistory clientMessageHistory;


    public Client(String _serverName, int _port) {
        try {
            this.serverName = _serverName;
            this.port = _port;
            this.socket = new Socket(_serverName, _port);
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.connected  = true;
            PrintMessage("соединение установлено");
        } catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    public void sendMessage(String message) {
        if (message.isEmpty()) {
            return;
        }
        try {
            this.out.writeUTF(message);
            this.out.flush();

//            if (nick != null){
//            clientMessageHistory.PushMessage(message,nick,Calendar.getInstance().getTime());
//            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void login(String _login,String _password) {
        this.login = _login;
        this.password = _password;
        sendMessage("/auth " + this.login + " " + this.password);
    }

    public void changeNickName(String newNickName) {

        sendMessage("/changenickname " + this.login + " " + this.password+ " " + newNickName);
    }

    public void logout() {
        this.stopped = true;
        PrintMessage("Вы " + nick + " вышли из чата");
        sendMessage("/end");
        SaveHistory();

    }

    public void PrintMessage(String str){
        System.out.println( new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+" "+str);
    }

    public void LoadHistory()
    {
        try{
            clientMessageHistory = new ClientMessageHistory(10,"history_hw4_"+login+".txt");
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void SaveHistory()
    {
        try{
            clientMessageHistory.SaveHistory();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void printCurrentMessageHistory()
    {
        this.clientMessageHistory.PrintCurrentHistory();
    }
    public void censorshipMessage(){

    }
    @Override
    public void run() {
        try {
            while ((socket.isConnected())&(!socket.isClosed()) &(!this.stopped) ){
                if (in.available()>0) {
                    String msg = in.readUTF();
                    if (nick != null) {
                        if (msg.startsWith("/")) {
                            if (msg.equalsIgnoreCase("/end")) {
                                PrintMessage("сервер завершил работу");
                            }
                            if (msg.startsWith("/changenickname ")){
                                String[] tokens = msg.split("\\s");
                                String newnickname = tokens[1];
                                String resultmsg = tokens[2];
                                this.nick = newnickname;
                                PrintMessage("никнейм успешно изменен на: "+newnickname);
                            }
                            if (msg.endsWith("зашел в чат")){
                                PrintMessage(msg);
                            }
                        } else {

                            if (!msg.isEmpty()) {
                                //msg.
                                //PrintMessage(msg);
                                String[] tokens = msg.split(":");
                                clientMessageHistory.PushMessage(tokens[1],tokens[0],Calendar.getInstance().getTime());
                                PrintMessage(msg);
                            }
                        }
                    } else {
                        if (msg.startsWith("/authok ")) {
                            String[] elements = msg.split(" ");
                            nick = elements[1];
                            authok = true;
                            PrintMessage("Вы авторизованы под ником: " + nick);
                            LoadHistory();
                            PrintMessage("Последние сообщения из истории чата");
                            printCurrentMessageHistory();
                        } else if (msg.startsWith("/authfail")) {
                            PrintMessage("Ошибка авторизации");
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            try {
                socket.close();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            e.printStackTrace();
        }


    }
}




