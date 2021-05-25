package commands;

import client.User;
import exceptions.InputIntervalException;
import app.App;
import app.OrgGeneration;
import app.Organization;
import server.Session;
import utils.Validation;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


public class CommandInsert extends Command{
    private CopyOnWriteArraySet<Long> idList;
    protected Organization org;
    protected Long argument;
    private ConcurrentHashMap<Long, Organization> collection;



    public void act(String arg){
        org = OrgGeneration.generate();
        if(Validation.checkStringToLong(arg, "Error of entering, argument is long, try again.")) this.argument = Long.valueOf(arg);
          else throw new InputIntervalException();

    }

    @Override
    public String execute(App application, Session session) {
        org.setOwner(session.getUser());
        if(!application.getDataBaseManager().addGroup(org)) return "Элемент не добвлен. Проверьте корректность данных";
        collection = application.getCollection();
        idList = application.getIdList();
        idList.add(org.getId());
        collection.put(argument, org);
        application.sort(collection);
        return "Element with key" + argument + " was added to the collection";
    }

    @Override
    public ConcurrentHashMap<Long, Organization> getCollection(){
        return collection;
    }

    @Override
    public CopyOnWriteArraySet<Long> getIdList(){
        return idList;
    }

    @Override
    String getCommandInfo() {
        return "insert null {element} : добавить новый элемент с заданным ключом";
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
        return "insert";
    }
}
