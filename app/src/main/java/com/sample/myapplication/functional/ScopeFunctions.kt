package com.sample.myapplication.functional

// ── Scope Functions ────────────────────────────────────────────────────────────
// let, run, with, apply, also — quick reference:
//
//  Function │ Context object │ Return value      │ Is extension?
//  ─────────┼────────────────┼───────────────────┼──────────────
//  let      │ it             │ lambda result     │ yes
//  run      │ this           │ lambda result     │ yes
//  with     │ this           │ lambda result     │ no
//  apply    │ this           │ context object    │ yes
//  also     │ it             │ context object    │ yes

data class ServerConfig(
    var host: String = "localhost",
    var port: Int = 8080,
    var maxConnections: Int = 100,
    var useTls: Boolean = false
)

data class ApiResponse(val status: Int, val body: String?)

fun fetchData(endpoint: String): ApiResponse? =
    if (endpoint.startsWith("/api")) ApiResponse(200, """{"ok":true}""") else null

fun demoScopeFunctions() {
    println("\n=== Scope Functions ===")

    // ── let — context: it, returns: lambda result ──────────────────────────────
    // Use: null-safe execution, scoping a temp var, transforming a value
    println("  --- let ---")
    val rawName: String? = "  Kotlin  "
    val trimmedLen = rawName?.let {
        println("  let: processing '$it'")
        it.trim().length
    }
    println("  trimmedLen=$trimmedLen")

    // let as a transformation chain
    val words = "  hello world  "
        .let { it.trim() }
        .let { it.split(" ") }
        .let { it.map(String::uppercase) }
    println("  words=$words")

    // ── run — context: this, returns: lambda result ────────────────────────────
    // Use: object config + result, or as a standalone scoped block
    println("  --- run ---")
    val config = ServerConfig()
    val summary = config.run {
        host = "prod.example.com"
        port = 443
        useTls = true
        "$host:$port (TLS=$useTls)" // returned
    }
    println("  summary=$summary")

    // Non-extension run
    val total = run {
        val price = 29.99
        val qty = 3
        price * qty
    }
    println("  total=$total")

    // ── with — context: this, returns: lambda result, not an extension ─────────
    // Use: calling multiple functions on an already-created object
    println("  --- with ---")
    val staging = ServerConfig(host = "staging.example.com", port = 8443)
    val desc = with(staging) {
        println("  with: host=$host port=$port tls=$useTls")
        "staging at $host:$port"
    }
    println("  desc=$desc")

    // ── apply — context: this, returns: context object ─────────────────────────
    // Use: object initialisation / builder pattern — chained constructors
    println("  --- apply ---")
    val server = ServerConfig().apply {
        host = "api.myapp.com"
        port = 443
        maxConnections = 500
        useTls = true
    }
    println("  server=$server")

    // ── also — context: it, returns: context object ────────────────────────────
    // Use: side effects (logging, assertions) that don't change the object
    println("  --- also ---")
    val users = mutableListOf("Alice", "Bob")
        .also { println("  also before: $it") }
        .apply { add("Charlie") }
        .also { println("  also after:  $it") }
    println("  users=$users")

    // ── Combining scope functions ──────────────────────────────────────────────
    println("  --- combining ---")
    fetchData("/api/users")
        ?.also { println("  response status=${it.status}") }
        ?.let { it.body }
        ?.run { trim() }
        ?.also { println("  body=$it") }
        ?: println("  no response")

    fetchData("/missing")
        ?.let { it.body }
        ?: println("  endpoint not found")
}
