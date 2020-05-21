package ru.ifmo.mit.repl;

import ru.ifmo.mit.repl.command.CommandFactory;
import ru.ifmo.mit.repl.command.CommandFactoryImpl;
import ru.ifmo.mit.repl.env.Context;
import ru.ifmo.mit.repl.env.ShellContext;
import ru.ifmo.mit.repl.io.IOController;
import ru.ifmo.mit.repl.parse.Lexer;
import ru.ifmo.mit.repl.parse.Parser;
import ru.ifmo.mit.repl.parse.ShellLexer;
import ru.ifmo.mit.repl.parse.ShellParser;
import ru.ifmo.mit.repl.shell.Controller;
import ru.ifmo.mit.repl.shell.ShellController;

public final class Repl {
    public static void main(String[] args) {
        Context context = new ShellContext();
        CommandFactory commandFactory = new CommandFactoryImpl(context);
        Parser parser = new ShellParser(commandFactory);
        Lexer lexer = new ShellLexer(context);
        var input = System.in;
        var printStream = System.out;
        IOController ioController = new IOController(input, printStream);
        Controller controller = new ShellController(ioController, parser, lexer);

        controller.start();
    }
}
