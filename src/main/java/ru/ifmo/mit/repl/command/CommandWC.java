package ru.ifmo.mit.repl.command;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/*
    Класс, представляющий команду вывода количество строк, слов и байтов в строковом представлении аргументов.
    Аргументы: либо 1 аргумент-файл, либо 0 аргументов.
 */
public class CommandWC extends Command {
    public CommandWC(List<String> arguments) {
        super(arguments);
    }

    private void executeWithoutArgument(InputStream input, OutputStream output) throws IOException {
        Scanner scanner = new Scanner(input);
        int linesCount = 0;
        int wordsCount = 0;
        int bytesCount = input.available();
        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();
            linesCount++;
            wordsCount += Arrays.stream(line.split(" ")).filter(s -> !s.isEmpty()).count();
        }
        PrintWriter writer = new PrintWriter(output);
        writer.println(linesCount + " " + wordsCount + " " + bytesCount);
        writer.flush();
    }

    @Override
    public void execute(InputStream input, OutputStream output) throws IOException {
        if (getArguments().isEmpty()) {
            executeWithoutArgument(input, output);
        } else {
            for (var argument : getArguments()) {
                executeWithoutArgument(new FileInputStream(new File(argument)), output);
            }
        }
    }
}
