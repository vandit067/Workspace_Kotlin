package com.sample.myapplication.collections

// ── Collections, Ranges & Loops ────────────────────────────────────────────────
// Demonstrates: ranges, for/while/do-while, labelled break/continue,
//               when expression, List/Map/Set operations, Enum.entries (Kotlin 1.9+)

// ── Ranges ─────────────────────────────────────────────────────────────────────
fun demoRanges() {
    println("  --- Ranges ---")
    print("  1..5:          "); for (i in 1..5) print("$i "); println()
    print("  0 until 5:     "); for (i in 0 until 5) print("$i "); println()
    print("  10 downTo 0 s2:"); for (i in 10 downTo 0 step 2) print("$i "); println()
    print("  'a'..'e':      "); for (c in 'a'..'e') print(c); println()

    val n = 7
    println("  $n in 1..10 → ${n in 1..10}")
    println("  $n !in 1..5 → ${n !in 1..5}")
}

// ── Loops ──────────────────────────────────────────────────────────────────────
fun demoLoops() {
    println("  --- Loops ---")
    val platforms = listOf("Android", "iOS", "Web", "Desktop")

    // for-in with withIndex()
    for ((i, v) in platforms.withIndex()) print("  [$i]$v ")
    println()

    // while
    var i = 0
    while (i < platforms.size) { print("  ${platforms[i++]} ") }
    println()

    // do-while — body executes at least once
    var j = platforms.size - 1
    do { print("  ${platforms[j--]} ") } while (j >= 0)
    println()

    // Labelled break/continue
    print("  labelled: ")
    outer@ for (x in 1..4) {
        for (y in 1..4) {
            if (y == 3) continue@outer
            print("($x,$y) ")
        }
    }
    println()
}

// ── When expression ────────────────────────────────────────────────────────────
fun demoWhen() {
    println("  --- When ---")

    // when as expression — returns a value
    fun classify(n: Int) = when {
        n < 0  -> "negative"
        n == 0 -> "zero"
        n < 10 -> "single-digit"
        else   -> "multi-digit"
    }
    (-2..12 step 3).forEach { print("  $it→${classify(it)} ") }
    println()

    // when with type check — smart cast inside branch
    fun typeOf(obj: Any) = when (obj) {
        is Int     -> "Int=${obj * 2}"
        is String  -> "String(len=${obj.length})"
        is List<*> -> "List(size=${obj.size})"
        else       -> "other"
    }
    listOf(42, "hi", listOf(1, 2, 3), 3.14).forEach { println("  ${typeOf(it)}") }
}

// ── Collections ────────────────────────────────────────────────────────────────
fun demoCollections() {
    println("  --- Collections ---")

    val nums = listOf(3, 1, 4, 1, 5, 9, 2, 6)
    val mutList = mutableListOf("cat", "dog").also { it.add("fish") }
    val map     = mapOf("France" to "Paris", "Japan" to "Tokyo")
    val set     = setOf(1, 2, 2, 3, 3) // duplicates removed

    println("  set:      $set")
    println("  filter:   ${nums.filter { it > 4 }}")
    println("  map:      ${nums.map { it * 2 }}")
    println("  reduce:   ${nums.reduce { acc, x -> acc + x }}")
    println("  fold:     ${nums.fold(100) { acc, x -> acc + x }}")
    println("  any>8:    ${nums.any { it > 8 }}")
    println("  all>0:    ${nums.all { it > 0 }}")
    println("  count%2:  ${nums.count { it % 2 == 0 }}")
    println("  groupBy:  ${nums.groupBy { if (it % 2 == 0) "even" else "odd" }}")
    println("  flatMap:  ${listOf(listOf(1, 2), listOf(3, 4)).flatMap { it }}")
    println("  zip:      ${listOf(1, 2, 3).zip(listOf("one", "two", "three"))}")
    println("  distinct: ${nums.distinct()}")
    println("  chunked:  ${(1..9).toList().chunked(3)}")
    println("  windowed: ${(1..6).toList().windowed(3, step = 2)}")
    println("  map[Japan]=${map["Japan"]}")
    println("  mutList:  $mutList")
}

// ── Enum.entries (Kotlin 1.9+) ────────────────────────────────────────────────
// entries replaces values() — returns a stable read-only List (no defensive array copy)
enum class Direction(val degrees: Int) {
    NORTH(0), EAST(90), SOUTH(180), WEST(270);

    fun opposite(): Direction = when (this) {
        NORTH -> SOUTH; SOUTH -> NORTH
        EAST  -> WEST;  WEST  -> EAST
    }
}

fun demoEnumEntries() {
    println("  --- Enum.entries (Kotlin 1.9+) ---")
    Direction.entries.forEach { d ->
        println("  ${d.name}(${d.degrees}°) → opposite: ${d.opposite().name}")
    }
    println("  NORTH in entries: ${Direction.NORTH in Direction.entries}")
}

fun demoCollectionsAndRanges() {
    println("\n=== Collections, Ranges & Loops ===")
    demoRanges()
    demoLoops()
    demoWhen()
    demoCollections()
    demoEnumEntries()
}
