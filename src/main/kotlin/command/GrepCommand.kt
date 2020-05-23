package command

import com.github.ajalt.clikt.parameters.arguments.argument
import com.github.ajalt.clikt.parameters.arguments.optional
import com.github.ajalt.clikt.parameters.options.default
import com.github.ajalt.clikt.parameters.options.flag
import com.github.ajalt.clikt.parameters.options.option
import com.github.ajalt.clikt.parameters.types.int
import parse.Grep
import utils.NoReturnCliktCommand
import java.io.*
import java.util.*
import java.util.regex.Pattern.CASE_INSENSITIVE


private class GrepArguments(
    private val inputStream: InputStream,
    private val outputStream: OutputStream
) : NoReturnCliktCommand(name = "grep") {
    val isCaseInsensitive by option("-i", help = "Выполняет поиск без учета регистра.").flag()

    val isWholeWordSearch by option("-w", help = "Выполняет поиск только слов целиком.").flag()

    val amountToPrintAfter by option("-A", help = "Распечатывает NUMBER строк после строки с совпадением")
        .int()
        .default(0)

    val pattern by argument(help = "Регулярное выражение")

    val filePath by argument(help = "Путь до файла").optional()

    override fun run() {
        val input = filePath?.let { FileInputStream(File(it)) } ?: inputStream
        grep(input, outputStream)
    }

    private fun grep(input: InputStream, output: OutputStream) {
        val pattern = regex(this).toPattern(flags(this))
        val scanner = Scanner(input)
        val writer = PrintWriter(output)

        while (scanner.hasNextLine()) {
            val string = scanner.nextLine()
            val matcher = pattern.matcher(string)
            if (!matcher.find()) {
                continue
            }
            writer.println(string)
            var numberOfLinesToPrint: Int = amountToPrintAfter
            while (scanner.hasNextLine() && numberOfLinesToPrint > 0) {
                writer.println(scanner.nextLine())
                numberOfLinesToPrint--
            }
            writer.flush()
        }

        writer.flush()
    }


    companion object {
        private fun flags(argument: GrepArguments): Int {
            var flag = 0
            if (argument.isCaseInsensitive)
                flag = flag or CASE_INSENSITIVE
            return flag
        }

        private fun regex(argument: GrepArguments): String {
            var regex: String = argument.pattern
            if (argument.isWholeWordSearch)
                regex = "\\b$regex\\b"

            return regex
        }
    }
}

// Представляет команду поиск по образцу.
class GrepCommand(private val grep: Grep) : ShellCommand {
    // Выполняет команду поиска.
    // Выводит результат построчно в выходной поток
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        val command = GrepArguments(inputStream, outputStream)
        val arguments = grep.literals.map { it.content }
        command.execute(arguments)
    }
}
