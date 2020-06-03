package parse

// Представление строки шелла в программе. AST.
sealed class ProgramAST {
    // Функция, которая переводит команду в строку
    // Функция должна удовлетворять правилу
    // `parse(string).toString() == string`
    abstract override fun toString(): String
}

// Пустая программа
object EmptyProgram : ProgramAST() {
    override fun toString(): String = ""
}

// Базовый класс команды.
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

// Синтаксическая структура неименованного канала
class Pipe(val commands: List<Command>) : Command() {
    override fun toString(): String {
        return commands.joinToString(" | ") { it.toString() }
    }
}

// Синтаксическая структура команды вызода из шелла
object Exit : Command() {
    override fun toString(): String = "exit"
}

// Синтаксическая структура команды вывода текущей директории
object Pwd : Command() {
    override fun toString(): String = "pwd"
}

// Синтаксическая структура вывода содержимого файла
class Cat(val literal: Literal?) : Command() {
    override fun toString(): String = show("cat", literal?.toString())
}

// Синтаксическая структура подсчета количества строк, слов и байт в файле
class WC(val literal: Literal?) : Command() {
    override fun toString(): String = show("wc", literal?.toString())
}

// Синтаксическая структура вывода все своих аргуметов
class Echo(val literals: List<Literal>) : Command() {
    override fun toString(): String = show("echo", literals)
}

// Синтаксическая структура поиск по образцу
class Grep(val literals: List<Literal>) : Command() {
    override fun toString(): String = show("grep", literals)
}

class Cd(val literal: Literal?) : Command() {
    //    override fun toString(): String = show("cd", literal?.toString())
    override fun toString(): String = "cd"
}

class Ls(val literal: Literal?) : Command() {
    //    override fun toString(): String = show("ls", literal?.toString())
    override fun toString(): String = "ls"
}

// Синтаксическая структура создания и присвоения переменной некоторого значения
class Assign(val identifier: Identifier, val literal: Literal) : Command() {
    override fun toString(): String = "$identifier=$literal"
}

// Синтаксическая структура вызова внешней структуры
class External(val literals: List<Literal>) : Command() {
    override fun toString(): String = literals.joinToString(" ")
}

// Базовый класс строкового литерала(аргумента команды) в шелле.
abstract class Literal : ProgramAST() {
    // Контент литерала без лишних символов
    abstract val content: String
}

// Синтаксическая структура, означающая строку, обернутую в одночные кавычки(')
class FullQuoted(override val content: String) : Literal() {
    override fun toString(): String = "'${this.content}'"
}

// Синтаксическая структура, означающая строку, обернутую в двойные кавычки(")
class WeakQuoted(override val content: String) : Literal() {
    override fun toString(): String = "\"${this.content}\""
}

// Синтаксическая структура, означающая идентификатор (lvalue в assign)
data class Identifier(private val name: String) : Literal() {
    override val content: String get() = name

    override fun toString(): String = content
}

// Синтаксическая структура, означающая взятие значения у переменной ($identifier)
data class Variable(private val name: String) : Literal() {
    override val content: String get() = name

    override fun toString(): String = "$$content"
}
