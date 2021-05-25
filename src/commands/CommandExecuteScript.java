package commands;

import client.Client;
import client.MainCl;
import app.App;
import app.Organization;
import client.User;
import server.Session;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Hashtable;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandExecuteScript extends Command implements CommandWithArg{
    private ConcurrentHashMap<Long, Organization> collection;
    private CopyOnWriteArraySet<Long> idList;
    private String status;



    public void act(String argument){
        try {
            File file = new File(argument);
            Scanner fileScanner = new Scanner(file);
            Client client = MainCl.getActiveClient();
            client.incrementScriptCounter();
            client.getHandler().run(fileScanner);
            client.decrementScriptCounter();
            status = "Script " + argument + " finished the execution.";
            return;
        } catch (FileNotFoundException e) {
            System.out.println("Script wasn't found...");
        } catch (NullPointerException e) {
            System.out.println("No active client was found...");
        }
        status="There is no such script! All scripts must be in the same folder as the jar or src.";

    }

    public String execute(App application, Session session){
        collection = application.getCollection();
        idList = application.getIdList();
        return status;
    }

    public ConcurrentHashMap<Long, Organization> getCollection(){
        return collection;
    }

    public CopyOnWriteArraySet<Long> getIdList(){
        return idList;
    }


    @Override
    String getCommandInfo() {
        return "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.";
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
        return "execute_script";
    }
}
