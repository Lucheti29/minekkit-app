package ar.com.overflowdt.minekkit.models;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Fede on 26/12/2014.
 */
public class News {
    private String tid;
    private String subject;
    private String username;
    private String message;
    private String dateline;

    public String getTid() {
        return tid;
    }

    public String getSubject() {
        return subject;
    }

    public String getUsername() {
        return username;
    }

    public String getMessage() {
        return message;
    }

    public String getDateline() {
        DateFormat df = new SimpleDateFormat("dd MMM yyyy HH:mm");
        return df.format(new Date(Long.parseLong(dateline, 10) * 1000));
    }
}
