package commands;

import app.App;
import app.Organization;
import client.User;
import server.Session;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandFilterPostal extends Command{

    private App application;
    private String postal;


    public void act(String argument) {
        this.postal = argument;
    }

    /**
     * сравнение и вывод нужных групп
     */
    @Override
    public String execute(App application, Session session) {
        this.application = application;
        ConcurrentHashMap<Long, Organization> collection = getCollection();
        StringBuilder result = new StringBuilder();
        application.sort(collection).values().stream().filter(org -> org.getPostalAddress().getZipCode().equals(postal))
                .forEach(org -> result.append(org.toString()).append("\n"));
        if (collection.isEmpty()) return "Collection is empty...";
        return result.deleteCharAt(result.length() - 1).toString();
    }

    @Override
    public ConcurrentHashMap<Long, Organization> getCollection() {
        return application.getCollection();
    }

    @Override
    public CopyOnWriteArraySet<Long> getIdList(){
        return application.getIdList();
    }



    @Override
    String getCommandInfo() {
        return "filter_by_postal_address postalAddress : вывести элементы, значение поля postalAddress которых равно заданному";
    }

    @Override
    public User getUser() {
        return user;
    }

    @Override
    public boolean withArgument(){
        return true;
    }

    @Override
    public String toString(){
        return "filter_by_postal_address";
    }
}
