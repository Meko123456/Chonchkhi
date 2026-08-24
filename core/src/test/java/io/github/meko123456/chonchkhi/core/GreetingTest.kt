package io.github.meko123456.chonchkhi.core

import org.junit.Assert.assertEquals
import org.junit.Test

class GreetingTest {
    @Test
    fun greetsAName() {
        assertEquals("Hello, Merab!", Greeting.greet("Merab"))
    }

    @Test
    fun blankFallsBackToWorld() {
        assertEquals("Hello, world!", Greeting.greet("   "))
    }
}
