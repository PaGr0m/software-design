package ru.ifmo.mit.repl.env;

import java.util.Optional;

/*
    Контекст, который представляет собой хранилище связанных переменных
 */
public interface Context {
    void add(String name, String value);

    Optional<String> getValue(String name);
}
