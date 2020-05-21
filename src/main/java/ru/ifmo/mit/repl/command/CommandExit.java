package ru.ifmo.mit.repl.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/*
    Класс, представляющий команду завершения работы шелла.
    Закрывает все  IO потоки.
 */
public class CommandExit extends Command {
    public CommandExit(List<String> arguments) {
        super(arguments);
    }

    @Override
    public void execute(InputStream input, OutputStream output) throws IOException {
        input.close();
        output.close();
    }
}
