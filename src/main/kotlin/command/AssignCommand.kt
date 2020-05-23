package command

import executor.ShellContext
import parse.Assign
import java.io.InputStream
import java.io.OutputStream

// Команда присваивания
class AssignCommand(private val assign: Assign, private val context: ShellContext) : ShellCommand {
    // Присваевает идентификатору новое значение. Обновляет контекст.
    override fun execute(inputStream: InputStream, outputStream: OutputStream) {
        context[assign.identifier.content] = assign.literal.content
    }
}
