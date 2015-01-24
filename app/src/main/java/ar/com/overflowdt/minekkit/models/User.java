package ar.com.overflowdt.minekkit.models;

/**
 * Created by Fede on 24/01/2015.
 */
public class User {
    private String user;
    private String email;
    private long userId;
    private String avatar;

    public long getUserID() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getUser() {
        return user;
    }
}
