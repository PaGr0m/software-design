package ru.ifmo.mit.repl.command;

import java.io.*;
import java.util.List;
import java.util.Scanner;
import java.util.regex.*;

import picocli.CommandLine;
import picocli.CommandLine.*;

import static java.util.regex.Pattern.CASE_INSENSITIVE;

/*
    Класс, представляющий команду "grep".
 */
public class CommandGrep implements CommandExecutable {
    private class Argument {
        @Option(names = "-i", description = "Выполняет команду без учета регистра.")
        boolean isCaseInsensitive = false;


        @Option(names = "-w", description = "Выполняет поиск только слов целиком.")
        boolean isWholeWordSearch = false;

        @Option(names = "-A", paramLabel = "NUMBER", description = "Распечатывает NUMBER строк после строки с совпадением")
        int amountToPrintAfter = 0;

        @Parameters(index = "0", arity = "1", description = "Регулярное выражение")
        String regex;

        @Parameters(index = "1", arity = "0", description = "Путь до файла")
        String filePath;

        @Option(names = {"-h", "--help"}, usageHelp = true, description = "Показывает сообщение о помощи")
        boolean usageHelpRequested = false;
    }

    private final Argument arguments;

    public CommandGrep(List<String> cliArgs) {
        arguments = new Argument();
        new CommandLine(arguments).parseArgs(cliArgs.toArray(String[]::new));
    }

    @Override
    public void execute(InputStream input, OutputStream output) throws IOException {
        if (arguments.usageHelpRequested) {
            CommandLine.usage(new Argument(), System.out);
            return;
        }
        if (arguments.filePath == null) {
            executeFromStream(input, output);
        } else {
            executeFromStream(new FileInputStream(new File(arguments.filePath)), output);
        }
    }

    private void executeFromStream(InputStream input, OutputStream output) throws IOException {
        final var flags = getFlags(arguments);
        final var regex = getRegex(arguments);
        var pattern = Pattern.compile(regex, flags);
        Scanner scanner = new Scanner(input);
        PrintWriter writer = new PrintWriter(output);

        while (scanner.hasNextLine()) {
            var string = scanner.nextLine();
            var matcher = pattern.matcher(string);
            if (!matcher.find()) {
                continue;
            }
            writer.println(string);
            int numberOfLinesToPrint = arguments.amountToPrintAfter;
            while (scanner.hasNextLine() && numberOfLinesToPrint > 0) {
                writer.println(scanner.nextLine());
                numberOfLinesToPrint--;
            }
            writer.flush();
        }

        writer.flush();
    }


    private int getFlags(Argument argument) {
        int flag = 0;
        if (argument.isCaseInsensitive)
            flag |= CASE_INSENSITIVE;
        return flag;
    }

    private String getRegex(Argument argument) {
        String regex = argument.regex;
        if (argument.isWholeWordSearch) {
            regex = "\\b" + regex + "\\b";
        }

        return regex;
    }
}
