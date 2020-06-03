package command

import executor.ShellContext
import java.io.InputStream
import java.io.OutputStream

// Команда `pwd`
class PwdCommand(private val context: ShellContext) : ShellCommand {
    // Выводит текущую директорию во выходной поток.
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        val path = context.currentPath
        outputStream.write(path.toByteArray())
        outputStream.flush()
    }
}
