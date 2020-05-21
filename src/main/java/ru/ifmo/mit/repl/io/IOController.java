package ru.ifmo.mit.repl.io;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Scanner;

/*
    Класс-обёртка, предоставляющий интерфейс IO.
 */
public final class IOController {
    private final Scanner scanner;
    private final InputStream stream;
    private final PrintStream printStream;

    public IOController(InputStream stream, PrintStream printStream) {
        this.stream = stream;
        this.scanner = new Scanner(stream);
        this.printStream = printStream;
    }

    public void print(String string) {
        printStream.print(string);
    }

    public void println(String string) { printStream.println(string); }

    public Optional<String> readLine() {
        try {
            return Optional.ofNullable(scanner.nextLine());
        } catch (NoSuchElementException e) {
            return Optional.empty();
        }
    }


    public InputStream getInputStream() {
        return stream;
    }

    public PrintStream getOutputStream() {
        return printStream;
    }
}
