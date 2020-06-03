package parse

import antlr.ShellBaseVisitor
import antlr.ShellParser
import executor.ShellContext

/// Класс, переделывающий дерево разбора ANTLR в ProgramAST
/// Каждый visitor.+\b определяет перевод своей вершины ParseTree ANTLR в вершину `ProgramAST`
/// Перевод может не удаться, тогда вернется  `null`
class ShellVisitorImpl(private val context: ShellContext) : ShellBaseVisitor<ProgramAST?>() {
    override fun visitProgram(ctx: ShellParser.ProgramContext?): ProgramAST? {
        return visitShellCommand(ctx?.shellCommand())
    }

    override fun visitShellCommand(ctx: ShellParser.ShellCommandContext?): ProgramAST? {
        return ctx?.command()?.let(this::visitCommand)
            ?: ctx?.assignment()?.let(this::visitAssignment)
            ?: ctx?.pipe()?.let(this::visitPipe)
            ?: EmptyProgram
    }

    override fun visitPipe(ctx: ShellParser.PipeContext?): Pipe? {
        val literals = ctx?.command()?.mapNotNull { visitCommand(it) }
        return literals?.let {
            Pipe(it)
        }
    }

    override fun visitCommand(ctx: ShellParser.CommandContext?): Command? {
        return ctx?.cat()?.let(this::visitCat)
            ?: ctx?.echo()?.let(this::visitEcho)
            ?: ctx?.wc()?.let(this::visitWc)
            ?: ctx?.ls()?.let(this::visitLs)
            ?: ctx?.cd()?.let(this::visitCd)
            ?: ctx?.pwd()?.let(this::visitPwd)
            ?: ctx?.exit()?.let(this::visitExit)
            ?: ctx?.grep()?.let(this::visitGrep)
            ?: ctx?.external()?.let(this::visitExternal)
    }

    override fun visitPwd(ctx: ShellParser.PwdContext?): Pwd? = Pwd

    override fun visitExit(ctx: ShellParser.ExitContext?): Exit? = Exit

    override fun visitCat(ctx: ShellParser.CatContext?): Cat? {
        return ctx?.let {
            val literal = it.literal().let(this::visitLiteral)
            Cat(literal)
        }
    }

    override fun visitWc(ctx: ShellParser.WcContext?): WC? {
        return ctx?.let {
            val literal = it.literal().let(this::visitLiteral)
            WC(literal)
        }
    }

    override fun visitEcho(ctx: ShellParser.EchoContext?): Echo? {
        return ctx?.let {
            val literals = it.literal().mapNotNull(this::visitLiteral)
            Echo(literals)
        }
    }

    override fun visitGrep(ctx: ShellParser.GrepContext?): Grep? {
        return ctx?.let {
            val literals = it.literal().mapNotNull(this::visitLiteral)
            Grep(literals)
        }
    }

    override fun visitLs(ctx: ShellParser.LsContext?): Ls? {
        return ctx?.let {
            val literal = it.literal().let(this::visitLiteral)
            Ls(literal)
        }
    }

    override fun visitCd(ctx: ShellParser.CdContext?): Cd? {
        return ctx?.let {
            val literal = it.literal().let(this::visitLiteral)
            Cd(literal)
        }
    }

    override fun visitAssignment(ctx: ShellParser.AssignmentContext?): Assign? {
        return ctx?.let {
            val identifier = visitId(it.id()) ?: return@let null
            val literal = visitLiteral(it.literal()) ?: return@let null
            Assign(identifier, literal)
        }
    }

    override fun visitExternal(ctx: ShellParser.ExternalContext?): Command? {
        return ctx?.let {
            val literals = it.literal().mapNotNull(this::visitLiteral)
            External(literals)
        }
    }

    override fun visitLiteral(ctx: ShellParser.LiteralContext?): Literal? {
        return ctx?.fullQuoting().let(this::visitFullQuoting)
            ?: ctx?.weakQuoting().let(this::visitWeakQuoting)
            ?: ctx?.id().let(this::visitId)
            ?: ctx?.variable().let(this::visitVariable)
    }

    override fun visitFullQuoting(ctx: ShellParser.FullQuotingContext?): FullQuoted? {
        return ctx?.let {
            FullQuoted(it.FullQuotedString().text.drop(1).dropLast(1))
        }
    }

    override fun visitWeakQuoting(ctx: ShellParser.WeakQuotingContext?): WeakQuoted? {
        return ctx?.let {
            val inside = processSubstitute(it.WeakQuotedString().text.drop(1).dropLast(1))
            WeakQuoted(inside)
        }
    }

    override fun visitId(ctx: ShellParser.IdContext?): Identifier? {
        return ctx?.let {
            Identifier(it.ID().text)
        }
    }

    override fun visitVariable(ctx: ShellParser.VariableContext?): Variable? {
        return ctx?.let {
            val identifier = processSubstitute(it.VAR_ID().text)
            Variable(identifier)
        }
    }

    private fun processSubstitute(original: String): String {
        var string = ""
        var offset = 0
        while (offset < original.length) {
            when (val character = original[offset]) {
                '$' -> {
                    val regexMatches = IDENTIFIER_REGEX.find(original, offset + 1)
                    if (regexMatches == null || regexMatches.range.first != offset + 1)
                        throw ShellError.Identifier()
                    val identifier = regexMatches.value
                    val value = this.context[identifier] ?: throw ShellError.NotFoundID(identifier)
                    string += value
                    offset += identifier.length + 1
                }
                '\\' -> {
                    string += original.slice(offset..offset + 1)
                    offset += 2
                }
                else -> {
                    string += character
                    offset += 1
                }
            }

        }
        return string
    }

    companion object {
        private const val IDENTIFIER_PATTERN = "[_a-zA-Z][_a-zA-Z0-9]*"
        private val IDENTIFIER_REGEX = Regex(IDENTIFIER_PATTERN)
    }
}
