package ru.ifmo.mit.repl.parse.token;

/*
    Интерфейс, предоставляющий атом строки шелла.
 */
public interface Token {

    enum Type {
        SINGLE_QUOTE,
        DOUBLE_QUOTE,
        ASSIGNMENT,
        SUBSTITUTION,
        PIPE,
        EXPRESSION
    }

    Type getTokenType();

    String getString();
}
