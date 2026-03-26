package com.sample.myapplication.coroutines

import kotlinx.coroutines.*

// ── Coroutines Basics ──────────────────────────────────────────────────────────
// Demonstrates: runBlocking, launch (Job), async/await (Deferred),
//               delay, structured concurrency (coroutineScope), withContext,
//               cancellation (cancel/ensureActive), withTimeout, exception handling

// Suspend functions — can be suspended and resumed without blocking a thread
suspend fun fetchUserName(id: Int): String {
    delay(100)               // non-blocking delay; suspends the coroutine, not the thread
    return "User-$id"
}

suspend fun fetchUserScore(id: Int): Int {
    delay(80)
    return id * 10
}

// coroutineScope — parallel decomposition; both async blocks run concurrently
suspend fun loadUserData(id: Int): String = coroutineScope {
    val name  = async { fetchUserName(id) }   // starts immediately
    val score = async { fetchUserScore(id) }  // starts immediately — runs in parallel
    "${name.await()} | score: ${score.await()}"
}

// withContext — switch dispatcher (e.g. to Default for CPU work) without a new coroutine
suspend fun heavyComputation(): Long = withContext(Dispatchers.Default) {
    (1L..1_000_000L).sum()
}

// Cooperative cancellation — ensureActive() throws CancellationException if cancelled
suspend fun cancellableTask(label: String): String {
    repeat(10) { i ->
        ensureActive()          // cooperative cancellation check
        delay(50)
        println("  $label step $i")
    }
    return "$label complete"
}

fun demoCoroutinesBasics() {
    println("\n=== Coroutines Basics ===")

    runBlocking {   // bridges blocking world → coroutine world (for demos / tests only)

        // ── launch — fire and forget, returns Job ────────────────────────────
        println("  --- launch ---")
        val job = launch {
            delay(200)
            println("  Job done")
        }
        println("  Launched, waiting for job…")
        job.join()    // suspend until job completes

        // ── async / await — returns Deferred<T> ──────────────────────────────
        println("  --- async/await ---")
        val deferred = async { fetchUserName(2) }
        println("  deferred result = ${deferred.await()}")

        // ── Parallel with coroutineScope ─────────────────────────────────────
        println("  --- parallel (coroutineScope) ---")
        val start = System.currentTimeMillis()
        val data  = loadUserData(7)
        println("  $data  (${System.currentTimeMillis() - start}ms)")   // ~100ms not 180ms

        // ── withContext — switch dispatcher ───────────────────────────────────
        println("  --- withContext ---")
        val sum = heavyComputation()
        println("  sum(1..1M) = $sum")

        // ── Cancellation ──────────────────────────────────────────────────────
        println("  --- cancellation ---")
        val cancelJob = launch { cancellableTask("Task-A") }
        delay(130)
        cancelJob.cancel()
        cancelJob.join()
        println("  Task-A cancelled after 130ms")

        // ── withTimeout ───────────────────────────────────────────────────────
        println("  --- withTimeout ---")
        try {
            withTimeout(150) {
                delay(500)
                "never reached"
            }
        } catch (e: TimeoutCancellationException) {
            println("  Timed out: ${e.message}")
        }

        val orNull = withTimeoutOrNull(150) {
            delay(50)
            "completed in time"
        }
        println("  withTimeoutOrNull = $orNull")

        // ── CoroutineExceptionHandler ─────────────────────────────────────────
        println("  --- exception handler ---")
        val handler = CoroutineExceptionHandler { _, e ->
            println("  Handler caught: ${e.message}")
        }
        val errJob = launch(handler) { throw RuntimeException("Boom!") }
        errJob.join()
    }
}
