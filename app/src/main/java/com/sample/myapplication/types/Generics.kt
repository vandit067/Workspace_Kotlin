package com.sample.myapplication.types

// ── Generics ───────────────────────────────────────────────────────────────────
// Demonstrates: generic classes, upper bounds, where clause, variance (out/in),
//               star projection, inline + reified type parameters

// Generic class — type parameter T
class Box<T>(val value: T) {
    fun peek(): T = value
    override fun toString() = "Box($value)"
}

// Generic function
fun <T> swap(pair: Pair<T, T>): Pair<T, T> = pair.second to pair.first

// Upper bound — T must be Comparable
fun <T : Comparable<T>> clamp(value: T, min: T, max: T): T = when {
    value < min -> min
    value > max -> max
    else        -> value
}

// Multiple bounds — where clause
fun <T> printSorted(value: T) where T : Any, T : Comparable<T> =
    println("  sorted printout: $value")

// ── Variance ───────────────────────────────────────────────────────────────────

// out (covariant) — Box<Dog> is assignable to Box<Animal>; can only produce T
class ReadBox<out T>(private val item: T) {
    fun get(): T = item
}

// in (contravariant) — Sink<Animal> is assignable to Sink<Dog>; can only consume T
class Sink<in T> {
    fun receive(item: T) = println("  Sink received: $item")
}

// Star projection (like Java wildcard <?>) — type is unknown at use-site
fun printBox(box: Box<*>) = println("  Box<*>: ${box.peek()}")

// ── Reified type parameters ────────────────────────────────────────────────────
// inline + reified: access T's class at runtime (no Class<T> param needed)
inline fun <reified T> isA(value: Any): Boolean = value is T

inline fun <reified T> safeCastTo(value: Any): T? = value as? T

inline fun <reified T> List<*>.keepOnly(): List<T> = filterIsInstance<T>()

fun demoGenerics() {
    println("\n=== Generics ===")

    // Generic class
    println(Box(42))
    println(Box("Kotlin"))

    // Generic function
    println("  swap: ${swap(1 to 2)}")
    println("  swap: ${swap("a" to "b")}")

    // Upper bound
    println("  clamp(15, 0..10) = ${clamp(15, 0, 10)}")
    println("  clamp(-5, 0..10) = ${clamp(-5, 0, 10)}")
    println("  clamp( 7, 0..10) = ${clamp(7, 0, 10)}")

    // where clause
    printSorted("hello")

    // out variance: ReadBox<String> → ReadBox<Any> (covariant)
    val strBox: ReadBox<String> = ReadBox("hello")
    val anyBox: ReadBox<Any> = strBox
    println("  covariant get: ${anyBox.get()}")

    // in variance: Sink<Any> → Sink<String> (contravariant)
    val anySink = Sink<Any>()
    val strSink: Sink<String> = anySink
    strSink.receive("message via contravariant Sink")

    // Star projection
    printBox(Box(99))
    printBox(Box("hi"))

    // Reified
    println("  isA<String>(\"hi\") = ${isA<String>("hi")}")
    println("  isA<Int>(\"hi\")    = ${isA<Int>("hi")}")
    println("  safeCastTo<Int>(42) = ${safeCastTo<Int>(42)}")
    println("  safeCastTo<Int>(\"x\")= ${safeCastTo<Int>("x")}")

    val mixed: List<Any> = listOf(1, "two", 3, "four", 5.0)
    println("  keepOnly<String>: ${mixed.keepOnly<String>()}")
    println("  keepOnly<Int>:    ${mixed.keepOnly<Int>()}")
}
