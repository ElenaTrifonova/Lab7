package app;

import client.Client;
import commands.*;
import exceptions.InputIntervalException;
import exceptions.StackIsLimitedException;

import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class Handler {

    private HashMap<String, Command> commands;
    private boolean exitCommand = false;
    private Client client;


    public Handler() {
        commands = new HashMap<>();
    }

    /**
     * Method that adds new command to handler
     *
     * @param string  - ключ
     * @param command - значение
     */
    public void addCommand(String string, Command command) {
        commands.put(string, command);
    }

    /**
     * Exiting the console application
     */
    public void setExitCommand() {
        exitCommand = true;
    }

    public boolean removeCommand(String string, Command command){
        return commands.remove(string, command);
    }

    public void removeAllCommands(){
        commands = new HashMap<>();
    }

    /**
     * список команд по умолчанию для авторизованного пользователя
     */
    public void setDefaultPack(){
        removeAllCommands();
        addCommand(new CommandInsert().toString(), new CommandInsert());
        addCommand(new CommandRemoveGreaterKey().toString(), new CommandRemoveGreaterKey());
        addCommand(new CommandRemoveLowerKey().toString(), new CommandRemoveLowerKey());
        addCommand(new CommandClear().toString(), new CommandClear());
        addCommand(new CommandExecuteScript().toString(), new CommandExecuteScript());
        addCommand(new CommandExit().toString(), new CommandExit());
        addCommand(new CommandRemoveKey().toString(), new CommandRemoveKey());
        addCommand(new CommandRemoveGreater().toString(), new CommandRemoveGreater());
        addCommand(new CommandHelp().toString(), new CommandHelp());
        addCommand(new CommandFilterPostal().toString(), new CommandFilterPostal());
        addCommand(new CommandInfo().toString(), new CommandInfo());
        addCommand(new CommandAverage().toString(), new CommandAverage());
        addCommand(new CommandShow().toString(), new CommandShow());
        addCommand(new CommandMax().toString(), new CommandMax());
        addCommand(new CommandUpdateID().toString(), new CommandUpdateID());
    }

    /**
     * method that starts the handler
     *
     * @param scanner - сканер, с которого считываем команды
     */
    public void run(Scanner scanner) {
        try {
            Runtime.getRuntime().addShutdownHook(new Thread(() -> client.sendCommandAndReceiveAnswer(new CommandExit())));
            while (!exitCommand && scanner.hasNext()) {
                try {
                    //ищем в следующей строке команду,если она не в начале строки
                    String[] nextLine = scanner.nextLine().split(" ");
                    int positionOfCommand = 0;
                    while (nextLine[positionOfCommand].equals("") || nextLine[positionOfCommand].equals("\n"))
                        positionOfCommand++;
                    String nextCommand = nextLine[positionOfCommand];
                    if(nextCommand.equals("\u0004")) throw new NoSuchElementException();
                    String argument = null;
                    if (nextLine.length > positionOfCommand + 1) argument = nextLine[positionOfCommand + 1];
                    Command command = commands.get(nextCommand);
                    if (command == null) throw new InputIntervalException();
                    if ((!command.withArgument() && argument != null) || nextLine.length > positionOfCommand + 2)
                        throw new IndexOutOfBoundsException("Command " + command.toString() + " has too many arguments!!!");
                    if (command.withArgument() && argument == null)
                        throw new IndexOutOfBoundsException("This command is expected to have argument!!!");
                    if (command.withArgument()) {
                        command.act(argument);
                    }
                    if(exitCommand) return;
                    if (command instanceof CommandExit) setExitCommand();
                    client.sendCommandAndReceiveAnswer(command);
                } catch (InputIntervalException | StringIndexOutOfBoundsException | ArrayIndexOutOfBoundsException e) {
                    System.out.println("Error of entering!!! Command doesn't exist. help - for showing available commands");
                    scanner.reset();//иначе при вводе большого кол-ва пустых строк будет выведено много предупреждений
                } catch (IndexOutOfBoundsException | StackIsLimitedException e) {
                    System.out.println(e.getMessage());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (NoSuchElementException e){
            client.sendCommandAndReceiveAnswer(new CommandExit());
        }
    }

    /**
     *Method that creates connection
     *between client and handler
     */
    public void setClient(Client client) {
        this.client = client;
    }
}
