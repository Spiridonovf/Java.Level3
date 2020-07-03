package Lesson3;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class ClientMessage   implements Serializable{
    private String message;
    private String whoSend;
    private Date datetimeMsg;

    public ClientMessage(String message, String whoSend, Date datetimeMsg) {
        this.message = message;
        this.whoSend = whoSend;
        this.datetimeMsg = datetimeMsg;
    }
    public String getString(){
        return new SimpleDateFormat("dd.MM.yyyy HH:mm:ss").format(datetimeMsg)+" "+whoSend+":"+message;
    }
}
