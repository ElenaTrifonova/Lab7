package commands;

import app.App;
import app.Organization;
import client.User;
import server.Session;

import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandHelp extends Command{

    private App application;

    @Override
    public String execute(App application, Session session){
        this.application = application;
        return "The available commands: " + "\n" +
                new CommandHelp().getCommandInfo() + "\n" +
                new CommandInfo().getCommandInfo() + "\n" +
                new CommandShow().getCommandInfo() + "\n" +
                new CommandInsert().getCommandInfo() + "\n" +
                new CommandUpdateID().getCommandInfo() + "\n" +
                new CommandRemoveKey().getCommandInfo() + "\n" +
                new CommandClear().getCommandInfo() + "\n" +
                new CommandExecuteScript().getCommandInfo() + "\n" +
                new CommandExit().getCommandInfo() + "\n" +
                new CommandAverage().getCommandInfo() + "\n" +
                new CommandRemoveGreater().getCommandInfo() + "\n" +
                new CommandRemoveGreaterKey().getCommandInfo() + "\n" +
                new CommandRemoveLowerKey().getCommandInfo() + "\n" +
                new CommandFilterPostal().getCommandInfo() + "\n" +
                new CommandMax().getCommandInfo();

    }

    @Override
    String getCommandInfo() {
        return "help: вывести справку по доступным командам";
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
        return "help";
    }

    public void act(String argument){   }
}
