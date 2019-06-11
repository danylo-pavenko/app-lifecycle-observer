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
        AppLifecycleObserver.instance?.removeAllListeners()
        AppLifecycleObserver.instance?.addAppLifecycleListener(javaClass.simpleName) {
            onAppStart { Log.d(TAG, "onAppStart") }
            onAppResumed { _, _ -> Log.d(TAG, "onAppResumed") }
            onAppPaused { _, _ -> Log.d(TAG, "onAppPaused") }
            onAppClose { Log.d(TAG, "onAppClose") }
            onAppConfigurationChanged { configuration -> Log.d(TAG, "onAppConfigurationChanged") }
        }
    }
}