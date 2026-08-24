package io.github.meko123456.chonchkhi.core

/**
 * A tiny sample of pure, unit-testable logic living in the `:core` library module —
 * the kind of code a real app keeps out of the UI layer. Replace with your own.
 */
object Greeting {
    fun greet(name: String): String {
        val who = name.trim().ifEmpty { "world" }
        return "Hello, $who!"
    }
}
