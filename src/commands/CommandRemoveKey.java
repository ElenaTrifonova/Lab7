package commands;

import client.User;
import exceptions.IdNotFoundException;
import exceptions.InputIntervalException;
import app.App;
import app.Organization;
import server.Session;
import utils.Validation;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;


public class CommandRemoveKey extends Command implements CommandWithArg {
    private CopyOnWriteArraySet<Long> idList;
    protected Organization org;
    protected Long key;
    private ConcurrentHashMap<Long, Organization> collection;


    public void act(String argument) {
        if (Validation.checkStringToLong(argument, "Error of entering, argument is long, try again."))
            this.key = Long.valueOf(argument);
        else throw new InputIntervalException();
    }


    public String execute(App application, Session session) {
        try {
            if ((!application.getCollection().get(key).getOwner().equals(session.getUser())) || !(application.getDataBaseManager().remove(key) > 0)) {
                if (application.getCollection().keySet().stream().noneMatch(key -> key == this.key))
                    throw new IdNotFoundException("Элемент не удален, т.к. элемента с таким ключом нет в коллекции");
                throw new IdNotFoundException("Элемент не удален, т.к. вы не являетесь владельцем этого элемента");
            }
            collection = application.getCollection();
            int startSize = collection.size();
            idList = application.getIdList();
            idList.remove(collection.get(key).getId());
            collection.remove(key);


            if (collection.size() < startSize) {
                //idList.remove(collection.get(key).getId());
                return "Element was deleted.";
            } else return "Element wasn't deleted. Maybe key isn't exist.";
        } catch (IdNotFoundException e) {
            return e.getMessage();
        }
    }

    public ConcurrentHashMap<Long, Organization> getCollection() {
        return collection;
    }

    public CopyOnWriteArraySet<Long> getIdList() {
        return idList;
    }

    @Override
    String getCommandInfo() {
        return "remove_key null : удалить элемент из коллекции по его ключу";
    }

    @Override
    public boolean withArgument() {
        return true;
    }

    @Override
    public String toString() {
        return "remove_key";
    }

    @Override
    public User getUser() {
        return user;
    }
}
