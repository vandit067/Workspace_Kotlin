package com.sample.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.sample.myapplication.basics.demoFunctions
import com.sample.myapplication.basics.demoNullSafety
import com.sample.myapplication.basics.demoVariablesAndProperties
import com.sample.myapplication.collections.demoCollectionsAndRanges
import com.sample.myapplication.collections.demoSequences
import com.sample.myapplication.coroutines.demoCoroutinesBasics
import com.sample.myapplication.coroutines.demoFlow
import com.sample.myapplication.databinding.ActivityMainBinding
import com.sample.myapplication.delegation.demoDelegation
import com.sample.myapplication.functional.demoHigherOrderFunctions
import com.sample.myapplication.functional.demoScopeFunctions
import com.sample.myapplication.oop.demoClassesAndInheritance
import com.sample.myapplication.oop.demoDataClasses
import com.sample.myapplication.oop.demoExtensions
import com.sample.myapplication.oop.demoObjectsAndCompanions
import com.sample.myapplication.oop.demoSealedClasses
import com.sample.myapplication.oop.showToast
import com.sample.myapplication.types.demoGenerics
import com.sample.myapplication.types.demoTypeSystem
import com.sample.myapplication.types.demoValueClasses

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.activityMainTvTest.text = "Kotlin 2.0 Feature Demos — check Logcat for output"
        showToast("Kotlin demos running…")

        // Run synchronous demos on the main thread (output → Logcat)
        runSyncDemos()

        // Run coroutine demos on a background thread to avoid blocking the UI
        Thread {
            demoCoroutinesBasics()
            demoFlow()
        }.start()
    }

    private fun runSyncDemos() {
        // basics
        demoVariablesAndProperties()
        demoFunctions()
        demoNullSafety()

        // oop
        demoClassesAndInheritance()
        demoDataClasses()
        demoSealedClasses()
        demoObjectsAndCompanions()
        demoExtensions()

        // functional
        demoHigherOrderFunctions()
        demoScopeFunctions()

        // collections
        demoCollectionsAndRanges()
        demoSequences()

        // types
        demoTypeSystem()
        demoGenerics()
        demoValueClasses()

        // delegation
        demoDelegation()
    }
}
