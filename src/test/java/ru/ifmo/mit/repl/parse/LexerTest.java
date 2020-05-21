package ru.ifmo.mit.repl.parse;


import ru.ifmo.mit.repl.env.Context;
import ru.ifmo.mit.repl.env.ShellContext;
import ru.ifmo.mit.repl.parse.Lexer;
import ru.ifmo.mit.repl.parse.ShellLexer;
import ru.ifmo.mit.repl.parse.token.Token;
import org.junit.jupiter.api.*;

import java.util.List;

final class LexerTest {
    Lexer lexer;
    Context context;

    @BeforeEach
    void setUp() {
        context = new ShellContext();
        lexer = new ShellLexer(context);
    }

    @Test
    void lexerParseTestTokenTypes() {
        Assertions.assertArrayEquals(
                new Token.Type[]{Token.Type.EXPRESSION, Token.Type.ASSIGNMENT, Token.Type.EXPRESSION},
                getTokenType(lexer.parse("v=e"))
        );

        Assertions.assertArrayEquals(
                new Token.Type[]{Token.Type.EXPRESSION, Token.Type.ASSIGNMENT, Token.Type.EXPRESSION},
                getTokenType(lexer.parse("var   = expr   "))
        );
        Assertions.assertArrayEquals(
                new Token.Type[]{Token.Type.EXPRESSION, Token.Type.ASSIGNMENT, Token.Type.EXPRESSION, Token.Type.PIPE},
                getTokenType(lexer.parse("var  =  expr | "))
        );


        Assertions.assertArrayEquals(
                new Token.Type[]{
                        Token.Type.DOUBLE_QUOTE,
                        Token.Type.EXPRESSION,
                        Token.Type.ASSIGNMENT,
                        Token.Type.DOUBLE_QUOTE,
                        Token.Type.EXPRESSION,
                        Token.Type.PIPE
                },
                getTokenType(lexer.parse("\"var  =\"  expr | "))
        );
    }


    @Test
    void lexerParseTestStrings() {
        Assertions.assertArrayEquals(
                new String[]{"v", "=", "e"},
                getStrings(lexer.parse("v=e"))
        );

        Assertions.assertArrayEquals(
                new String[]{"var", "=", "expr"},
                getStrings(lexer.parse("var   = expr   "))
        );
        Assertions.assertArrayEquals(
                new String[]{"var", "=", "expr", "|"},
                getStrings(lexer.parse("var  =  expr | "))
        );


        Assertions.assertArrayEquals(
                new String[]{
                        "\"",
                        "var",
                        "=",
                        "\"",
                        "'",
                        "$expr",
                        "'",
                        "|"
                },
                getStrings(lexer.parse("\"var  =\"  '$expr' | "))
        );
    }


    private static Token.Type[] getTokenType(List<Token> tokens) {
        return tokens.stream().map(Token::getTokenType).toArray(Token.Type[]::new);
    }

    private static String[] getStrings(List<Token> tokens) {
        return tokens.stream().map(Token::getString).toArray(String[]::new);
    }
}