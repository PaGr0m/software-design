package command

import parse.Cat
import java.io.*
import java.util.*

// Команда `cat`
class CatCommand(private val cat: Cat) : ShellCommand {
    // Выводит содержимое из потока ввода. Принимает 0 или 1 аргумент
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        val input = cat.literal?.let { FileInputStream(File(it.content)) } ?: inputStream
        cat(input, outputStream)
    }

    private fun cat(input: InputStream, output: OutputStream) {
        val scanner = Scanner(input)
        val writer = PrintWriter(output)
        while (scanner.hasNextLine()) {
            val line: String = scanner.nextLine()
            writer.println(line)
        }
        writer.flush()
    }
}
