package command

import executor.ShellContext
import parse.Cd
import java.io.InputStream
import java.io.OutputStream
import java.io.PrintWriter

class CdCommand(private val cd: Cd, private val context: ShellContext) : ShellCommand {
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        val input = cd.literal?.content ?: System.getProperty("user.dir")
        cd(input, outputStream)
    }

    private fun cd(arguments: String, output: OutputStream) {
        val writer = PrintWriter(output)

        val path = context.getPath(arguments)
        if (path != null) {
            context.currentPath = path
        } else {
            writer.println("No such file or directory!")
        }

        writer.flush()
    }
}
