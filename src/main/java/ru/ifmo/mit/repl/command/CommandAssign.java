package ru.ifmo.mit.repl.command;

import ru.ifmo.mit.repl.env.Context;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

/*
    Класс, представляющий команду связывания первого и второго аргумента.
 */
public class CommandAssign extends Command {
    private final Context context;

    public CommandAssign(List<String> arguments, Context context) {
        super(arguments);
        this.context = context;
    }

    @Override
    public void execute(InputStream input, OutputStream output) throws IOException {
        var lvalue = getArguments().get(0);
        var rvalue = getArguments().get(1);
        context.add(lvalue, rvalue);
    }
}
