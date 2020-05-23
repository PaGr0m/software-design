package repl

import executor.ShellExecutor
import io.IOController
import parse.ShellError
import parse.ShellParser

class ReplImpl(
    private val ioController: IOController,
    private val executor: ShellExecutor,
    private val parser: ShellParser
) : Repl {

    override fun start() {
        ioController.print(PROMPT)
        var line = ioController.readLine() ?: return
        while (true) {
            try {
                val ast = parser.parse(line) ?: throw ShellError.Parse()
                executor.execute(ast)
            } catch (error: ShellError) {
                handle(error)
            } catch (error: Exception) {
                handle(error)
            }
            ioController.print("\n$PROMPT")
            line = ioController.readLine() ?: return
        }
    }

    private fun handle(error: ShellError) {
        print("Возникла ошибка: ${error.localizedMessage}")
    }
    private fun handle(error: Exception) {
        print("Возникла неожиданная ошибка: ${error.localizedMessage}")
    }

    companion object {
        const val PROMPT = "$ "
    }
}
