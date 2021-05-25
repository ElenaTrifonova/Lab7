package server;

import java.io.Serializable;

public class Session implements Serializable {
    private String user;
    private String pass;

    public Session(String user, String pass){
        this.user = user;
        this.pass = pass;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getUser() {
        return user;
    }

    public String getPass() {
        return pass;
    }
}
