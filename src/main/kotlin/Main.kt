import command.CommandFactoryImpl
import executor.ShellContext
import executor.ShellExecutor
import executor.ShellExecutorImpl
import io.IOController
import io.IOControllerImpl
import parse.ShellParser
import parse.ShellParserImpl
import repl.Repl
import repl.ReplImpl

fun createRepl(ioController: IOController, executor: ShellExecutor, parser: ShellParser): Repl {
    return ReplImpl(ioController, executor, parser)
}

fun main() {
    val shellContext = ShellContext()
    val commandFactory = CommandFactoryImpl(shellContext)
    val ioController = IOControllerImpl(System.`in`, System.out)
    val executor = ShellExecutorImpl(ioController, commandFactory)
    val parser = ShellParserImpl(shellContext)
    val repl = createRepl(ioController, executor, parser)

    repl.start()
}
