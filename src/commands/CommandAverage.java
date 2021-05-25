package commands;

import app.App;
import app.Organization;
import client.User;
import server.Session;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandAverage extends Command{

    private App application;


    @Override
    public String execute(App application, Session session){
        this.application=application;
        return "Average of employees count is " + application.getSumOfEmployeesCount()/application.getCollection().size();
    }

    @Override
    String getCommandInfo() {
        return "average_of_employees_count : вывести среднее значение поля employeesCount для всех элементов коллекции";
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
        return "average_of_employees_count";
    }

    public void act(String argument){   }

    public User getUser(){ return user; }

}
