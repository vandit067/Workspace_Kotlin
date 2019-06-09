package com.sample.myapplication

// class in kotlin declared using keyword class
class ClassAndInheritance {

    fun callPerson() {
        val person = Person("Vandit")
    }

    fun callPersonWithSecondaryConstructor() {
        val person = Person("Vandit", "Patel")
    }
}

fun main(){
    val classAndInheritance = ClassAndInheritance()
    classAndInheritance.callPersonWithSecondaryConstructor()
}
/**
 * Class created with class name, header(primary constructor, parameters) and class body
 * Primary constructor not contain any code
 * If the primary constructor does not have any annotations or visibility modifiers, the constructor keyword can be omitted
 * init - initializer block, during instance initialization blocks executed in same order as they appear in the class body
 ***/
class Person constructor(name: String) {
    val nameUpperCase = name.toUpperCase()
    val firstProperty = "First property: $name".also (::println)

    init {
        println("First initializer block with value $name")
        println("Uppercase Name: $nameUpperCase")
    }

    val secondProperty = "Second property: $name".also (::println)

    init {
        println("Second initializer block with value ${name.length}")
    }

    constructor(firstName: String, lastName: String) : this("$firstName $lastName") {
        println("Person Name: $firstName $lastName")
    }
}

// Empty class with no body, header
class Empty