package ru.ifmo.mit.repl.command;

import java.io.*;
import java.util.List;

/*
    Класс, представляющий команду вывода на экран своих аргументов.
    Выводит все свои аргументы.
 */
public class CommandEcho extends Command {
    public CommandEcho(List<String> arguments) {
        super(arguments);
    }

    @Override
    public void execute(InputStream input, OutputStream output) throws IOException {
        PrintWriter writer = new PrintWriter(output);
        writer.println(String.join(" ", getArguments()));
        writer.flush();
    }
}
