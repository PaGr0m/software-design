package io

import java.io.*
import java.util.*

class IOControllerImpl(input: InputStream, output: OutputStream) : IOController {
    override val inputStream = input
    override val outputStream = PrintStream(output)

    override fun readLine(): String? {
        val scanner = Scanner(inputStream)
        if (scanner.hasNextLine())
            return scanner.nextLine()
        return null
    }

    override fun print(content: String) {
        outputStream.print(content)

    }

    override fun println(content: String) {
        outputStream.println(content)
    }

    override fun closeInput() {
        inputStream.close()
    }
}
