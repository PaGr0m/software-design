package ru.ifmo.mit.repl.shell;


import ru.ifmo.mit.repl.io.IOController;
import ru.ifmo.mit.repl.parse.Lexer;
import ru.ifmo.mit.repl.parse.Parser;

import java.io.IOException;

public class ShellController implements Controller {
    private final static String PROMPT = "> ";

    private final Parser parser;
    private final Lexer lexer;
    private final IOController ioController;

    public ShellController(IOController ioController, Parser parser, Lexer lexer) {
        this.parser = parser;
        this.lexer = lexer;
        this.ioController = ioController;
    }

    @Override
    public void start() {
        ioController.print(PROMPT);
        var line = ioController.readLine();
        while (line.isPresent()) {
            var tokens = lexer.parse(line.get());
            try {
                var command = parser.parse(tokens);
                command.execute(ioController.getInputStream(), ioController.getOutputStream());
            } catch (Exception e) {
                ioController.println(e.getLocalizedMessage());
            }

            ioController.print(PROMPT);
            line = ioController.readLine();
        }
    }
}
