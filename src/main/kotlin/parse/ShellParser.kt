package parse

// Интерфейс для перевода строки в AST.
interface ShellParser {
    // Перевод строку в AST.
    fun parse(content: String): ProgramAST?
}
