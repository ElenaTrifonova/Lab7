package server;

import commands.Command;
import commands.CommandExit;

import app.App;
import utils.Serialization;
import utils.Task;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

import java.util.concurrent.*;
import java.util.logging.*;

public class Server {
    private DatagramChannel channel;
    private SocketAddress address;
    private byte[] buffer;


    public Server() {
        buffer = new byte[65536];
    }

    /**
     * Method that accepts connections
     * @param port
     * @throws IOException
     */
    public void connect(int port) throws IOException {
        address = new InetSocketAddress(port);
        channel = DatagramChannel.open();
        channel.configureBlocking(false);
        channel.bind(address);
        Runtime.getRuntime().addShutdownHook(new Thread(() -> System.out.println("—ервер закончил работу...")));
    }

    /**
     * Method that reads received information
     * and send the answer back
     * @param application
     */
    public void run(App application) {
        try {
            Callable<SocketAddress> task = getTask();
            ExecutorService service = Executors.newCachedThreadPool();
            while (true) {
                Future<SocketAddress> result = service.submit(task);
                SocketAddress socketAddress = result.get();
                byte[] copyData = new byte[buffer.length];
                System.arraycopy(buffer, 0, copyData, 0, buffer.length);
                new ForkJoinPool().invoke(new Task(application, copyData, socketAddress, channel));
            }
        } catch (ClassCastException e) {
            System.out.println("The server was expecting a command, but got something wrong...");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //получение запроса
    private Callable<SocketAddress> getTask() {
        return () -> {
            ByteBuffer byteBuffer = ByteBuffer.wrap(buffer);
            SocketAddress socketAddress;
            do {
                socketAddress = channel.receive(byteBuffer);
            } while (socketAddress == null);
            return socketAddress;
        };
    }

}
