package ru.ifmo.mit.repl.parse;

import ru.ifmo.mit.repl.env.Context;
import ru.ifmo.mit.repl.parse.token.ShellToken;
import ru.ifmo.mit.repl.parse.token.Token;

import java.util.*;
import java.util.stream.Collectors;

public final class ShellLexer implements Lexer {
    private enum Separator {
        SINGLE_QUOTE('\''),

        DOUBLE_QUOTE('"'),

        ASSIGNMENT('='),

        SUBSTITUTION('$'),

        PIPE('|');

        private final char character;

        Separator(char character) {
            this.character = character;
        }

        public char getCharacter() {
            return character;
        }

        private static Optional<Separator> asSeparator(char character) {
            return Arrays.stream(Separator.values()).filter(s -> s.getCharacter() == character).findFirst();
        }
    }

    private static final Set<Character> SEPARATORS = new HashSet<>(
            Arrays.stream(Separator.values())
                    .map(Separator::getCharacter)
                    .collect(Collectors.toList())
    );

    final private Context context;

    public ShellLexer(Context context) {
        this.context = context;
    }

    @Override
    public List<Token> parse(String line) {
        List<Token> tokens = new ArrayList<>();

        for (int offset = 0; offset < line.length(); ) {
            char character = line.charAt(offset);
            if (Character.isWhitespace(character)) {
                offset++;
                continue;
            }

            var token = makeSeparatorTokenIfPossible(character);

            if (token.isPresent()) {
                tokens.add(token.get());
                offset++;
            } else {
                StringBuilder builder = new StringBuilder();
                while (!Character.isWhitespace(character) && !SEPARATORS.contains(character)) {
                    builder.append(character);
                    offset++;
                    if (offset >= line.length())
                        break;
                    character = line.charAt(offset);
                }
                tokens.add(new ShellToken(Token.Type.EXPRESSION, builder.toString()));
            }
        }

        return substituteTokens(tokens);
    }


    private List<Token> substituteTokens(List<Token> tokens) {
        List<Token> substitutedTokens = new ArrayList<>();

        boolean inSingleQuotes = false;
        boolean isLastTokenSubstitution = false;
        for (int offset = 0; offset < tokens.size(); offset++) {
            Token token = tokens.get(offset);
            if (token.getTokenType() == Token.Type.SINGLE_QUOTE) {
                inSingleQuotes = !inSingleQuotes;
                substitutedTokens.add(token);
            } else {
                if (token.getTokenType() == Token.Type.SUBSTITUTION) {
                    isLastTokenSubstitution = true;
                } else {
                    if (isLastTokenSubstitution) {
                        if (inSingleQuotes) {
                            var newToken = new ShellToken(
                                    Token.Type.EXPRESSION,
                                    tokens.get(offset - 1).getString() + token.getString());
                            substitutedTokens.add(newToken);
                        } else {
                            var value = context.getValue(token.getString());
                            if (value.isEmpty()) {
                                throw new IllegalArgumentException("Invalid variable name");
                            }

                            substitutedTokens.addAll(parse(value.get()));
                        }
                        isLastTokenSubstitution = false;
                    } else {
                        substitutedTokens.add(token);
                    }
                }
            }
        }

        return substitutedTokens;
    }

    private Optional<Token> makeSeparatorTokenIfPossible(char character) {
        var separatorOptional = Separator.asSeparator(character);
        return separatorOptional.map(s -> new ShellToken(asTokenType(s), String.valueOf(character)));
    }

    private static Token.Type asTokenType(Separator separator) {
        switch (separator) {
            case SINGLE_QUOTE:
                return Token.Type.SINGLE_QUOTE;
            case DOUBLE_QUOTE:
                return Token.Type.DOUBLE_QUOTE;
            case ASSIGNMENT:
                return Token.Type.ASSIGNMENT;
            case SUBSTITUTION:
                return Token.Type.SUBSTITUTION;
            case PIPE:
                return Token.Type.PIPE;
            default:
                throw new IllegalArgumentException("Unexpected argument" + separator);
        }

    }
}
