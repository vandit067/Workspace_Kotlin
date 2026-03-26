package com.sample.myapplication.delegation

import kotlin.properties.Delegates
import kotlin.reflect.KProperty

// ── Delegation ─────────────────────────────────────────────────────────────────
// Demonstrates: class delegation (by), lazy, Delegates.observable,
//               Delegates.vetoable, custom property delegate, Map delegation

// ── Class Delegation — by keyword ─────────────────────────────────────────────
// All interface methods are forwarded to the wrapped delegate; override selectively
interface Logger {
    fun log(message: String)
    fun logError(message: String) = log("[ERROR] $message")  // default impl
}

class ConsoleLogger : Logger {
    override fun log(message: String) = println("  $message")
}

class CountingLogger(private val delegate: Logger = ConsoleLogger()) : Logger by delegate {
    private var count = 0

    // Override only this method; logError still uses delegate via the default
    override fun log(message: String) {
        count++
        delegate.log("[$count] $message")
    }

    fun printTotal() = println("  Total log calls: $count")
}

// ── lazy ───────────────────────────────────────────────────────────────────────
class AppDatabase {
    val connectionString: String by lazy {
        println("  [lazy] Opening DB connection…")
        "jdbc:sqlite:app.db"
    }
}

// ── Delegates.observable — callback on every change ───────────────────────────
class ObservableUser {
    var name: String by Delegates.observable("<none>") { prop, old, new ->
        println("  ${prop.name}: '$old' → '$new'")
    }
    var score: Int by Delegates.observable(0) { _, old, new ->
        println("  score: $old → $new")
    }
}

// ── Delegates.vetoable — callback can reject a change ─────────────────────────
class RangeGuarded {
    var level: Int by Delegates.vetoable(0) { _, _, new ->
        (new in 0..100).also { if (!it) println("  Rejected level=$new (must be 0..100)") }
    }
}

// ── Custom delegate ────────────────────────────────────────────────────────────
// Implement getValue/setValue operators to create a reusable property delegate
class TrimDelegate(private var backing: String = "") {
    operator fun getValue(thisRef: Any?, property: KProperty<*>): String = backing
    operator fun setValue(thisRef: Any?, property: KProperty<*>, value: String) {
        backing = value.trim()
    }
}

class UserProfile {
    var displayName: String by TrimDelegate()
    var bio: String by TrimDelegate("No bio yet.")
}

// ── Map delegation — property values backed by a Map ─────────────────────────
class ConfigFromMap(map: Map<String, Any?>) {
    val host: String  by map
    val port: Int     by map
    val debug: Boolean by map
}

fun demoDelegation() {
    println("\n=== Delegation ===")

    // Class delegation
    println("  --- class delegation ---")
    val logger = CountingLogger()
    logger.log("App started")
    logger.log("Config loaded")
    logger.logError("Something went wrong")   // uses default impl → routes through override
    logger.printTotal()

    // lazy
    println("  --- lazy ---")
    val db = AppDatabase()
    println("  (before first access)")
    println("  ${db.connectionString}")  // triggers init
    println("  ${db.connectionString}")  // cached

    // observable
    println("  --- observable ---")
    val user = ObservableUser()
    user.name  = "Alice"
    user.name  = "Bob"
    user.score = 100
    user.score = 200

    // vetoable
    println("  --- vetoable ---")
    val rg = RangeGuarded()
    rg.level = 75;  println("  level=${rg.level}")
    rg.level = 110              // rejected
    println("  level after rejection=${rg.level}")
    rg.level = 90;  println("  level=${rg.level}")

    // Custom delegate
    println("  --- custom delegate ---")
    val profile = UserProfile()
    profile.displayName = "  Kotlin Dev  "
    println("  displayName='${profile.displayName}'")  // trimmed
    println("  bio='${profile.bio}'")

    // Map delegation
    println("  --- map delegation ---")
    val config = ConfigFromMap(
        mapOf("host" to "localhost", "port" to 5432, "debug" to true)
    )
    println("  ${config.host}:${config.port}  debug=${config.debug}")
}
