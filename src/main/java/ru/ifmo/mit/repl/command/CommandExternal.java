package ru.ifmo.mit.repl.command;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/*
    Класс, представляющий команду вызова сторонней команды.
 */
public class CommandExternal extends Command {
    public CommandExternal(List<String> arguments) {
        super(arguments);
    }

    @Override
    public void execute(InputStream input, OutputStream output) throws IOException {
        Process process = Runtime.getRuntime().exec(getArguments().toArray(new String[0]));
        Scanner scanner = new Scanner(process.getInputStream());
        PrintWriter writer = new PrintWriter(output);
        while (scanner.hasNextLine()) {
            writer.println(scanner.nextLine());
        }
        writer.flush();
    }
}
