package server;

import client.User;

import java.io.Serializable;

public class Response implements Serializable {
    private String answer;
    private User user;

    public Response(String answer) {
        this.answer = answer;
    }

    public Response(String answer, User user) {
        this.answer = answer;
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
}