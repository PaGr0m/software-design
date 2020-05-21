package ru.ifmo.mit.repl.parse;

import ru.ifmo.mit.repl.parse.token.Token;

import java.util.List;

/*
    Интерфейс класс, позволяющий разбивать данную строку на токены.
 */
public interface Lexer {
    /*
        Разбиение поданной на вход строки на токены
     */
    List<Token> parse(String line);
}
