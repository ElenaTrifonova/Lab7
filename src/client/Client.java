package client;

import commands.Command;
import exceptions.StackIsLimitedException;
import app.Handler;
import utils.Serialization;
import server.Response;

import java.io.IOException;
import java.net.*;

public class Client {
    private SocketAddress socketAddress;
    private DatagramSocket socket;
    private Handler handler;
    private User user;
    private Serialization<Command> commandSerialization = new Serialization<>();
    private Serialization<Response> responseSerialization = new Serialization<>();
    private int scriptCount = 0;
    private static final int STACK_SIZE = 10;



    public Handler getHandler() {
        return handler;
    }

    /**
     * Method that sets handler of commands
     * @param handler
     */
    public void setHandler(Handler handler) {
        this.handler = handler;
    }

    /**
     * Method that creates connection between
     * server and client
     * @param port
     * @throws IOException
     */
    public void connect(int port) throws IOException {
        try {
            //InetAddress address = InetAddress.getByName(host);
            InetAddress address = InetAddress.getLocalHost();
            if (address == null) throw new NullPointerException();
            System.out.println("Connect to " + address);
            socketAddress = new InetSocketAddress(address, port);
            socket = new DatagramSocket();
            socket.connect(socketAddress);
        } catch (NullPointerException e) {
            System.out.println("The entered address does not exist!");
        }
    }

    /**
     * Method that sends command to the server
     * and receives answer
     * @param command
     */
    public void sendCommandAndReceiveAnswer(Command command) {
        try {
            byte[] commandInBytes = commandSerialization.writeObject(command);
            DatagramPacket packet = new DatagramPacket(commandInBytes, commandInBytes.length, socketAddress);
            socket.send(packet);
            System.out.println("Request sent to the server...");
            byte[] answerInBytes = new byte[65536];
            packet = new DatagramPacket(answerInBytes, answerInBytes.length);
            socket.receive(packet);
            Response response = responseSerialization.readObject(answerInBytes);
            String result = response.getAnswer();

            if (user == null) {
                if (response.getUser() != null) handler.setDefaultPack();
                user = response.getUser();
            }
            System.out.println("Received a response from the server: ");
            System.out.println(result);
        } catch (IOException e) {
            System.out.println("The server is currently unavailable...");
        } catch (ClassNotFoundException e) {
            System.out.println("The client was waiting for a response in the form of a class Response, but received something incomprehensible...");
        }
    }

    public int getScriptCount() {
        return scriptCount;
    }

    /**
     * Method that increases the number of scripts in file
     */
    public void incrementScriptCounter() {
        if (scriptCount >= STACK_SIZE) throw new StackIsLimitedException();
        scriptCount++;
    }

    /**
     * Method that decreases the number of scripts in file
     */
    public void decrementScriptCounter() {
        scriptCount--;
    }
}
