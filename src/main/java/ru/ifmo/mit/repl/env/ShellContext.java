package ru.ifmo.mit.repl.env;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/*
    Имплементация контекста
 */
public class ShellContext implements Context {
    private Map<String, String> context = new HashMap<>();

    @Override
    public void add(String name, String value) {
        context.put(name, value);
    }

    @Override
    public Optional<String> getValue(String name) {
        return Optional.ofNullable(context.get(name));
    }
}
