package commands;

import app.App;
import app.Organization;
import client.User;
import server.Session;
import utils.Validation;

import java.io.Console;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

public class CommandLogin extends Command{
    private User userForLogin;

    //авторизация, если такой пользователь зарегистрирован
    //ВАЛИДАЦИЯ
    @Override
    public String execute(App application, Session session) {
        if (application.getDataBaseManager().containsUser(userForLogin)) {
            Session sessionActual = new Session(userForLogin.getName(), userForLogin.getPass());
            if(!application.addSession(userForLogin, sessionActual)) return "Пользователь с ником " + userForLogin.getName() + " уже авторизован.\nАвторизация с двух устройств запрещена!";
            user = userForLogin;
            return "Пользователь с ником " + userForLogin.getName() + " успешно авторизован\n" +
                    "Теперь вам доступны все команды, для их просмотра введите help";
        }
        user = null;
        return "Пароль или логин некорректен, авторизация отменена";
    }

    @Override
    public ConcurrentHashMap<Long, Organization> getCollection() {
        return null;
    }

    @Override
    public CopyOnWriteArraySet<Long> getIdList() {
        return null;
    }

    @Override
    public String getCommandInfo() {
        return "login name : авторизоваться по имени name";
    }

    @Override
    public String toString() {
        return "login";
    }

    @Override
    public boolean withArgument() {
        return true;
    }

    //просим пароль
    @Override
    public void act(String argument) {
        String pass;
        //Console console = System.console();
        Scanner scanner = new Scanner(System.in);
        do {
            System.out.println("Введите пароль для авторизации(от 6 до 20 символов):");
            pass = scanner.nextLine();
        } while (!Validation.checkInterval((long) pass.length(), 6, false, "Пароль не удовлетворяет условиям. Попробуйте снова"));
        userForLogin = new User(argument, pass);
    }

    @Override
    public User getUser() {
        return userForLogin;
    }
}
