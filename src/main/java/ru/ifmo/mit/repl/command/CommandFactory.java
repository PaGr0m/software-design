package ru.ifmo.mit.repl.command;

import java.util.List;

/*
    Фабрика для создания команд шелла.
 */
public abstract class CommandFactory {
    public abstract CommandExecutable makeAssignCommand(List<String> arguments);

    public abstract CommandExecutable makeCatCommand(List<String> arguments);

    public abstract CommandExecutable makeEchoCommand(List<String> arguments);

    public abstract CommandExecutable makeExternalCommand(List<String> arguments);

    public abstract CommandExecutable makePipeCommand(List<CommandExecutable> commands);

    public abstract CommandExecutable makePwdCommand(List<String> arguments);

    public abstract CommandExecutable makeWCCommand(List<String> arguments);

    public abstract CommandExecutable makeExitCommand(List<String> arguments);

    public abstract CommandExecutable makeGrepCommand(List<String> arguments);

    /*
        Функция-фабрика, позволяющая создавать инстансы команд, основываясь на их названиях.
     */
    public CommandExecutable makeCommand(String commandName, List<String> args) {
        switch (commandName) {
            case "cat":
                return makeCatCommand(args);
            case "echo":
                return makeEchoCommand(args);
            case "pwd":
                return makePwdCommand(args);
            case "wc":
                return makeWCCommand(args);
            case "exit":
                return makeExitCommand(args);
            case "grep":
                return makeGrepCommand(args);
            default:
                args.add(0, commandName);
                return makeExternalCommand(args);
        }
    }
}
