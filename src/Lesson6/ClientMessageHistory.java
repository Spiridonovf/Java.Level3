package Lesson6;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

public class ClientMessageHistory  implements Serializable {
    private int maxSize = 10;
    private ArrayList<ClientMessage> messageHistory = new ArrayList<ClientMessage>();
    private String filename;

    public ClientMessageHistory(int maxSize, String filename) {
        this.maxSize = maxSize;
        this.filename = filename;
        LoadHistory();
    }

    public void LoadHistory() {
        try (ObjectInputStream objIn = new ObjectInputStream(new FileInputStream(filename))) {
            this.messageHistory = (ArrayList<ClientMessage>) objIn.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void PrintCurrentHistory()
    {
        System.out.println("====история сообщений чата====");
        messageHistory.forEach(msg->System.out.println(msg.getString()));
        System.out.println("======конец истории=======");
    }
    public void SaveHistory(){

        try (ObjectOutputStream objOut = new ObjectOutputStream(new FileOutputStream(filename))) {
            objOut.writeObject(this.messageHistory);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void PushMessage(String message, String whoSend, Date datetimeMsg){
        ClientMessage msg = new ClientMessage(message, whoSend, datetimeMsg);
        this.messageHistory.add(msg);
        while (this.messageHistory.size() > this.maxSize) {
            this.messageHistory.remove(0);
        }
    }
}
