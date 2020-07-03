package Lesson3;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

public class ClientHandler {
    private MyServer myServer;
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private int Timeout=5000;
    private boolean Authtimeout=false;

    private String name;

    public String getName() {
        return name;
    }

    private void delayForAuth(int ms)
    {
        try {
            Thread.sleep(ms);
            if (name=="") {
                Authtimeout = true;
                myServer.PrintMessage("достигнуто время по таймауту подключения: " + Timeout);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public ClientHandler(MyServer myServer, Socket socket) {
        try {
            this.myServer = myServer;
            this.socket = socket;
            this.in = new DataInputStream(socket.getInputStream());
            this.out = new DataOutputStream(socket.getOutputStream());
            this.name = "";
            myServer.PrintMessage("ожидание логина пароля");
            new Thread(() -> {
                try {
                    authentication();
                    if (name!=""){
                        readMessages();
                    }
                    //closeConnection();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finally {
                    closeConnection();
                }

            }).start();

        } catch (IOException e) {
            throw new RuntimeException("Проблемы при создании обработчика клиента");
        }
    }


    public void authentication() throws IOException {
        //long t1 = System.currentTimeMillis();
        new Thread(() -> {
            delayForAuth(Timeout);
        }).start();

        while (!Authtimeout) {
            if (in.available()>0) {
                String str = in.readUTF();
                if (str.startsWith("/auth")) {
                    String[] parts = str.split("\\s");
                    String nick = myServer.getAuthService().getNickByLoginPass(parts[1], parts[2]);
                    if (nick != null) {
                        if (!myServer.isNickBusy(nick)) {
                            sendMsg("/authok " + nick);
                            name = nick;
                            myServer.subscribe(this);
                            myServer.broadcastMsg("/"+name + " зашел в чат");
                            return;
                        } else {
                            sendMsg("Учетная запись уже используется");
                        }
                    } else {
                        sendMsg("/authfail");
                    }
                }
            }
        }
        //closeConnection();
    }

    public void readMessages() throws IOException {

        while (true) {
            if (in.available()>0) {
                String str = in.readUTF();
                if (str.startsWith("/")) {
                    if (str.equals("/end")) {
                        myServer.PrintMessage("юзер завершил работу: " + name);
                        return;
                    }
                    if (str.startsWith("/w ")) {
                        String[] tokens = str.split("\\s");
                        String nick = tokens[1];
                        String msg = str.substring(4 + nick.length());
                        myServer.sendMsgToClient(this, nick, msg);
                    }
                    if (str.startsWith("/changenickname ")){
                        String[] tokens = str.split("\\s");
                        String login = tokens[1];
                        String password = tokens[2];
                        String newnickname = tokens[3];
                        try {
                            boolean res = myServer.getAuthService().changeNickName(login, password, newnickname);
                            if (res)
                            {
                                this.name = newnickname;
                                myServer.sendServiceMsgToClient(this,"/changenickname",newnickname,"successfully");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {
                    myServer.PrintMessage("от " + name + ": " + str);
                    myServer.broadcastMsg(name + ": " + str);
                }
            }
        }
    }

    public void sendMsg(String msg) {
        try {
            out.writeUTF(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeConnection() {
        myServer.unsubscribe(this);
        if (name!="") {
            myServer.broadcastMsg(name + " вышел из чата");
        } else
        {
            myServer.PrintMessage("выход неавторизованного пользователя");
        }
        name = "";
        try {
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


