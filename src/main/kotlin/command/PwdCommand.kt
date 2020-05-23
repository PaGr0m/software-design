package command

import java.io.InputStream
import java.io.OutputStream

// Команда `pwd`
class PwdCommand : ShellCommand {
    // Выводит текущую директорию во выходной поток.
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        val path = System.getProperty("user.dir")
        outputStream.write(path.toByteArray())
        outputStream.flush()
    }
}
