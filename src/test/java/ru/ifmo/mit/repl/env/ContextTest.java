package ru.ifmo.mit.repl.env;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContextTest {
    private Context context;

    @BeforeEach
    void setUp() {
        context = new ShellContext();
    }

    @Test
    void testAssignNewVar() {
        assertTrue(context.getValue("x").isEmpty());
        context.add("x", "y");
        assertEquals("y", context.getValue("x").get());
        context.add("xx", "ab12");
        assertEquals("ab12", context.getValue("xx").get());
        assertTrue(context.getValue("qwerty").isEmpty());
    }


    @Test
    void testReassignVariable() {
        assertTrue(context.getValue("x").isEmpty());
        context.add("x", "y");
        assertEquals("y", context.getValue("x").get());
        context.add("x", "ab12");
        assertEquals("ab12", context.getValue("x").get());
    }
}