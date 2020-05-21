package ru.ifmo.mit.repl.parse.token;

/*
    Имплементация, предоставляющий атом строки шелла.
 */
public final class ShellToken implements Token {
    private final Token.Type tokenType;
    private final String string;

    public ShellToken(Type tokenType, String string) {
        this.tokenType = tokenType;
        this.string = string;
    }

    @Override
    public Type getTokenType() {
        return tokenType;
    }

    @Override
    public String getString() { return string; }

    @Override
    public String toString() {
        return "ShellToken{" +
                "tokenType=" + tokenType +
                ", string='" + string + '\'' +
                '}';
    }
}
