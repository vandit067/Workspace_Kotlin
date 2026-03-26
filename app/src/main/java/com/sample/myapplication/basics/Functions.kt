package com.sample.myapplication.basics

// ── Functions ──────────────────────────────────────────────────────────────────
// Demonstrates: regular, single-expression, Unit, default params, named args,
//               varargs, spread operator, infix, local functions, tailrec, Nothing

// Regular function with explicit return type
fun add(a: Int, b: Int): Int {
    return a + b
}

// Single-expression function — return type inferred
fun multiply(a: Int, b: Int) = a * b

// Unit return — analogous to void
fun logMessage(tag: String, message: String) {
    println("[$tag] $message")
}

// Default parameter values
fun greet(name: String, greeting: String = "Hello", punctuation: String = "!") =
    "$greeting, $name$punctuation"

// Named arguments — caller can supply in any order
fun createTag(text: String, bold: Boolean = false, italic: Boolean = false): String {
    var result = text
    if (italic) result = "<i>$result</i>"
    if (bold)   result = "<b>$result</b>"
    return result
}

// Varargs — variable number of same-type arguments
fun sumAll(vararg numbers: Int): Int = numbers.sum()

// Spread operator (*) — pass an existing array as varargs
fun joinWith(separator: String, vararg parts: String): String =
    parts.joinToString(separator)

// Infix function — member or extension, exactly one parameter, no default
infix fun Int.repeat(value: String): String = value.repeat(this)

// Local function — nested inside another, captures outer variables
fun processOrder(orderId: String, items: List<String>): String {
    fun validate(item: String) {
        require(item.isNotBlank()) { "Order $orderId: blank item not allowed" }
    }
    items.forEach { validate(it) }
    return "Order $orderId: ${items.size} items processed"
}

// Tail-recursive function — compiler eliminates stack frames (no StackOverflow)
tailrec fun factorial(n: Long, acc: Long = 1L): Long =
    if (n <= 1L) acc else factorial(n - 1L, n * acc)

// Nothing return type — function never completes normally (always throws)
fun fail(message: String): Nothing = throw IllegalStateException(message)

fun demoFunctions() {
    println("\n=== Functions ===")

    println(add(3, 5))
    println(multiply(4, 6))
    logMessage("APP", "started")

    // Default params
    println(greet("Alice"))
    println(greet("Bob", greeting = "Hi", punctuation = "."))

    // Named args — order independent
    println(createTag(bold = true, italic = true, text = "Kotlin"))

    // Varargs
    println("Sum: ${sumAll(1, 2, 3, 4, 5)}")
    val scores = intArrayOf(10, 20, 30)
    println("Sum from array: ${sumAll(*scores)}")   // spread

    // joinWith with spread
    val words = arrayOf("one", "two", "three")
    println(joinWith(separator = "-", parts = words))

    // Infix notation
    println(3 repeat "Kotlin! ")

    // Local function
    println(processOrder("ORD-001", listOf("book", "pen", "ruler")))

    // Tail recursion
    println("10! = ${factorial(10)}")
    println("20! = ${factorial(20)}")

    // Nothing — used for guaranteed-throw helpers
    try {
        fail("demo error")
    } catch (e: IllegalStateException) {
        println("Caught: ${e.message}")
    }
}
