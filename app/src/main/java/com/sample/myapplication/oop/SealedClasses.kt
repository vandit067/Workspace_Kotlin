package com.sample.myapplication.oop

// ── Sealed Classes & Sealed Interfaces ────────────────────────────────────────
// Demonstrates: sealed class hierarchy, sealed interface, exhaustive when (no else),
//               modelling network results, UI events, and payment state

// ── Sealed class — generic result wrapper ─────────────────────────────────────
// All direct subclasses must be in the same package
sealed class NetworkResult<out T> {
    data class Success<T>(val data: T)              : NetworkResult<T>()
    data class Error(val code: Int, val message: String) : NetworkResult<Nothing>()
    data object Loading                              : NetworkResult<Nothing>()
}

fun fetchUser(id: Int): NetworkResult<String> = when {
    id <= 0  -> NetworkResult.Error(400, "Invalid ID")
    id == 99 -> NetworkResult.Loading
    else     -> NetworkResult.Success("User-$id")
}

// Exhaustive when — compiler enforces all branches; no 'else' needed
fun handleResult(result: NetworkResult<String>): String = when (result) {
    is NetworkResult.Success -> "Loaded: ${result.data}"
    is NetworkResult.Error   -> "Error ${result.code}: ${result.message}"
    NetworkResult.Loading    -> "Still loading…"
}

// ── Sealed Interface (Kotlin 1.5+) ────────────────────────────────────────────
// More flexible than sealed class — subclasses can be data classes, objects, or
// even classes from elsewhere in the same package
sealed interface UiEvent {
    data object Refresh                                         : UiEvent
    data class Navigate(val route: String)                      : UiEvent
    data class ShowSnackbar(val message: String, val isError: Boolean = false) : UiEvent
}

fun processEvent(event: UiEvent): String = when (event) {
    is UiEvent.Refresh      -> "Refreshing data"
    is UiEvent.Navigate     -> "Navigating to ${event.route}"
    is UiEvent.ShowSnackbar -> if (event.isError) "Error: ${event.message}"
                               else "Info: ${event.message}"
}

// ── Modelling payment lifecycle ───────────────────────────────────────────────
sealed class PaymentState {
    data object Idle                                  : PaymentState()
    data class  Processing(val amount: Double)        : PaymentState()
    data class  Completed(val transactionId: String)  : PaymentState()
    data class  Failed(val reason: String)            : PaymentState()
}

fun describePayment(state: PaymentState) = when (state) {
    PaymentState.Idle            -> "Waiting for payment"
    is PaymentState.Processing   -> "Processing $${"%.2f".format(state.amount)}"
    is PaymentState.Completed    -> "Paid! TX: ${state.transactionId}"
    is PaymentState.Failed       -> "Payment failed: ${state.reason}"
}

fun demoSealedClasses() {
    println("\n=== Sealed Classes & Interfaces ===")

    listOf(-1, 42, 99).forEach { id ->
        println("  fetchUser($id) → ${handleResult(fetchUser(id))}")
    }

    println()

    val events: List<UiEvent> = listOf(
        UiEvent.Refresh,
        UiEvent.Navigate("/home"),
        UiEvent.ShowSnackbar("Saved!"),
        UiEvent.ShowSnackbar("Network error", isError = true)
    )
    events.forEach { println("  ${processEvent(it)}") }

    println()

    val states: List<PaymentState> = listOf(
        PaymentState.Idle,
        PaymentState.Processing(49.99),
        PaymentState.Completed("TX-7781"),
        PaymentState.Failed("Card declined")
    )
    states.forEach { println("  ${describePayment(it)}") }
}
