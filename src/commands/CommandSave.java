/*
package commands;

import app.App;
import app.FileManager;
import app.Organization;
import server.Session;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandSave extends Command {
    private App application;

    public String execute(App application, Session session) {
        this.application = application;
        FileManager man = new FileManager();
        if (man.saveCollection(application.getCollection())) return "Collection was saved to the file";
        else return "Problems with the environment variable and the default file on the server, the collection is not saved...";


    }

    public ConcurrentHashMap<Long, Organization> getCollection() {
        return application.getCollection();
    }

    public CopyOnWriteArraySet<Long> getIdList() {
        return application.getIdList();
    }

    @Override
    String getCommandInfo() {
        return "save : сохранить коллекцию в файл";
    }

    @Override
    public boolean withArgument() {
        return false;
    }



    @Override
    public String toString() {
        return "save";
    }

    public void act(String argument){   }
}
*/