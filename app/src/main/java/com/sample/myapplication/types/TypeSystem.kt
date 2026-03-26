package com.sample.myapplication.types

// ── Type System: Smart Casts & Type Checks ─────────────────────────────────────
// Demonstrates: is/!is, smart cast, as (unsafe), as? (safe), Any/Unit/Nothing,
//               definitely non-nullable types T & Any (Kotlin 1.7+)

// Smart cast — after is-check the compiler narrows the type automatically
fun describe(obj: Any): String = when (obj) {
    is Int     -> "Int: $obj (×2 = ${obj * 2})"          // obj is smart-cast to Int
    is String  -> "String: \"$obj\" (len=${obj.length})"  // smart-cast to String
    is List<*> -> "List(size=${obj.size})"                 // smart-cast to List
    is Boolean -> if (obj) "true flag" else "false flag"
    else       -> "unknown: ${obj::class.simpleName}"
}

// Smart cast in an if-chain — compiler tracks the narrowed type
fun lengthOf(obj: Any): Int? {
    if (obj !is String) return null
    // Here obj is automatically String — no explicit cast
    return obj.length
}

// Unsafe cast — throws ClassCastException if incompatible
fun forceString(obj: Any): String = obj as String

// Safe cast — returns null instead of throwing
fun tryString(obj: Any): String? = obj as? String

// Any — root of Kotlin's non-null hierarchy (like Object in Java)
fun printAny(value: Any) = println("  Any: $value (${value::class.simpleName})")

// Unit — the type of functions with no meaningful return (like void)
fun sideEffect(): Unit = println("  Side effect executed")

// Nothing — type of expressions that never complete normally
fun fail(message: String): Nothing = throw IllegalStateException(message)

fun alwaysThrows(): Nothing = error("always throws")

// Definitely non-nullable (T & Any) — Kotlin 1.7+
// Useful in generics: T might be declared nullable (T?) but we guarantee non-null here
fun <T> requireNonNullValue(value: T & Any): T & Any = value

fun demoTypeSystem() {
    println("\n=== Type System ===")

    // Smart casts
    println("  --- smart cast ---")
    listOf(42, "hello", listOf(1, 2), true, 3.14).forEach {
        println("  ${describe(it)}")
    }

    // lengthOf with smart cast
    println("  lengthOf(\"Kotlin\") = ${lengthOf("Kotlin")}")
    println("  lengthOf(42)       = ${lengthOf(42)}")

    // Safe vs unsafe cast
    println("  --- cast ---")
    println("  tryString(\"hi\") = ${tryString("hi")}")
    println("  tryString(123)  = ${tryString(123)}")   // null, not exception
    try {
        forceString(99)
    } catch (e: ClassCastException) {
        println("  forceString(99) threw: ${e::class.simpleName}")
    }

    // Any
    println("  --- Any ---")
    printAny(1); printAny("text"); printAny(listOf(1, 2))

    // Unit
    println("  --- Unit ---")
    val u: Unit = sideEffect()
    println("  unit value = $u")

    // Nothing — fail() and alwaysThrows()
    println("  --- Nothing ---")
    try { fail("demo") } catch (e: IllegalStateException) { println("  caught: ${e.message}") }

    // Definitely non-nullable
    println("  --- T & Any ---")
    val result: String = requireNonNullValue("hello")
    println("  requireNonNullValue(\"hello\") = $result")
}
