package commands;

import client.User;
import exceptions.IdNotFoundException;
import exceptions.InputIntervalException;
import app.App;
import app.Organization;
import server.Session;
import utils.Validation;

import java.util.Hashtable;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandRemoveGreater extends Command implements CommandWithArg{
    private CopyOnWriteArraySet<Long> idList;
    protected Organization org;
    protected Long id;
    private ConcurrentHashMap<Long, Organization> collection;


    public void act(String argument){
        if((!Validation.checkStringToLong(argument, "Error of entering, argument is long, try again."))
          ||(!Validation.checkInterval(Long.valueOf(argument), 0, false, "Error of entering"))) throw new InputIntervalException();
        this.id = Long.valueOf(argument);

    }

    @Override
    public String execute(App application, Session session){
        try {
        if (application.getCollection().values().stream().noneMatch(org -> org.getOwner().equals(session.getUser()) && org.getId() == id) || !(application.getDataBaseManager().remove(id) > 0)) {
            if (application.getCollection().values().stream().noneMatch(org -> org.getId() == id))
                throw new IdNotFoundException("Элемент не удален, т.к. элемента с таким id нет в коллекции");
            throw new IdNotFoundException("Элемент не удален, т.к. вы не являетесь владельцем этого элемента");
        }
        collection = application.getCollection();
        idList = application.getIdList();

        Set<Long> set = collection.keySet();

        int count_del = 0;
        for (Organization org : collection.values()) {
            while (set.iterator().hasNext()) {
                Long key = set.iterator().next();
                if (org.getId() > this.id) {
                    count_del++;
                    idList.remove(org.getId());
                    collection.remove(key);
                }
            }
        }
            if (count_del > 0) return count_del + " elements were deleted";
            else return "Nothing was changed. There are no elements bigger than given.";
        } catch (IdNotFoundException e) {
            return e.getMessage();
        }

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
        return "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный";
    }

    @Override
    public boolean withArgument(){
        return true;
    }

    @Override
    public String toString(){
        return "remove_greater";
    }

    @Override
    public User getUser() {
        return user;
    }
}
