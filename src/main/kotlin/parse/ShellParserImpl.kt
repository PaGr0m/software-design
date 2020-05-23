package parse

import antlr.ShellLexer
import antlr.ShellVisitor
import executor.ShellContext
import antlr.ShellParser as Parser
import org.antlr.v4.runtime.CharStream
import org.antlr.v4.runtime.CharStreams
import org.antlr.v4.runtime.CommonTokenStream

class ShellParserImpl(private val shellContext: ShellContext): ShellParser {
    private val parser = Parser(null)
    override fun parse(content: String): ProgramAST? {
        val stream = CharStreams.fromString(content)
        return parse(stream)
    }

    private fun parse(stream: CharStream): ProgramAST? {
        val lexer = ShellLexer(stream)
        val tokens = CommonTokenStream(lexer)
        parser.inputStream = tokens
        val program = parser.program()
        check(parser.numberOfSyntaxErrors == 0) { "Syntax Errors" }

        val visitor: ShellVisitor<ProgramAST?> = ShellVisitorImpl(shellContext)
        return visitor.visitProgram(program)
    }
}
