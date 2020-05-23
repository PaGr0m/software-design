package command

import parse.Echo
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter


// Команда `echo`.
class EchoCommand(private val echo: Echo) : ShellCommand {
    // Выводит аргументы в выходной поток
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        val writer = PrintWriter(outputStream)
        writer.print(echo.literals.joinToString(" ") { it.content })
        writer.flush()
    }
}
