package com.sample.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        activity_main_tv_test.text = "Sum is : " + calculateSum()
        val title:String = "test app"
        showToast(title)
    }

    fun getA() : Int{
        return 2
    }

    fun getB() : Int{
        return 4
    }

    fun calculateSum(a:Int = 4, b:Int = 2) : Int{
        return (a+b)
    }
}
