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
    }
}