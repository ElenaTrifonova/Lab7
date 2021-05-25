package commands;

import app.App;
import app.Organization;
import client.User;
import server.Session;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandShow extends Command{

    private App application;

    @Override
    public String execute(App application, Session session){
        this.application = application;
        StringBuilder result = new StringBuilder();
        if (application.getCollection().size() == 0){
            return "Collection is empty.";
        }
        else{
            System.out.println("Elements of collection:");
            application.getCollection().forEach ((k, v) ->{
                result.append("Key: ").append(k).append("\n").append("Value:").append("\n").append(v.toString()).append("\n");
            });
            return result.deleteCharAt(result.length()-1).toString();
        }
    }


    @Override
    String getCommandInfo() {
        return "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
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
        return "show";
    }

    public void act(String argument){   }

    @Override
    public User getUser() {
        return user;
    }
}
