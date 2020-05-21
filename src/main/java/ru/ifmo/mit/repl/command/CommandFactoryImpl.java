package ru.ifmo.mit.repl.command;

import ru.ifmo.mit.repl.env.Context;

import java.util.List;

/*
    Ипмлементация фабрики для создания команд шелла.
 */
public class CommandFactoryImpl extends CommandFactory {
    private final Context context;

    public CommandFactoryImpl(Context context) {
        this.context = context;
    }

    @Override
    public CommandExecutable makeAssignCommand(List<String> arguments) {
        return new CommandAssign(arguments, context);
    }

    @Override
    public CommandExecutable makeCatCommand(List<String> arguments) {
        return new CommandCat(arguments);
    }

    @Override
    public CommandExecutable makeEchoCommand(List<String> arguments) {
        return new CommandEcho(arguments);
    }

    @Override
    public CommandExecutable makeExternalCommand(List<String> arguments) {
        return new CommandExternal(arguments);
    }

    @Override
    public CommandExecutable makePipeCommand(List<CommandExecutable> commands) {
        return new CommandPipe(commands);
    }

    @Override
    public CommandExecutable makePwdCommand(List<String> arguments) {
        return new CommandPWD(arguments);
    }

    @Override
    public CommandExecutable makeWCCommand(List<String> arguments) {
        return new CommandWC(arguments);
    }

    @Override
    public CommandExecutable makeExitCommand(List<String> arguments) { return new CommandExit(arguments); }

    @Override
    public CommandExecutable makeGrepCommand(List<String> arguments) { return new CommandGrep(arguments); }
}
