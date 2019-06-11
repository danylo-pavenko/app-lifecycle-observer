package com.dansdev.libalo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.dansdev.libapplifecycleobserver.AppLifecycleObserver
import com.dansdev.libapplifecycleobserver.listener.addAppLifecycleListener

class SecondActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "SecondActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)
        setupListeners()
    }

    override fun onResume() {
        super.onResume()
        Log.d(javaClass.simpleName, AppLifecycleObserver.instance?.lastOpenedActivity()?.localClassName)
    }

    private fun setupListeners() {
        AppLifecycleObserver.instance?.addAppLifecycleListener(javaClass.simpleName) {
            onAppStart { Log.d(TAG, "onAppStart") }
            onAppResumed { _, _ -> Log.d(TAG, "onAppResumed") }
            onAppPaused { _, _ -> Log.d(TAG, "onAppPaused") }
            onAppClose { Log.d(TAG, "onAppClose") }
            onAppConfigurationChanged { configuration -> Log.d(TAG, "onAppConfigurationChanged") }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        AppLifecycleObserver.instance?.removeListener(javaClass.simpleName)
    }
}
