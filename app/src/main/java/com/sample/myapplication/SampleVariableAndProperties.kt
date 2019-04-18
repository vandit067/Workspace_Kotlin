package com.sample.myapplication

// var - mutable keyword
// val - read-only keyword

/**
 * Compile-Time Constants
 * Properties the value of which is known at compile time can be marked as compile time constants using the const modifier.
 * Such properties need to fulfil the following requirements:
 * Top-level, or member of an object declaration or a companion object.
 * Initialized with a value of type String or a primitive type
 * No custom getter
 * Such properties can be used in annotations:
 */
const val SUBSYSTEM_DEPRECATED: String = "This subsystem is deprecated"

class SampleVariableAndProperties {

    @Deprecated(SUBSYSTEM_DEPRECATED)
    fun foo() {
        // Explain Compile time constants - const
    }

    fun copyAddress(address: Address): Address {
        val result = Address()
        result.name = address.name
        result.city = address.city
        result.state = address.state
        result.street = address.street
        result.zipCode = address.zipCode
        return result
    }
}

class Address {
    var name: String = "Test"
    var street: String = ""
    var city: String = ""
    var state: String = ""
    var zipCode = 0
    set(value) {
        // Backing Fields
        if(zipCode > 0) {
            field = value
        }
    }
}