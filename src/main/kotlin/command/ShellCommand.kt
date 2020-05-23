package command

import java.io.InputStream
import java.io.OutputStream

// Интерфейс команды
interface ShellCommand {
    // Запуск команды с данным входным и выходным потоком
    // Переопределяется в наследниках, конкретных командах
    fun execute(inputStream: InputStream, outputStream: OutputStream)
}
