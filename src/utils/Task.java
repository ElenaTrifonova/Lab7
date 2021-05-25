package utils;

import app.App;
import commands.Command;
import server.Response;
import server.Server;
import server.Session;

import java.io.IOException;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.concurrent.*;
import java.util.logging.Logger;

public class Task extends RecursiveAction {
    private App application;
    private byte[] data;
    private SocketAddress socketAddress;
    private DatagramChannel datagramChannel;

    public Task(App application, byte[] data, SocketAddress socketAddress, DatagramChannel datagramChannel){
        this.application = application;
        this.data = data;
        this.socketAddress = socketAddress;
        this.datagramChannel = datagramChannel;
    }


    public static final Logger log = Logger.getLogger(String.valueOf(Server.class));


/*
    public void run(App application, byte[] data, SocketAddress socketAddress) {

*/
    public void compute() {
        try {
            Command command = new Serialization<Command>().readObject(data);
            log.info("Server receive command " + command.toString());
            Session session = application.getSession(command.getUser());
            String result;
            try {
                result = command.execute(application, session);
            } catch (NullPointerException e) {
                e.printStackTrace();
                result = "Ошибка на сервере. Команда не выполнена" +
                        "\nАктивная сессия не найлена, видимо сервер отключился на некоторое время, перезапустите клиента";
            }
            Response response = new Response(result, command.getUser());
            log.info("Command " + command.toString() + " is completed, send an answer to the client");

            byte[] answer = new Serialization<Response>().writeObject(response);

            class Sender{
                private Callable<Integer> getTaskSender() {
                    return () -> {
                        ByteBuffer byteBuffer = ByteBuffer.wrap(answer);
                        int number = datagramChannel.send(byteBuffer, socketAddress);
                        return number;
                    };
                }
            }
            Sender sender = new Sender();
            Callable<Integer> task = sender.getTaskSender();
            ExecutorService service = Executors.newCachedThreadPool();

            Future<Integer> resultSender =  service.submit(task);

        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
    }


}
