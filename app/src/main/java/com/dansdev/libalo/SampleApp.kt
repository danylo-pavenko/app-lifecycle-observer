package com.dansdev.libalo

import android.app.Application
import android.util.Log
import com.dansdev.libapplifecycleobserver.AppLifecycleObserver
import com.dansdev.libapplifecycleobserver.listener.addAppLifecycleListener

class SampleApp : Application() {

    companion object {
        private const val TAG = "SampleApp"
    }

    override fun onCreate() {
        super.onCreate()
        AppLifecycleObserver.instance?.init(this)
        setupListeners()
    }

    private fun setupListeners() {
        AppLifecycleObserver.instance?.addAppLifecycleListener {
            onAppStart { Log.d(TAG, "onAppStart") }
            onAppResumed { Log.d(TAG, "onAppResumed") }
            onAppPaused { Log.d(TAG, "onAppPaused") }
            onAppClose { Log.d(TAG, "onAppClose") }
        }
    }
}