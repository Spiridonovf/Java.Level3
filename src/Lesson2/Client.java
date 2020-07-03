package Lesson2;

import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;

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

    }

    public void PrintMessage(String str){
        System.out.println( new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(Calendar.getInstance().getTime())+" "+str);
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
                        } else {
                            if (!msg.isEmpty()) {
                                PrintMessage(msg);
                            }
                        }
                    } else {
                        if (msg.startsWith("/authok ")) {
                            String[] elements = msg.split(" ");
                            nick = elements[1];
                            authok = true;
                            PrintMessage("Вы авторизованы под ником: " + nick);

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




