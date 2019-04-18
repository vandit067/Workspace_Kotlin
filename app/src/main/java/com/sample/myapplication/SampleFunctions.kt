package com.sample.myapplication

class SampleFunctions{
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // Functions
            println("Sum of 3 & 5 is : " + sum(3, 5))
            println("Sum of 2 & 5 is : ${sumWithInferredReturnType(2, 5)}")
            sumWithNoreturnType(-1, 4)
            println("Power of number 2 with component 5 is : ${powOf(2.0, 5.0)}")
            //Override methods use
            val b = B()
            b.foo(20)

            printHello(null)
            /**
             * Variable Number Of Arguments
             * When we call a vararg-function, we can pass arguments one-by-one,e.g. asList(1, 2, 3), or,
             * if we already have an array and want to pass its contents to the function,we use the spread
             * operator (prefix the array with *):
             */
            val a = arrayOf(1, 2, 3)
            println("list a : ${a.asList()}")
            val list = arrayListOf(*a)
            println("list : $list")

            /**
             * Infix notation
             * Functions marked with the infix keyword can also be called using the infix notation (omitting the dot and the parentheses for the call). Infix functions must satisfy the following requirements:
             * They must be member functions or extension functions;
             * They must have a single parameter;
             * The parameter must not accept variable number of arguments and must have no default value.
             */
            this printValue 2

            // local function scope
            localFunction(true)
        }

        fun sum(a: Int, b: Int): Int {
            return a + b
        }

        // Single expression function
        fun sumWithInferredReturnType(a: Int, b: Int) = a + b

        fun sumWithNoreturnType(a: Int, b: Int) {
            println("Sum of $a & $b is : ${a + b}")
        }

        fun powOf(number: Double, component: Double = 2.0): Double {
            return Math.pow(number, component)
        }

        fun printHello(name: String?): Unit {
            if(name != null){
                println("Name is : $name")
            } else {
                println("Name is empty")
            }
        }

        infix fun printValue(a: Int) {
            println("Value is $a")
        }

        // Function inside function
        fun localFunction(flag: Boolean) {
            var a = 2
            fun localFunction(b: Int = 2) {
                a += b
            }
            if(flag) {
               localFunction(5)
            }
            println("Value of a is $a")
        }

    }

    open class A {
        open fun foo(a: Int = 10) {
            println("Value in class A is $a")
        }
    }

    open class B : A() {
        override fun foo(a: Int) {
            println("Value in class B is $a")
        }
    }
}
