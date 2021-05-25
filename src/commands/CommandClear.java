package commands;

import app.App;
import app.Organization;
import client.User;
import server.Session;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandClear extends Command{


    private App application;


    @Override
    public String execute(App application, Session session){
        application.getDataBaseManager().removeAll(session.getUser());
        application.getCollection().forEach((k, v) -> {
            if (v.getOwner().equals(session.getUser())) {
                application.getIdList().remove(v.getId());
                application.getCollection().remove(k);
            }
        });
        return "Все элементы принадлежашие пользователю " + session.getUser() + " удалены.";
    }

    @Override
    public ConcurrentHashMap<Long, Organization> getCollection() {
        return application.getCollection();
    }

    @Override
    public CopyOnWriteArraySet<Long> getIdList() {
        return application.getIdList();
    }


    @Override
    String getCommandInfo() {
        return "clear : очистить коллекцию";
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean withArgument(){
        return false;
    }

    @Override
    public String toString(){
        return "clear";
    }

    public void act(String argument){   }
}
