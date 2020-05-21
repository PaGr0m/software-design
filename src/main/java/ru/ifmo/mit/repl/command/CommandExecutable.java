package ru.ifmo.mit.repl.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/*
    Интерфейс, определяющий команду, которую можно вызывать
 */
public interface CommandExecutable {
    /*
        Абстрактный метод для запуска команды.
     */
    void execute(InputStream input, OutputStream output) throws IOException;
}
