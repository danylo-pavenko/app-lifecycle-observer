package com.dansdev.libalo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.dansdev.libapplifecycleobserver.AppLifecycleObserver

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
    }

    override fun onResume() {
        super.onResume()
        Log.d(javaClass.simpleName, AppLifecycleObserver.instance?.lastOpenedActivity()?.localClassName)
    }
}
