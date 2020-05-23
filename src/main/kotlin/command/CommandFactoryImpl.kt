package command

import executor.ShellContext
import parse.*

class CommandFactoryImpl(private val context: ShellContext) : CommandFactory() {
    override fun makeAssignCommand(assign: Assign): ShellCommand = AssignCommand(assign, context)

    override fun makeCatCommand(cat: Cat): ShellCommand = CatCommand(cat)

    override fun makeEchoCommand(echo: Echo): ShellCommand = EchoCommand(echo)

    override fun makeExternalCommand(external: External): ShellCommand = ExternalCommand(external)

    override fun makePipeCommand(pipe: Pipe): ShellCommand = PipeCommand(pipe.commands.map(this::makeCommand))

    override fun makePwdCommand(pwd: Pwd): ShellCommand = PwdCommand()

    override fun makeWCCommand(wc: WC): ShellCommand = WCCommand(wc)

    override fun makeExitCommand(exit: Exit): ShellCommand = ExitCommand()

    override fun makeGrepCommand(grep: Grep): ShellCommand = GrepCommand(grep)
}
