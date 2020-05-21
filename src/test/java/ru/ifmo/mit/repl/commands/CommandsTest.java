package ru.ifmo.mit.repl.commands;

import org.junit.jupiter.api.*;
import ru.ifmo.mit.repl.command.*;
import ru.ifmo.mit.repl.env.*;

import java.io.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CommandsTest {
    Context context;

    @BeforeEach
    public void setUp() {
        context = new ShellContext();

    }


    @Test
    public void testAssignCommand() throws IOException {
        var command = new CommandAssign(List.of("a", "b"), context);
        command.execute(makeUselessInput(), makeUselessOutput());
        assertTrue(context.getValue("a").isPresent());


        command = new CommandAssign(List.of("hello", "world"), context);
        command.execute(makeUselessInput(), makeUselessOutput());
        assertTrue(context.getValue("hello").isPresent());
    }


    @Test
    public void testCatCommand() throws IOException {
        var command = new CommandCat(List.of());
        var input = new ByteArrayInputStream("hello world".getBytes());
        var output = makeUselessOutput();
        command.execute(input, output);
        assertEquals("hello world\n", output.toString());
    }

    @Test
    public void testEchoCommand() throws IOException {
        var command = new CommandEcho(List.of("hello world"));
        var input = new ByteArrayInputStream("".getBytes());
        var output = makeUselessOutput();
        command.execute(input, output);
        assertEquals("hello world\n", output.toString());
    }


    @Test
    public void testGrepPipeCommand() throws IOException {
        var commandEcho = new CommandEcho(List.of("hello world\nmyself\ngilo   zpsh"));
        var commandGrep = new CommandGrep(List.of("o\\s+"));
        var command = new CommandPipe(List.of(commandEcho, commandGrep));

        var input = new ByteArrayInputStream("".getBytes());
        var output = makeUselessOutput();

        command.execute(input, output);
        assertEquals("hello world\ngilo   zpsh\n", output.toString());

    }

    @Test
    public void testExitCommand() throws IOException {
        var command = new CommandExit(List.of());

        final boolean[] isStreamClosed = {false, false};
        InputStream input = new InputStream() {
            @Override
            public int read() throws IOException {
                return 0;
            }

            @Override
            public void close() throws IOException {
                isStreamClosed[0] = true;
                super.close();
            }
        };

        OutputStream output = new OutputStream() {
            @Override
            public void write(int b) throws IOException {
            }

            @Override
            public void close() throws IOException {
                isStreamClosed[1] = true;
                super.close();
            }
        };
        command.execute(input, output);
        assertTrue(isStreamClosed[0]);
        assertTrue(isStreamClosed[1]);
    }

    @Test
    public void testLsCommand() throws IOException {
        var command = new CommandExternal(List.of("ls", "-la"));
        var input = new ByteArrayInputStream("".getBytes());
        var output = makeUselessOutput();
        command.execute(input, output);
        assertFalse(output.toString().isEmpty());
    }


    @Test
    public void testPWDCommand() throws IOException {
        var command = new CommandPWD(List.of());
        var input = new ByteArrayInputStream("".getBytes());
        var output = makeUselessOutput();
        command.execute(input, output);
        assertEquals(System.getProperty("user.dir") + "\n", output.toString());
    }

    @Test
    public void testWCPipeCommand() throws IOException {

        var commandEcho = new CommandEcho(List.of("hello world"));
        var commandWC = new CommandWC(List.of());
        var command = new CommandPipe(List.of(commandEcho, commandWC));

        var input = new ByteArrayInputStream("".getBytes());
        var output = makeUselessOutput();

        command.execute(input, output);
        assertEquals("1 2 12\n", output.toString());

    }

    private InputStream makeUselessInput() {
        return new ByteArrayInputStream(new byte[0]);
    }


    private OutputStream makeUselessOutput() {
        return new ByteArrayOutputStream();
    }
}
