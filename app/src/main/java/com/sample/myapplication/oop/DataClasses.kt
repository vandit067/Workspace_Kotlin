package com.sample.myapplication.oop

// ── Data Classes & Data Objects ────────────────────────────────────────────────
// Demonstrates: auto-generated equals/hashCode/toString/copy/componentN,
//               destructuring, body-only properties excluded from generated fns,
//               and data object (Kotlin 1.9+)

// Data class — compiler generates equals, hashCode, toString, copy, componentN
data class User(val name: String, val age: Int, val email: String = "")

// Properties declared in the class body are NOT part of generated functions
data class Product(val id: Int, val title: String) {
    var stock: Int = 0           // excluded from equals/hashCode/toString/copy
    var discountPct: Double = 0.0
}

// Nested data classes model rich domain objects
data class Address(val street: String, val city: String, val zip: String)
data class Customer(
    val name: String,
    val address: Address,
    val isPremium: Boolean = false
)

// ── Data Object (Kotlin 1.9+) ─────────────────────────────────────────────────
// Singleton with auto-generated toString/equals/hashCode — perfect for sealed hierarchies
data object UnauthenticatedUser {
    val role = "guest"
}

fun demoDataClasses() {
    println("\n=== Data Classes & Data Objects ===")

    // toString
    val alice = User("Alice", 30, "alice@example.com")
    println("  $alice")

    // equals — structural (==) vs referential (===)
    val alice2 = User("Alice", 30, "alice@example.com")
    println("  == : ${alice == alice2}")   // true
    println("  ===: ${alice === alice2}")  // false

    // copy — create a modified clone
    val olderAlice = alice.copy(age = 31)
    println("  original: $alice")
    println("  copy:     $olderAlice")

    // Destructuring via componentN()
    val (name, age, email) = alice
    println("  name=$name  age=$age  email=$email")

    // Body property excluded from equals
    val p1 = Product(1, "Laptop").also { it.stock = 10 }
    val p2 = Product(1, "Laptop").also { it.stock = 999 }
    println("  p1 == p2: ${p1 == p2}") // true — stock is excluded

    // Nested copy
    val customer = Customer("Bob", Address("123 Main St", "Springfield", "12345"), true)
    println("  $customer")
    val moved = customer.copy(address = customer.address.copy(city = "Shelbyville"))
    println("  moved: $moved")

    // Data object
    println("  $UnauthenticatedUser (role=${UnauthenticatedUser.role})")
    println("  same instance: ${UnauthenticatedUser === UnauthenticatedUser}") // always true
}
