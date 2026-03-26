package com.sample.myapplication.basics

// ── Variables & Properties ────────────────────────────────────────────────────
// Demonstrates: val/var, const, lateinit, isInitialized guard, backing fields,
//               custom getter/setter, computed property, lazy delegation, @Deprecated

// Compile-time constant — top-level (or in companion/object), String or primitive, no custom getter
const val APP_NAME: String = "KotlinDemo"
const val MAX_RETRIES: Int = 3

// Top-level mutable (var) vs read-only (val)
var sessionCount: Int = 0
val defaultLocale: String = "en-US"

// ── lateinit — non-null var initialized after construction ───────────────────
class UserSession {
    lateinit var userId: String  // must be initialized before first read

    fun start(id: String) {
        userId = id
    }

    fun print() {
        // isInitialized guard prevents UninitializedPropertyAccessException
        if (::userId.isInitialized) {
            println("Active session: $userId")
        } else {
            println("Session not started yet")
        }
    }
}

// ── Backing fields & custom accessors ────────────────────────────────────────
class BankAccount(initialBalance: Double) {
    // 'field' refers to the backing field inside getter/setter
    var balance: Double = initialBalance
        get() = field
        set(value) {
            if (value >= 0) field = value
            else println("Rejected negative balance: $value")
        }

    // Computed property — derives value, no backing field
    val isOverdrawn: Boolean
        get() = balance < 0.0
}

// ── Delegated property: lazy ──────────────────────────────────────────────────
class HeavyResource {
    // Computed only on first access; result cached for subsequent reads
    val data: List<String> by lazy {
        println("  [lazy] Loading data…")
        listOf("Alpha", "Beta", "Gamma")
    }
}

// ── @Deprecated with ReplaceWith ──────────────────────────────────────────────
class LegacyApi {
    @Deprecated(
        message = "Use greetUser(name) instead",
        replaceWith = ReplaceWith("greetUser(name)")
    )
    fun hello(name: String) = println("Hello $name")

    fun greetUser(name: String) = println("Welcome, $name!")
}

fun demoVariablesAndProperties() {
    println("\n=== Variables & Properties ===")

    // val vs var
    sessionCount++
    println("sessionCount=$sessionCount  defaultLocale=$defaultLocale")
    println("APP_NAME=$APP_NAME  MAX_RETRIES=$MAX_RETRIES")

    // lateinit + isInitialized
    val session = UserSession()
    session.print()         // not initialized yet
    session.start("u-42")
    session.print()         // initialized

    // Custom getter/setter with backing field validation
    val account = BankAccount(100.0)
    account.balance = 250.0
    println("Balance: ${account.balance}, overdrawn: ${account.isOverdrawn}")
    account.balance = -10.0 // rejected
    println("Balance after rejected set: ${account.balance}")

    // Lazy delegation
    val resource = HeavyResource()
    println("Before first access")
    println("data = ${resource.data}") // triggers init
    println("data = ${resource.data}") // cached, no re-init
}
