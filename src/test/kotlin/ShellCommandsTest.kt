package ru.ifmo.mit.repl.parse

import command.*
import executor.ShellContext
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import parse.*
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.io.OutputStream


class ShellCommandsTest {
    private lateinit var context: ShellContext

    @BeforeEach
    fun setUp() {
        context = ShellContext()
    }

    @Test
    fun testAssignCommand() {
        var command = AssignCommand(Assign(Identifier("a"), Identifier("b")), context)
        command.execute(makeUselessInput(), makeUselessOutput())
        assertTrue(context["a"] != null)
        command = AssignCommand(Assign(Identifier("hello"), Identifier("world")), context)
        command.execute(makeUselessInput(), makeUselessOutput())
        assertTrue(context["hello"] != null)
    }

    @Test
    fun testCatCommand() {
        val command = CatCommand(Cat(null))
        val input = ByteArrayInputStream("hello world".toByteArray())
        val output = makeUselessOutput()
        command.execute(input, output)
        assertEquals("hello world\n", output.toString())
    }

    @Test
    fun testEchoCommand() {
        val command = EchoCommand(Echo(listOf(Identifier("hello"), Identifier("world"))))
        val input = ByteArrayInputStream("".toByteArray())
        val output = makeUselessOutput()
        command.execute(input, output)
        assertEquals("hello world", output.toString())
    }

    @Test
    fun testGrepPipeCommand() {
        val commandEcho = EchoCommand(Echo(listOf(Identifier("hello world\nmyself\ngilo   zpsh"))))
        val commandGrep = GrepCommand(Grep(listOf(Identifier("o\\s+"))))
        val command = PipeCommand(listOf(commandEcho, commandGrep))
        val input = ByteArrayInputStream("".toByteArray())
        val output = makeUselessOutput()
        command.execute(input, output)
        assertEquals("hello world\ngilo   zpsh\n", output.toString())
    }

    @Test
    fun testExitCommand() {
        val command = ExitCommand()
        val isStreamClosed = booleanArrayOf(false, false)
        val input: InputStream = object : InputStream() {
            override fun read(): Int {
                return 0
            }

            override fun close() {
                isStreamClosed[0] = true
                super.close()
            }
        }
        val output: OutputStream = object : OutputStream() {
            override fun write(b: Int) {
            }

            override fun close() {
                isStreamClosed[1] = true
                super.close()
            }
        }
        command.execute(input, output)
        assertTrue(isStreamClosed[0])
        assertTrue(isStreamClosed[1])
    }

    @Test
    fun testLsCommand() {
        val command = LsCommand(Ls(null), context)
        val input = ByteArrayInputStream("".toByteArray())
        val output = makeUselessOutput()
        command.execute(input, output)
        assertFalse(output.toString().isEmpty())
    }

    @Test
    fun testCdCommand() {
        val command = LsCommand(Ls(null), context)
        val input = ByteArrayInputStream("".toByteArray())
        val output = makeUselessOutput()
        command.execute(input, output)
        assertFalse(output.toString().isEmpty())
    }

    @Test
    fun testPWDCommand() {
        val command = PwdCommand(context)
        val input = ByteArrayInputStream("".toByteArray())
        val output = makeUselessOutput()
        command.execute(input, output)
        assertEquals(
            "${System.getProperty("user.dir")}\n".trimIndent(), output.toString()
        )
    }

    @Test
    fun testWCPipeCommand() {
        val commandEcho = EchoCommand(Echo(listOf(Identifier("hello world"))))
        val commandWC = WCCommand(WC(null))
        val command = PipeCommand(listOf(commandEcho, commandWC))
        val input = ByteArrayInputStream("".toByteArray())
        val output = makeUselessOutput()
        command.execute(input, output)
        assertEquals("1 2 11", output.toString())
    }

    private fun makeUselessInput(): InputStream {
        return ByteArrayInputStream(ByteArray(0))
    }

    private fun makeUselessOutput(): OutputStream {
        return ByteArrayOutputStream()
    }
}
