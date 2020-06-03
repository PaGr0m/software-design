package command

import parse.*

/// Фабрика команд
abstract class CommandFactory {
    protected abstract fun makeAssignCommand(assign: Assign): ShellCommand
    protected abstract fun makeCatCommand(cat: Cat): ShellCommand
    protected abstract fun makeEchoCommand(echo: Echo): ShellCommand
    protected abstract fun makeExternalCommand(external: External): ShellCommand
    protected abstract fun makePipeCommand(pipe: Pipe): ShellCommand
    protected abstract fun makePwdCommand(pwd: Pwd): ShellCommand
    protected abstract fun makeWCCommand(wc: WC): ShellCommand
    protected abstract fun makeExitCommand(exit: Exit): ShellCommand
    protected abstract fun makeGrepCommand(grep: Grep): ShellCommand
    protected abstract fun makeCdCommand(cd: Cd): ShellCommand
    protected abstract fun makeLsCommand(ls: Ls): ShellCommand

    /// Функция-фабрика, которая создает необходимую команду шелла в зависимости от аргумента
    fun makeCommand(command: Command): ShellCommand {
        return when (command) {
            is Assign -> makeAssignCommand(command)
            is Cat -> makeCatCommand(command)
            Exit -> makeExitCommand(Exit)
            Pwd -> makePwdCommand(Pwd)
            is WC -> makeWCCommand(command)
            is Echo -> makeEchoCommand(command)
            is Grep -> makeGrepCommand(command)
            is Cd -> makeCdCommand(command)
            is Ls -> makeLsCommand(command)
            is External -> makeExternalCommand(command)
            is Pipe -> makePipeCommand(command)
        }
    }
}
