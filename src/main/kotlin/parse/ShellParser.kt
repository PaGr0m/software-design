package parse

interface ShellParser {
    fun parse(content: String): ProgramAST?
}
