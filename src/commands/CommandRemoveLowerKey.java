package commands;

import client.User;
import exceptions.IdNotFoundException;
import exceptions.InputIntervalException;
import app.App;
import app.Organization;
import server.Session;
import utils.Validation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandRemoveLowerKey extends Command implements CommandWithArg{
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
        idList = application.getIdList();
        Set<Long> set = collection.keySet();
        ArrayList<Long> for_deleted = new ArrayList<>();
        int count_del = 0;
        for (Long key_cur : set) {
            if (key_cur < this.key) {
                count_del++;
                for_deleted.add(key_cur);
            }
        }
        if (count_del > 0) {
            for (Long key_cur : for_deleted) {
                idList.remove(collection.get(key_cur).getId());
                collection.remove(key_cur);
            }
            return count_del + " key(s) were deleted";
        }
        else return "Collection wasn't changed";
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
        return "remove_lower_key null : удалить из коллекции все элементы, ключ которых меньше, чем заданный";
    }

    @Override
    public boolean withArgument(){
        return true;
    }

    @Override
    public String toString(){
        return "remove_lower_key";
    }

    @Override
    public User getUser() {
        return user;
    }
}
