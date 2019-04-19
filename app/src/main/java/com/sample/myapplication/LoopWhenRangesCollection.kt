package com.sample.myapplication

class LoopWhenRangesCollection {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            // Use of for loop
            useOfForLoop()
            // Using for each loop
            useOfForEachLoop(listOf("android", "ios", "windows"))
            // Using for each loop with indices
            useOfForEachLoopWithIndices(listOf("android", "ios", "windows"))
            // Using for each loop with index
            useOfForEachLoopWithIndex(listOf("android", "ios", "windows"))

            // Use of while loop
            useOfWhileLoop(listOf("android", "ios", "windows"))
            // Use of do while loop
            useOfDoWhileLoop(listOf("android", "ios", "windows"))

            // Use of when expression
            println("Type is ${useWhenExpression(1.5)}")

            // Use of range to check no is in range or not
            useRangeToCheckNoInRange(11)
            // Use of range to check out of range case
            useOfRangeToCheckNoOutOfRange(listOf("android", "ios", "windows"))

            // Use of collection to print results or iterate through loop
            useOfCollectionToPrintResults(listOf("android", "ios", "windows"))
            // Use of collection to check object contains or not in collection
            useOfCollectionToCheckContainsObject(listOf("android", "", "Something"))
            // Use of collection with use of filter and map
            useOfCollectionWithMapAndFilter(listOf("android", "assets", "banana", "apple", "fruits", "ball", "force"))

        }

        fun useOfForEachLoop(itemList: List<String>){
            println("----- UseOfForEachLoop --------")
            for (item in itemList){
                println(item)
            }
        }

        fun useOfForEachLoopWithIndices(itemList: List<String>){
            println("----- UseOfForEachLoopWithIndices --------")
            for (index in itemList.indices){
                println("Item at $index is ${itemList[index]}")
            }
        }

        fun useOfForEachLoopWithIndex(itemList: List<String>){
            println("----- UseOfForEachLoopWithIndex --------")
            for ((index, value) in itemList.withIndex()){
                println("Item at $index is $value")
            }
        }

        fun useOfForLoop() {
            println("----- In --------")
            for (i in 1..5){
                println(i)
            }

            println("----- DownTo --------")
            for (i in 6 downTo 0 step 1){
                println(i)
            }

            println("----- Until --------")
            for (i in 2 until 10 step 2){
                println(i)
            }
        }

        fun useOfWhileLoop(itemList: List<String>) {
            println("----- useOfWhileLoop -----")
            var index = 0
            while (index < itemList.size) {
                println("item at $index is ${itemList[index]}")
                index++
            }

            do {

            } while (index < itemList.size)
        }

        fun useOfDoWhileLoop(itemList: List<String>) {
            println("----- useOfDoWhileLoop -----")
            var index = itemList.size - 1
            do {
                println("item at $index is ${itemList[index]}")
                index--
            } while (index >= 0)
        }

        fun useWhenExpression(obj: Any): String {
            println("----- useWhenExpression -----")
            return when(obj) {
                1 -> "Integer"
                "test" -> "String"
                is Long -> "Long"
                is Boolean -> "boolean"
                else -> "Unknown"
            }
        }

        fun useRangeToCheckNoInRange(value: Int) {
            println("----- useRangeToCheckNoInRange -----")
            val limit = 10
            if(value in 1..limit){
                println("$value in range")
                return
            }

            println("$value not in range")
        }

        fun useOfRangeToCheckNoOutOfRange(itemList: List<String>) {
            println("----- useOfRangeToCheckNoOutOfRange -----")
            if (-1 !in 1..itemList.lastIndex) {
                println("-1 is out of range")
            }

            if(itemList.size !in itemList.indices) {
                println("list size is out of valid list indices range, too")
            }
        }

        fun useOfCollectionToPrintResults(itemList: List<String?>) {
            println("----- useOfCollectionToPrintResults -----")
            for (item in itemList) {
                println(item)
                 // Or
                item?.let {
                    println("Using Null check : $it")
                }
            }
        }

        fun useOfCollectionToCheckContainsObject(itemList: List<String>) {
            println("----- useOfCollectionToCheckContainsObject -----")
            when {
                "android" in itemList -> println("Google Product")
                "windows" in itemList -> println("Microsoft Product")
                "ios" in itemList -> println("Apple Product")
            }
        }

        fun useOfCollectionWithMapAndFilter(itemList: List<String>) {
            println("----- useOfCollectionWithMapAndFilter -----")
            itemList.filter { it.startsWith("b") }
                .sortedBy { it }
                .map { it.toUpperCase() }
                .forEachIndexed { index, s ->  println("Value at $index is $s")}
        }
    }
}