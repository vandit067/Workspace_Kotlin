package com.sample.myapplication.collections

// ── Sequences ──────────────────────────────────────────────────────────────────
// Demonstrates: lazy evaluation vs eager collections, generateSequence,
//               sequence builder (yield/yieldAll), constrainOnce

fun demoSequences() {
    println("\n=== Sequences ===")

    // ── Eager List: each step materialises a full intermediate list ────────────
    println("  --- eager (List) ---")
    val eagerResult = (1..10)
        .toList()
        .filter  { print("  filter $it | "); it % 2 == 0 }
        .map     { print("map $it | ");  it * it }
        .first()
    println("\n  eager first = $eagerResult")

    // ── Lazy Sequence: elements processed one-by-one; stops early ────────────
    println("  --- lazy (Sequence) ---")
    val lazyResult = (1..10)
        .asSequence()
        .filter  { print("  filter $it | "); it % 2 == 0 }
        .map     { print("map $it | ");  it * it }
        .first()
    println("\n  lazy first = $lazyResult")

    // ── generateSequence ───────────────────────────────────────────────────────
    println("  --- generateSequence ---")
    // Infinite sequence — take() makes it finite
    val powersOf2 = generateSequence(1) { it * 2 }
    println("  powers of 2: ${powersOf2.take(10).toList()}")

    // State via Pair — Fibonacci
    val fibonacci = generateSequence(0 to 1) { (a, b) -> b to a + b }
        .map { it.first }
        .take(15)
        .toList()
    println("  fibonacci:   $fibonacci")

    // Finite — return null to stop
    var tick = 0
    val finite = generateSequence { if (tick < 5) tick++ else null }
    println("  finite:      ${finite.toList()}")

    // ── sequence builder (yield / yieldAll) ────────────────────────────────────
    println("  --- sequence builder ---")
    val custom = sequence {
        yield(0)
        yieldAll(1..5)
        yield(99)
        yieldAll(listOf(100, 200, 300))
    }
    println("  custom: ${custom.toList()}")

    // ── Practical: lazy processing to avoid large intermediate lists ──────────
    println("  --- lazy processing ---")
    val processed = generateSequence(1) { if (it < 20) it + 1 else null }
        .filter { it % 3 == 0 }
        .map { it * it }
        .toList()
    println("  multiples of 3 squared (1..20): $processed")

    // ── constrainOnce — single-iteration guard ────────────────────────────────
    println("  --- constrainOnce ---")
    val once = sequenceOf(1, 2, 3).constrainOnce()
    println("  first pass: ${once.toList()}")
    // once.toList() here would throw IllegalStateException — sequence already consumed
}
