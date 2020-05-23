package command

import java.io.InputStream
import java.io.OutputStream


// Команды выхода из шелла
class ExitCommand : ShellCommand {
    // Закрывает входной и выходной поток
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        inputStream.close()
        outputStream.close()
    }
}
