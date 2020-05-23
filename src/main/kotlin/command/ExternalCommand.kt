package command

import parse.External
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter
import java.util.*

// Команда для запуска неизвестной команды
class ExternalCommand(private val external: External) : ShellCommand {
    // Вызывает внешнюю команду с данными аргументами
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        val arguments = external.literals.map { it.content }.toTypedArray()
        val process: Process = Runtime.getRuntime().exec(arguments)
        val scanner = Scanner(process.inputStream)
        val writer = PrintWriter(outputStream)
        while (scanner.hasNextLine()) {
            writer.println(scanner.nextLine())
        }
        writer.flush()
    }
}
