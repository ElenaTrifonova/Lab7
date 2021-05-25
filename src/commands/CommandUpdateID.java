package commands;

import client.User;
import exceptions.IdNotFoundException;
import exceptions.InputIntervalException;
import app.App;
import app.OrgGeneration;
import app.Organization;
import server.Session;
import utils.Validation;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandUpdateID extends Command{
    private CopyOnWriteArraySet<Long> idList;
    protected Organization org;
    protected Long argument;
    private ConcurrentHashMap<Long, Organization> collection;


    public void act(String arg) {
        org = OrgGeneration.generate();
        if ((Validation.checkStringToLong(arg, "Error of entering, argument is long, try again."))
                || (Validation.checkInterval(Long.valueOf(arg), 0, false, "Error of entering")))
            this.argument = Long.valueOf(arg);
        else throw new InputIntervalException();
        org.setId(Long.valueOf(arg));
    }

    @Override
    public String execute(App application, Session session) {
        org.setOwner(session.getUser());
        try {
            if (!application.getCollection().values().removeIf(org -> org.getId() == this.org.getId()) && org.getOwner().equals(session.getUser()) || !(application.getDataBaseManager().update(org.getId(), org) > 0)) {
                if (application.getCollection().values().stream().noneMatch(org1 -> org1.getId() == this.org.getId()))
                    throw new IdNotFoundException("Элемент нельзя обновить, т.к. элемента с таким id нет в коллекции");
                throw new IdNotFoundException("Элемент нельзя обновить, т.к. он не принадлежит вам");
            }
            collection = application.getCollection();
            idList = application.getIdList();
            idList.add(org.getId());
            collection.put(argument, org);
            application.sort(collection);
            return "Element with id " + argument + " was updated";
        }catch (IdNotFoundException e) {
            return e.getMessage();
        }

    }
        @Override
        public ConcurrentHashMap<Long, Organization> getCollection () {
            return collection;
        }

        @Override
        public CopyOnWriteArraySet<Long> getIdList () {
            return idList;
        }
        @Override
        String getCommandInfo () {
            return "update id {element} : обновить значение элемента коллекции, id которого равен заданному";
        }

        @Override
        public boolean withArgument () {
            return true;
        }

        @Override
        public String toString () {
            return "update";
        }

    @Override
    public User getUser() {
        return user;
    }
    }
