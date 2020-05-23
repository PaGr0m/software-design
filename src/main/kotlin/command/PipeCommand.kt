package command

import java.io.InputStream
import java.io.OutputStream
import java.io.PipedInputStream
import java.io.PipedOutputStream

// Команда перенаправления входного и выходного потока
class PipeCommand(private val commands: List<ShellCommand>) : ShellCommand {
    // Перенаправляет выходной поток текущий команды во входной поток следующей.
    // Первая команда получает `inputStream` как входной поток
    // Последняя команда получает `outputStream` как выходной поток
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        if (commands.isEmpty())
            return

        var currentInputStream = inputStream
        for (offset in 0 until commands.size - 1) {
            val currentOutputStream = PipedOutputStream()
            val nextInputStream: InputStream = PipedInputStream(currentOutputStream)
            commands[offset].execute(currentInputStream, currentOutputStream)
            currentOutputStream.close()
            currentInputStream = nextInputStream
        }
        commands[commands.size - 1].execute(currentInputStream, outputStream)
        outputStream.flush()
    }
}
