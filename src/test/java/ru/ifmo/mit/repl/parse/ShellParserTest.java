package ru.ifmo.mit.repl.parse;

import ru.ifmo.mit.repl.command.*;
import ru.ifmo.mit.repl.env.Context;
import ru.ifmo.mit.repl.env.ShellContext;
import ru.ifmo.mit.repl.parse.Lexer;
import ru.ifmo.mit.repl.parse.Parser;
import ru.ifmo.mit.repl.parse.ShellLexer;
import ru.ifmo.mit.repl.parse.ShellParser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class ShellParserTest {
    Parser parser;
    Lexer lexer;
    Context context;

    @BeforeEach
    void setUp() {
        context = new ShellContext();
        context.add("expr", "some value");
        CommandFactory commandFactory = new CommandFactoryImpl(context);
        parser = new ShellParser(commandFactory);
        lexer = new ShellLexer(context);
    }

    @Test
    void parseAssign() {
        var command = (CommandPipe) parser.parse(lexer.parse("a=b"));
        var expectedCommand = new CommandPipe(List.of(new CommandAssign(Arrays.asList("a", "b"), context)));
        assertEquals(expectedCommand.getCommands().size(), command.getCommands().size());
        for (int i = 0; i < expectedCommand.getCommands().size(); i++) {
            assertEquals(expectedCommand.getCommands().get(i).toString(), command.getCommands().get(i).toString());
        }
    }


    @Test
    void parsePipeDoubleQuotes() {
        var command = parser.parse(lexer.parse("echo \"hello $expr\" | wc"));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        assertDoesNotThrow(() -> {
            command.execute(System.in, stream);
        });

        assertEquals("1 3 17\n", stream.toString());
    }


    @Test
    void parsePipeSingleQuotes() {
        var command = parser.parse(lexer.parse("echo 'hello $expr' | wc"));
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        assertDoesNotThrow(() -> {
            command.execute(System.in, stream);
        });

        assertEquals("1 2 12\n", stream.toString());
    }
}