package com.sample.myapplication

class DataClass {

    // We frequently create classes whose main purpose is to hold data. In such a class some standard functionality
    // and utility functions are often mechanically derivable from the data. In Kotlin, this is called a data class
    // and is marked as data
    /**
     * The compiler automatically derives the following members from all properties declared in the primary constructor:
            equals()/hashCode() pair;
            toString() of the form "User(name=John, age=42)";
            componentN() functions corresponding to the properties in their order of declaration;
            copy() function (see below).
     */
    data class User(val name: String = "", val age: Int = 0)

    // Note that the compiler only uses the properties defined inside the primary constructor for the automatically
    // generated functions. To exclude a property from the generated implementations, declare it inside the class body
    /**
     * Only the property name will be used inside the toString(), equals(), hashCode(), and copy() implementations,
     * and there will only be one component function component1(). While two Person objects can have different ages,
     * they will be treated as equal
     */
    data class Person(val name: String = "") {
        var age: Int = 0
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            printToStringValue()
            useOfCopyFunction()
            dataClassWithDestructiveFunction()
            checkSamePerson()
            checkNotSamePerson()
        }

        fun printToStringValue() {
            println("User value is : " + User("Vandit", 30).toString())
        }

        // Copying
        //It's often the case that we need to copy an object altering some of its properties,
        // but keeping the rest unchanged. This is what copy() function is generated for.
        fun useOfCopyFunction() {
            val vansh = User(name = "Vansh", age = 1)
            val oldVansh = vansh.copy(age = 2)
            println("${vansh.name} age is ${vansh.age} for $vansh")
            println("${oldVansh.name} age is ${oldVansh.age} for $oldVansh")
        }

        //Data Classes and Destructuring Declarations
        //Component functions generated for data classes enable their use in destructuring declarations.
        fun dataClassWithDestructiveFunction() {
            val (name, age) = User("James", 50)
            println("$name, $age years of age")
        }

        fun checkSamePerson(){
            val person1 = Person("Vandit")
            val person2 = Person("Vandit")

            person1.age = 31
            person2.age = 30

            println("Person1 with age ${person1.age} and person2 with age ${person2.age} are same ? " +
                    "${(person1 == person2)}")
        }

        fun checkNotSamePerson(){
            val person1 = Person("Vandit")
            val person2 = Person("Vansh")

            person1.age = 31
            person2.age = 30

            println("Person1 with age ${person1.age} and person2 with age ${person2.age} are same ? " +
                    "${(person1 == person2)}")
        }
    }
}