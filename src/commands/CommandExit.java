package commands;

import app.App;
import app.Organization;
import client.User;
import server.Session;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandExit extends Command{

    private App application;


    @Override
    public String execute(App application, Session session){
        application.removeSession(user, session);
        return "The program is finishing... Have a good day!.";

    }

    @Override
    String getCommandInfo() {
        return "exit : завершить программу (без сохранения в файл)";
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public String toString() {
        return "exit";
    }

    @Override
    public boolean withArgument() {
        return false;
    }

    @Override
    public ConcurrentHashMap<Long, Organization> getCollection() {
        return application.getCollection();
    }

    @Override
    public CopyOnWriteArraySet<Long> getIdList() {
        return application.getIdList();
    }

    public void act(String argument){   }
}
