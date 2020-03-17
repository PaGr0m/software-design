package ru.ifmo.mit.repl.command;

import org.junit.jupiter.api.Test;
import ru.ifmo.mit.repl.env.ShellContext;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CommandCdTest {
    private final InputStream inputStream = System.in;
    private final OutputStream outputStream = System.out;

    @Test
    void testExecuteCdOnDefaultPath() throws IOException {
        // Arrange
        Command commandCd = new CommandCd(new ArrayList<>());

        // Act
        commandCd.execute(inputStream, outputStream);
        String newPath = ShellContext.getCurrentPath();

        // Assert
        assertEquals(System.getProperty("user.dir"), newPath);
    }

    @Test
    void testExecuteCdOnSrc() throws IOException {
        // Arrange
        String toPath = "src/test";
        String oldPath = ShellContext.getCurrentPath();
        Command commandCd = new CommandCd(Collections.singletonList(toPath));

        // Act
        commandCd.execute(inputStream, outputStream);
        String newPath = ShellContext.getCurrentPath();

        // Assert
        assertEquals(Paths.get(oldPath, toPath).toString(),
                     newPath);
    }

    @Test
    void testExecuteDirectoryIsNotExist() throws IOException {
        // Arrange
        String toPath = "Hello World!";
        String oldPath = ShellContext.getCurrentPath();
        Command commandCd = new CommandCd(Collections.singletonList(toPath));

        // Act
        commandCd.execute(inputStream, outputStream);
        String newPath = ShellContext.getCurrentPath();

        // Assert
        assertEquals(oldPath, newPath);
    }
}