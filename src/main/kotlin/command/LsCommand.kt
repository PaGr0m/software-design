package command

import executor.ShellContext
import parse.Ls
import java.io.File
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter

// Команда `ls`
class LsCommand(private val ls: Ls, private val context: ShellContext) : ShellCommand {
    // Выдает список файлов директории. Принимает 0 или 1 аргумент
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        val input = ls.literal?.content ?: context.currentPath
        ls(input, outputStream)
    }

    private fun ls(arguments: String, output: OutputStream) {
        val writer = PrintWriter(output)

        val path = context.getPath(arguments)
        if (path != null) {
            File(path).list()?.toList()?.forEach(writer::println)
        } else {
            writer.println("No such file or directory!")
        }

        writer.flush()
    }
}
