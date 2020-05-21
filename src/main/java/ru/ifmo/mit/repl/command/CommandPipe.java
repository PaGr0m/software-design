package ru.ifmo.mit.repl.command;

import java.io.*;
import java.util.List;

/*
    Класс, представляющий команду неименнованного канала.
 */
public class CommandPipe extends Command {
    private final List<CommandExecutable> commands;

    public CommandPipe(List<CommandExecutable> commands) {
        super(List.of());
        this.commands = commands;
    }

    public List<CommandExecutable> getCommands() {
        return commands;
    }

    @Override
    public void execute(InputStream input, OutputStream output) throws IOException {
        if (getCommands().isEmpty()) {
            return;
        }
        InputStream currentInputStream = input;
        for (int i = 0; i < commands.size() - 1; i++) {
            PipedOutputStream currentOutputStream = new PipedOutputStream();
            InputStream nextInputStream = new PipedInputStream(currentOutputStream);

            commands.get(i).execute(currentInputStream, currentOutputStream);
            currentOutputStream.close();

            currentInputStream = nextInputStream;
        }
        commands.get(commands.size() - 1).execute(currentInputStream, output);
        output.flush();
    }

    @Override
    public String toString() {
        return "CommandPipe{" +
                "commands=" + commands +
                '}';
    }
}
