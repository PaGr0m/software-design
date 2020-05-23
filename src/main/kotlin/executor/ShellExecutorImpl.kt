package executor

import command.CommandFactory
import io.IOController
import parse.*

// Класс, который конструирует аргументы комманды
class ShellExecutorImpl(
    private val ioController: IOController,
    private val factory: CommandFactory
) : ShellExecutor {

    // Исполняет данное AST, запуская команды
    override fun execute(program: ProgramAST) {
        when (program) {
            is Command -> {
                val command = factory.makeCommand(program)
                command.execute(ioController.inputStream, ioController.outputStream)
            }
            is Literal -> throw ShellError.NotFoundCommand(program.toString())
            EmptyProgram -> {
            }
        }
    }
}
