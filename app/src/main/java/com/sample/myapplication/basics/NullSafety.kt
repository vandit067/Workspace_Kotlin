package com.sample.myapplication.basics

// ── Null Safety ────────────────────────────────────────────────────────────────
// Demonstrates: nullable types, safe call (?.), Elvis (?:), not-null assertion (!!),
//               let with null, filterNotNull, mapNotNull, requireNotNull/checkNotNull

// Nullable return type — caller is forced to handle null
fun findUser(id: Int): String? =
    if (id > 0) "User-$id" else null

// Safe call chain — short-circuits to null at first null in the chain
fun emailDomain(email: String?): String? =
    email?.substringAfter("@")?.uppercase()

// Elvis operator — default value when left-hand side is null
fun displayName(name: String?): String = name ?: "Anonymous"

// Not-null assertion — use only when you are certain; throws NPE if null
fun forcedLength(value: String?): Int = value!!.length

// requireNotNull — throws IllegalArgumentException with a custom message
fun processToken(token: String?) {
    val valid = requireNotNull(token) { "Auth token must not be null" }
    println("  Token accepted: $valid")
}

// checkNotNull — throws IllegalStateException
fun ensureReady(state: String?) {
    val s = checkNotNull(state) { "State must be initialised before use" }
    println("  State: $s")
}

// Nullable collections: filterNotNull and mapNotNull
fun demoCollectionNull() {
    println("  --- nullable collections ---")
    val mixed: List<String?> = listOf("Kotlin", null, "Java", null, "Swift")
    println("  Raw:         $mixed")

    // filterNotNull → List<String> (non-nullable element type)
    val clean: List<String> = mixed.filterNotNull()
    println("  Filtered:    $clean")

    // mapNotNull — transform + drop nulls in one pass
    val lengths = mixed.mapNotNull { it?.length }
    println("  Lengths:     $lengths")
}

// let for null-safe scoping
fun processIfPresent(value: String?) {
    value?.let { nonNull ->
        println("  Present: $nonNull (len=${nonNull.length})")
    } ?: println("  Was null — skipped")
}

fun demoNullSafety() {
    println("\n=== Null Safety ===")

    println(findUser(5))
    println(findUser(-1))

    println(emailDomain("alice@example.com"))
    println(emailDomain(null))

    println(displayName("Carol"))
    println(displayName(null))

    // Elvis in a loop
    val emails: List<String?> = listOf("a@test.com", null, "b@demo.org")
    emails.forEach { e ->
        println("  domain: ${e?.substringAfter("@") ?: "N/A"}")
    }

    demoCollectionNull()

    processIfPresent("Kotlin 2.0")
    processIfPresent(null)

    processToken("Bearer abc123")
    try { processToken(null) } catch (e: IllegalArgumentException) { println("  ${e.message}") }

    ensureReady("READY")
    try { ensureReady(null) } catch (e: IllegalStateException) { println("  ${e.message}") }
}
