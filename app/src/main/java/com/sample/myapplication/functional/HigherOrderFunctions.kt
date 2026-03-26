package com.sample.myapplication.functional

// ── Higher-Order Functions ─────────────────────────────────────────────────────
// Demonstrates: function types, lambdas, passing/returning functions,
//               anonymous functions, inline + reified, function references, closures

// Function type as parameter
fun operate(a: Int, b: Int, operation: (Int, Int) -> Int): Int = operation(a, b)

// Function that returns a function (closure)
fun multiplierOf(factor: Int): (Int) -> Int = { x -> x * factor }

// Nullable function type
fun applyIfNotNull(value: String?, transform: ((String) -> String)?): String? =
    transform?.let { value?.let(it) }

// Multiple function parameters — pipeline pattern
fun buildPipeline(
    input: String,
    preprocessor: (String) -> String = { it },
    processor: (String) -> String,
    postProcessor: (String) -> String = { it }
): String = postProcessor(processor(preprocessor(input)))

// inline — lambda is inlined at call-site; no Function object allocation
inline fun measureTime(label: String, block: () -> Unit): Long {
    val start = System.currentTimeMillis()
    block()
    val elapsed = System.currentTimeMillis() - start
    println("  $label → ${elapsed}ms")
    return elapsed
}

// inline + reified — access the actual type T at runtime without reflection hacks
inline fun <reified T> List<*>.filterIsType(): List<T> = filterIsInstance<T>()

// Standalone functions used as references
private fun isPositive(n: Int) = n > 0
private fun doubled(n: Int) = n * 2

// Closure — lambda captures and mutates outer variable
fun counter(start: Int = 0): () -> Int {
    var count = start
    return { count++ }
}

fun demoHigherOrderFunctions() {
    println("\n=== Higher-Order Functions ===")

    // Passing a lambda
    println("  operate +: ${operate(10, 3) { a, b -> a + b }}")
    println("  operate *: ${operate(10, 3) { a, b -> a * b }}")

    // Function reference with ::
    println("  operate ref: ${operate(10, 3, ::addInts)}")

    // Returning a function
    val triple = multiplierOf(3)
    println("  triple(7) = ${triple(7)}")

    // Nullable function type
    println("  applyIfNotNull: ${applyIfNotNull("hello", String::uppercase)}")
    println("  applyIfNotNull null value: ${applyIfNotNull(null, String::uppercase)}")
    println("  applyIfNotNull null fn: ${applyIfNotNull("hello", null)}")

    // Pipeline
    val result = buildPipeline(
        input = "  hello world  ",
        preprocessor = String::trim,
        processor = String::uppercase,
        postProcessor = { "[$it]" }
    )
    println("  pipeline: $result")

    // inline measureTime
    measureTime("list sum") {
        (1..100_000).toList().sum()
    }

    // Reified type filter
    val mixed: List<Any> = listOf(1, "two", 3, "four", 5.0, "six")
    println("  strings: ${mixed.filterIsType<String>()}")
    println("  ints:    ${mixed.filterIsType<Int>()}")

    // Function references
    val numbers = listOf(-3, -1, 0, 2, 4, 7)
    println("  filter+map: ${numbers.filter(::isPositive).map(::doubled)}")

    // Closure with mutable state
    val next = counter(10)
    println("  counter: ${next()} ${next()} ${next()}")
}

private fun addInts(a: Int, b: Int) = a + b
