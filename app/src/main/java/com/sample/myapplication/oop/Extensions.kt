package com.sample.myapplication.oop

import android.content.Context
import android.widget.Toast

// ── Extension Functions & Properties ──────────────────────────────────────────
// Demonstrates: extension functions, extension properties (no backing field),
//               nullable receiver, generic extensions with type constraints,
//               and Android Context extension

// Android-specific extension — adds showToast to any Context
fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

// String extensions
fun String.capitalizeWords(): String =
    split(" ").joinToString(" ") { word ->
        word.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }
    }

fun String.isPalindrome(): Boolean {
    val clean = filter { it.isLetterOrDigit() }.lowercase()
    return clean == clean.reversed()
}

fun String.truncate(maxLength: Int, ellipsis: String = "…"): String =
    if (length <= maxLength) this else take(maxLength) + ellipsis

// Nullable receiver — safe to call even on null references
fun String?.orDefault(default: String = "N/A"): String = this ?: default

// Extension properties — computed, no backing field allowed
val String.wordCount: Int
    get() = trim().split("\\s+".toRegex()).count { it.isNotEmpty() }

val Int.isEven: Boolean get() = this % 2 == 0
val Int.squared: Int    get() = this * this

// Generic collection extensions
fun <T> List<T>.secondOrNull(): T? = if (size >= 2) this[1] else null

fun <T> List<T>.swap(i: Int, j: Int): List<T> {
    val m = toMutableList()
    val tmp = m[i]; m[i] = m[j]; m[j] = tmp
    return m
}

// Extension with Comparable upper bound
fun <T : Comparable<T>> List<T>.median(): T? {
    if (isEmpty()) return null
    val sorted = sorted()
    return sorted[sorted.size / 2]
}

fun demoExtensions() {
    println("\n=== Extensions ===")

    // String extensions
    println("  ${"hello world".capitalizeWords()}")
    println("  'racecar' isPalindrome: ${"racecar".isPalindrome()}")
    println("  'hello'   isPalindrome: ${"hello".isPalindrome()}")
    println("  truncate: ${"The quick brown fox".truncate(10)}")

    // Nullable receiver
    val nullName: String? = null
    println("  null.orDefault() = ${nullName.orDefault("Guest")}")
    println("  'Alice'.orDefault() = ${"Alice".orDefault()}")

    // Extension properties
    println("  wordCount: ${"The quick brown fox".wordCount}")
    println("  4.isEven=${4.isEven}  7.isEven=${7.isEven}")
    println("  5.squared=${5.squared}")

    // Collection extensions
    val items = listOf("a", "b", "c", "d")
    println("  secondOrNull: ${items.secondOrNull()}")
    println("  swap(0,3): ${items.swap(0, 3)}")

    val numbers = listOf(3, 1, 4, 1, 5, 9, 2, 6)
    println("  median: ${numbers.median()}")
}
