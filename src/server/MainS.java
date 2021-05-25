package server;

import exceptions.WithoutArgException;
import app.App;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.*;


public class MainS {
    public static final Logger log = Logger.getLogger(String.valueOf(MainS.class));
    public static void main(String[] args) throws SQLException {

        try {
            Scanner scanner1 = new Scanner(System.in);
            System.out.println("Enter port: ");
            int port = Integer.parseInt(scanner1.nextLine());
            if (port == 0) throw new WithoutArgException();

            App application = new App();
            System.out.println("The server application is running...");
            Server server = new Server();
            server.connect(port);
            log.info("Connection is established, listen port: " + port);
            server.run(application);
        } catch (WithoutArgException e) {
            System.out.println("Port can't be null!");
        } catch (NumberFormatException e) {
            System.out.println("Port is an integer number!");
        } catch (IOException e) {
            System.out.println("Problems with connection...");
        }
    }
}
