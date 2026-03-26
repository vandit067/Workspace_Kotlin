package com.sample.myapplication.types

// ── Value Classes ──────────────────────────────────────────────────────────────
// Demonstrates: @JvmInline value class (stable since Kotlin 1.5)
// A value class wraps a single value and provides type safety with (usually) zero overhead —
// at runtime the JVM sees the underlying type directly, not a wrapper object.

@JvmInline
value class UserId(val raw: Long) {
    init { require(raw > 0) { "UserId must be positive, got $raw" } }
    override fun toString() = "UserId($raw)"
}

@JvmInline
value class EmailAddress(val value: String) {
    init {
        require(value.contains("@") && value.contains(".")) {
            "'$value' is not a valid email"
        }
    }
    val domain: String get() = value.substringAfter("@")
    override fun toString() = "Email($value)"
}

// Arithmetic with value classes
@JvmInline
value class Meters(val value: Double) {
    operator fun plus(other: Meters)  = Meters(value + other.value)
    operator fun times(factor: Double) = Meters(value * factor)
    override fun toString() = "${value}m"
}

@JvmInline
value class Kilograms(val value: Double) {
    override fun toString() = "${value}kg"
}

// Value class implementing an interface
interface Printable { fun print() }

@JvmInline
value class Label(val text: String) : Printable {
    override fun print() = println("  Label: $text")
}

// API using value classes — prevents mixing up raw primitives accidentally
fun findUserById(id: UserId): String = "Loaded user ${id.raw}"
fun sendEmail(to: EmailAddress, subject: String) =
    println("  Sending '$subject' → ${to.value}")

// Type safety demo: the compiler rejects findUserById(42L) — must wrap in UserId
fun demoValueClasses() {
    println("\n=== Value Classes ===")

    val uid = UserId(42L)
    println("  $uid")
    println("  ${findUserById(uid)}")

    val email = EmailAddress("dev@kotlin.org")
    println("  domain: ${email.domain}")
    sendEmail(email, "Hello Kotlin!")

    // Arithmetic
    val a = Meters(3.5)
    val b = Meters(2.0)
    println("  $a + $b = ${a + b}")
    println("  $a × 2  = ${a * 2.0}")

    // Interface implementation
    Label("Submit").print()

    // Init block validation
    try { UserId(-1) } catch (e: IllegalArgumentException) { println("  ${e.message}") }
    try { EmailAddress("not-an-email") } catch (e: IllegalArgumentException) { println("  ${e.message}") }
}
