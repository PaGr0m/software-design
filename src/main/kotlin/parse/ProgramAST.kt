package parse

/*
Представление строки шелла в программе. AST.
 */
sealed class ProgramAST

object EmptyProgram : ProgramAST() {
    override fun toString(): String = ""
}


sealed class Command : ProgramAST() {
    protected fun show(keyword: String, argument: Any?): String {
        if (argument != null)
            return "$keyword $argument"
        return keyword
    }

    protected fun show(keyword: String, arguments: List<Any>): String {
        if (arguments.isEmpty())
            return keyword
        return "$keyword ${arguments.joinToString(" ")}"
    }
}

class Pipe(val commands: List<Command>) : Command() {
    override fun toString(): String {
        return commands.joinToString(" | ") { it.toString() }
    }
}

object Exit : Command() {
    override fun toString(): String = "exit"
}

object Pwd : Command() {
    override fun toString(): String = "pwd"
}

class Cat(val literal: Literal?) : Command() {
    override fun toString(): String = show("cat", literal?.toString())
}

class WC(val literal: Literal?) : Command() {
    override fun toString(): String = show("wc", literal?.toString())
}

class Echo(val literals: List<Literal>) : Command() {
    override fun toString(): String = show("echo", literals)

}

class Grep(val literals: List<Literal>) : Command() {
    override fun toString(): String = show("grep", literals)
}

class Assign(val identifier: Identifier, val literal: Literal) : Command() {
    override fun toString(): String = "$identifier = $literal"
}

class External(val literals: List<Literal>) : Command() {
    override fun toString(): String = literals.joinToString(" ")
}


abstract class Literal : ProgramAST() {
    abstract val content: String
}

class FullQuoted(override val content: String) : Literal() {
    override fun toString(): String = "'${this.content}'"
}

class WeakQuoted(override val content: String) : Literal() {
    override fun toString(): String = "\"${this.content}\""
}

data class Identifier(private val name: String) : Literal() {
    override val content: String get() = name

    override fun toString(): String = content
}

data class Variable(private val name: String) : Literal() {
    override val content: String get() = name

    override fun toString(): String = "$$content"
}
