package ru.ifmo.mit.repl.parse

import executor.ShellContext
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import parse.ProgramAST
import parse.ShellError
import parse.ShellParser
import parse.ShellParserImpl
import java.io.ByteArrayOutputStream
import java.lang.IllegalStateException

internal class ShellParserTest {
    private lateinit var parser: ShellParser
    private lateinit var shellContext: ShellContext

    @BeforeEach
    fun setUp() {
        shellContext = ShellContext()
        parser = ShellParserImpl(shellContext)
    }

    @Test
    fun parseAssign() {
        addDummyContext()
        val program = parseStringNonNull("a=b")
        assertEquals("a=b", program.toString())
    }

    @Test
    fun parsePipeDoubleQuotes() {
        assertThrows<ShellError.NotFoundID> {
            parseStringNonNull("echo \"hello \$expr\" | wc")
        }
        addDummyContext()
        val program = parseStringNonNull("echo \"hello   \$_id_\" | wc")
        assertEquals("echo \"hello   hello\" | wc", program.toString())
    }

    @Test
    fun parsePipeSingleQuotes() {
        val program = parseStringNonNull("echo 'hello \$expr' | wc")
        assertEquals("echo 'hello \$expr' | wc", program.toString())
    }

    private fun parseStringNonNull(string: String): ProgramAST {
        val program = parser.parse(string)
        if (program != null)
            return program
        assertTrue(false)
        throw IllegalStateException()
    }

    private fun addDummyContext() {
        val dummy = mutableMapOf(
            "a" to "1",
            "b" to "123",
            "_id_" to "hello",
        "expr" to "world"
        )
        shellContext.putAll(dummy)
    }

}
