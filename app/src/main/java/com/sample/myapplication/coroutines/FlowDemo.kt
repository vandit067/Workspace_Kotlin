package com.sample.myapplication.coroutines

import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

// ── Kotlin Flow ────────────────────────────────────────────────────────────────
// Demonstrates: flow builder, flowOf, asFlow, collect, operators (filter/map/transform/
//               take/onEach/catch/onCompletion), zip, flatMapConcat,
//               StateFlow (hot, always has a current value), SharedFlow (hot, multicast)

// Cold flow — starts fresh for each collector
fun numbersFlow(): Flow<Int> = flow {
    println("  [flow] started")
    for (i in 1..5) {
        delay(40)
        emit(i)
    }
}

// Chained operators — returns a new Flow (lazy, nothing runs yet)
fun processedFlow(): Flow<String> = numbersFlow()
    .filter { it % 2 == 0 }
    .map    { "item-$it" }
    .onEach { println("  onEach: $it") }

// Flow with an error mid-stream
fun riskyFlow(): Flow<Int> = flow {
    emit(1); emit(2)
    throw RuntimeException("Flow error!")
    @Suppress("UNREACHABLE_CODE")
    emit(3)
}

// transform — emit 0..N items per upstream item
fun expandingFlow(): Flow<String> = (1..3).asFlow()
    .transform { n ->
        emit("before-$n")
        emit("$n")
        emit("after-$n")
    }

fun demoFlow() {
    println("\n=== Kotlin Flow ===")

    runBlocking {

        // ── Basic collect ─────────────────────────────────────────────────────
        println("  --- collect ---")
        numbersFlow().collect { println("  collected: $it") }

        // ── filter + map ──────────────────────────────────────────────────────
        println("  --- filter + map ---")
        processedFlow().collect { println("  $it") }

        // ── take ──────────────────────────────────────────────────────────────
        println("  --- take ---")
        numbersFlow().take(3).collect { print("$it ") }
        println()

        // ── transform ─────────────────────────────────────────────────────────
        println("  --- transform ---")
        expandingFlow().take(6).collect { print("$it ") }
        println()

        // ── Terminal operators ─────────────────────────────────────────────────
        val squares = (1..5).asFlow().map { it * it }.toList()
        println("  toList: $squares")

        // ── catch + onCompletion ───────────────────────────────────────────────
        println("  --- catch ---")
        riskyFlow()
            .catch  { e -> emit(-1); println("  caught: ${e.message}") }
            .onCompletion { println("  flow completed") }
            .collect { println("  value: $it") }

        // ── zip ───────────────────────────────────────────────────────────────
        println("  --- zip ---")
        flowOf("A", "B", "C")
            .zip(flowOf(1, 2, 3)) { a, b -> "$a$b" }
            .collect { print("$it ") }
        println()

        // ── flatMapConcat ──────────────────────────────────────────────────────
        println("  --- flatMapConcat ---")
        (1..3).asFlow()
            .flatMapConcat { n -> flowOf("$n-x", "$n-y") }
            .collect { print("$it ") }
        println()

        // ── StateFlow — hot flow, always holds a current value ─────────────────
        println("  --- StateFlow ---")
        val state = MutableStateFlow(0)
        launch {
            repeat(3) { i -> delay(30); state.value = i + 1 }
        }
        state.take(4).collect { println("  state=$it") }   // 0, 1, 2, 3

        // ── SharedFlow — hot, multicast, configurable replay ───────────────────
        println("  --- SharedFlow ---")
        val shared = MutableSharedFlow<String>(replay = 1)
        launch { shared.emit("event-A") }
        launch { shared.emit("event-B") }
        delay(30)
        shared.take(1).collect { println("  shared=$it") }
    }
}
