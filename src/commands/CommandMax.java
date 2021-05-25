package commands;

import app.App;
import app.Organization;
import client.User;
import server.Session;

import java.time.LocalDate;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandMax extends Command {

    private App application;

    @Override
    public String execute(App application, Session session) {
        this.application = application;
        LocalDate max;
        Long maxKey;
        if (application.getCollection().size() > 0) {

            try {
                maxKey = application.getCollection().keySet().iterator().next();
                max = application.getCollection().get(maxKey).getCreationDate();
                Set<Long> set = application.getCollection().keySet();
                for (Long key : set) {
                    Organization org = application.getCollection().get(key);
                    if (org.getCreationDate().isAfter(max)) {
                        maxKey = key;
                        max = org.getCreationDate();
                    }
                }
                return "Information about organization with max creation date:" + application.getCollection().get(maxKey).toString();
            } catch (NullPointerException e) {
                return "Error";
            }
        } else return "Collection is empty.";
    }

    @Override
    String getCommandInfo() {
        return "max_by_creation_date : вывести любой объект из коллекции, значение поля creationDate которого является максимальным";
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public ConcurrentHashMap<Long, Organization> getCollection(){
        return application.getCollection();
    }

    @Override
    public CopyOnWriteArraySet<Long> getIdList(){
        return application.getIdList();
    }

    @Override
    public boolean withArgument(){
        return false;
    }

    @Override
    public String toString(){
        return "max_by_creation_date";
    }

    public void act(String argument){   }
}
