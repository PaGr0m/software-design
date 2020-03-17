package ru.ifmo.mit.repl.command;

import org.junit.jupiter.api.Test;
import ru.ifmo.mit.repl.env.ShellContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class CommandLsTest {
    private final InputStream inputStream = System.in;
    private final OutputStream outputStream = System.out;

    @Test
    void testExecuteLsOnCurrentDirectory() throws IOException {
        Command commandLs = new CommandLs(new ArrayList<>());
        commandLs.execute(inputStream, outputStream);
    }

    @Test
    void testExecuteLsOnDirectoryWithPath() throws IOException {
        String toPath = "src/test/java/ru/ifmo/mit/repl";
        Command commandLs = new CommandLs(Collections.singletonList(toPath));
        commandLs.execute(inputStream, outputStream);
    }

    @Test
    void testExecuteLsOnAnotherDirectory() throws IOException {
        String toPath = "/bin";
        Command commandLs = new CommandLs(Collections.singletonList(toPath));
        commandLs.execute(inputStream, outputStream);
    }

    @Test
    void testExecuteLsOnDirectoryNotExist() throws IOException {
        String toPath = "...";
        Command commandLs = new CommandLs(Collections.singletonList(toPath));
        commandLs.execute(inputStream, outputStream);
    }
}