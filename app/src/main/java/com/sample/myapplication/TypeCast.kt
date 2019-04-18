package com.sample.myapplication

class TypeCast {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
                println("Length is: ${getStringLength("test")}")
        }

        // Auto type cast
        fun getStringLength(obj: Any): Int? {
            if (obj !is String) {
                return null
            }
            return obj.length
        }
    }

}