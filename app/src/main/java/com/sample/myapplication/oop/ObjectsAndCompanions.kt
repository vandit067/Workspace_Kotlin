package com.sample.myapplication.oop

// ── Objects & Companion Objects ───────────────────────────────────────────────
// Demonstrates: object declaration (singleton), companion object (factory + static-like),
//               companion implementing an interface, object expression (anonymous class)

// ── Object declaration — application-wide singleton ───────────────────────────
object AppConfig {
    val appName: String = "KotlinDemo"
    var debugMode: Boolean = false
    private val features = mutableSetOf<String>()

    fun enableFeature(name: String) { features.add(name) }
    fun isEnabled(name: String) = name in features
    override fun toString() = "AppConfig(app=$appName, debug=$debugMode, features=$features)"
}

// ── Companion object — factory + class-level members ─────────────────────────
// Accessed via ClassName.member — analogous to Java statics
class Session private constructor(val token: String, val userId: String) {

    companion object Factory {
        private var count = 0
        val DEFAULT_TIMEOUT_MS = 30_000L

        // Private constructor means this is the only way to create a Session
        fun create(userId: String): Session {
            val token = "tok-${userId}-${++count}"
            return Session(token, userId)
        }

        fun totalCreated() = count
    }

    override fun toString() = "Session(token=$token, user=$userId)"
}

// Companion implementing an interface
interface Validator<T> {
    fun isValid(value: T): Boolean
}

class Email(val address: String) {
    companion object : Validator<String> {
        override fun isValid(value: String) =
            value.contains("@") && value.contains(".")

        fun parse(value: String): Email? =
            if (isValid(value)) Email(value) else null
    }

    override fun toString() = "Email($address)"
}

// ── Object expression — anonymous object (inline implementation) ──────────────
interface ClickListener {
    fun onClick(target: String)
    fun onLongClick(target: String) = println("  Long-click: $target")
}

fun attachListener(target: String, listener: ClickListener) {
    listener.onClick(target)
    listener.onLongClick(target)
}

fun demoObjectsAndCompanions() {
    println("\n=== Objects & Companion Objects ===")

    // Singleton
    println("  $AppConfig")
    AppConfig.debugMode = true
    AppConfig.enableFeature("dark_mode")
    AppConfig.enableFeature("beta_ui")
    println("  dark_mode enabled: ${AppConfig.isEnabled("dark_mode")}")
    println("  $AppConfig")

    println()

    // Companion / Factory
    val s1 = Session.create("alice")
    val s2 = Session.create("bob")
    println("  $s1")
    println("  $s2")
    println("  Sessions created: ${Session.totalCreated()}, timeout: ${Session.DEFAULT_TIMEOUT_MS}ms")

    println()

    // Companion as Validator
    println("  valid: ${Email.isValid("user@example.com")}")
    println("  invalid: ${Email.isValid("not-an-email")}")
    println("  parsed: ${Email.parse("dev@kotlin.org")}")

    println()

    // Object expression — anonymous class with overrides
    attachListener("Button-A", object : ClickListener {
        override fun onClick(target: String) = println("  Clicked: $target")
        // onLongClick uses default implementation
    })
}
