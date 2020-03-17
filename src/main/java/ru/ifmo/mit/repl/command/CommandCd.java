package ru.ifmo.mit.repl.command;

import ru.ifmo.mit.repl.env.ShellContext;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class CommandCd extends Command {
    public CommandCd(List<String> arguments) {
        super(arguments);
    }

    @Override
    public void execute(InputStream input, OutputStream output) {
        if (getArguments().isEmpty()) {
            executeWithoutArgument(input, output);
        } else {
            executeWithArgument(input, output);
        }
    }

    private void executeWithoutArgument(InputStream input, OutputStream output) {
        ShellContext.setCurrentPath(System.getProperty("user.dir"));
    }

    private void executeWithArgument(InputStream input, OutputStream output) {
        PrintWriter writer = new PrintWriter(output);
        String path = String.join(" ", getArguments());

        String pathToDirectory;
        if (Files.isDirectory(Paths.get(ShellContext.getCurrentPath(), path))) {
            pathToDirectory = Paths.get(ShellContext.getCurrentPath(), path).toString();
        } else if (Files.isDirectory(Paths.get(path))) {
            pathToDirectory = Paths.get(path).toString();
        } else {
            writer.println("No such file or directory!");
            writer.flush();
            return;
        }

        ShellContext.setCurrentPath(pathToDirectory);
    }

    private List<String> getFilesInDirectory(String pathToDirectory) {
        return Arrays.asList(Objects.requireNonNull(new File(pathToDirectory).list()));
    }
}
