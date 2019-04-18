package com.sample.myapplication

import android.support.annotation.NonNull

class NullCheck {

    companion object {
        @JvmStatic
        fun main (args: Array<String>) {

            val a = "Kotlin"
            println(a.length)
            val b: String? = if(a.length > 2) "test" else null
            println(b?.length)

            // If the expression to the left of ?: is not null, the elvis operator returns it, otherwise it returns
            // the expression to the right. Note that the right-hand side expression is evaluated only
            // if the left-hand side is null
            // Elvis operator ?:
            val length = b?.length ?: -1

            println("length of b is : $length")

            // Safe call operator use with let
            safeCallOperatorUseWithLet(listOf("Kotlin", "Java", null, "C", "C++", null, "Python"))

            // Not null assertion operator
            notNullAssertionOperator("Kotlin")
        }

        fun safeCallOperatorUseWithLet(listWithNull: List<String?>) {
            for (listItem in listWithNull){
                listItem?.let {
                    println("value of item is : $it")
                }
            }

            println("-------Filtered Non Null List------")
            val listWithNonNull: List<String> = listWithNull.filterNotNull()
            for (listItem in listWithNonNull){
                println("value of item is : $listItem")
            }
        }

        fun notNullAssertionOperator(value: String?) {
            println(when(value!!.length) {
                1 -> "length is : 1"
                2 -> "length is : 2"
                else -> "length is : ${value.length}"
            })
        }
    }
}