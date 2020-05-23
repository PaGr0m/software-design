package command

import parse.WC
import java.io.*
import java.util.*


// Команда `wc`. Принимает 0 или 1 аргумент
class WCCommand(private val wc: WC) : ShellCommand {
    // Запускает посчет строк, слов и байт во входном потоке.
    // Выводит в формате 'КОЛ_ВО_СТРОК КОЛ_ВО_СЛОВ КОЛ_ВО_БАЙТ'
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        val input = wc.literal?.let { FileInputStream(File(it.content)) } ?: inputStream
        wc(input, outputStream)
    }

    // Построчно считывает все данные из входного потока, попутно считая строк, слов и байт
    // В каждый момент работы использует O(max длина строки) дополнительной памяти
    private fun wc(input: InputStream, output: OutputStream) {
        val scanner = Scanner(input)
        var linesCount: Long = 0
        var wordsCount: Long = 0
        val bytesCount = input.available()

        while (scanner.hasNextLine()) {
            val line: String = scanner.nextLine()
            linesCount++
            wordsCount += Arrays.stream(line.split(" ".toRegex()).toTypedArray())
                .filter { s: String -> s.isNotEmpty() }
                .count()
        }
        val writer = PrintWriter(output)
        writer.print("$linesCount $wordsCount $bytesCount")
        writer.flush()
    }
}
