package main.java.ru.ifmo.mit.repl.command;

import java.io.*;
import java.util.List;
import java.util.Scanner;

/*
    Класс, представляющий команду вывода содержимого из потока ввода.
 */
public class CommandCat extends Command {
    public CommandCat(List<String> arguments) {
        super(arguments);
    }


    private void executeWithoutArguments(InputStream input, OutputStream output) {
        Scanner scanner = new Scanner(input);
        PrintWriter writer = new PrintWriter(output, true);
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            writer.println(line);
        }
    }

    @Override
    public void execute(InputStream input, OutputStream output) throws FileNotFoundException {
        if (getArguments().isEmpty()) {
            executeWithoutArguments(input, output);
        } else {
            for (var argument : getArguments()) {
                executeWithoutArguments(new FileInputStream(new File(argument)), output);
            }
        }
    }
}
