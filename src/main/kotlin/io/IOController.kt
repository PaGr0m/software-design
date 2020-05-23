package io

import java.io.InputStream
import java.io.OutputStream


/// Интерфейс взаимодействия с консолью(input/output стримы)
interface IOController {
    // Считать с входного потока
    fun readLine(): String?

    // Вывести в выходной поток
    fun print(content: String)

    // Вывести в выходной поток и поставить '\n'
    fun println(content: String)

    // Закрывает входной поток
    fun closeInput()


    val inputStream: InputStream
    val outputStream: OutputStream
}
