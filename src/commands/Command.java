package commands;

import app.App;
import app.Organization;
import client.User;
import server.Session;

import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public abstract class Command implements Serializable {
    User user;
    /**
     * метод исполнения команды
     * @param application - текущее работающее приложение
     */
    public abstract String execute(App application, Session session);

    public abstract ConcurrentHashMap<Long, Organization> getCollection();

    public abstract CopyOnWriteArraySet<Long> getIdList();

    /**
     * @return информация о команде, которая потом выводится с командой help
     */
    abstract String getCommandInfo();

    public abstract User getUser();

    /**
     * метод, нужный для того, чтобы из ссылки на абстрактную команды, мы знали требуется ли этой команде аргумент
     */
    public abstract boolean withArgument();

    public abstract void act(String argument) throws IOException;
}
