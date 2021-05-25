package client;

import commands.*;
import exceptions.WithoutArgException;
import app.Handler;

import java.io.IOException;
import java.util.Scanner;

/**
 * Class that creates handler of the commands and sets the commands to it,
 * creates application and starts it
 */
public class MainCl {

    private static Client activeClient = null;

    public static Client getActiveClient() {
        return activeClient;
    }

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.println("Enter port: ");
            int port = Integer.parseInt(scanner.nextLine());
            if (port == 0) throw new WithoutArgException();

            Handler handler = new Handler();
            handler.addCommand(new CommandLogin().toString(), new CommandLogin());
            handler.addCommand(new CommandRegister().toString(), new CommandRegister());

            Client client = new Client();
            activeClient = client;
            client.connect(port);
            handler.setClient(client);
            client.setHandler(handler);
            System.out.println("Client-server application for managing a collection of objects");
            System.out.println("Author: Trifonova Elena R3135");
            System.out.println("To view the available commands: type - help, and to exit: type - exit.");
            System.out.println("Для работы с коллекцией зарегистрируйтесь или авторизуйтесь с помощью команд:");
            System.out.println(new CommandRegister().getCommandInfo());
            System.out.println(new CommandLogin().getCommandInfo());
            handler.run(new Scanner(System.in));
        } catch (WithoutArgException e) {
            System.out.println("Port can't be null");
        } catch (NumberFormatException e) {
            System.out.println("Port is integer number!!!");
        } catch (IllegalArgumentException e) {
            System.out.println("Enter valid number of port...");
        } catch (IOException e) {
            System.out.println("Problems with connection, check the arguments...");
            e.printStackTrace();
        }
    }
}
