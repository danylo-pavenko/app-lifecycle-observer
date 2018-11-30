package com.dansdev.libalo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dansdev.libapplifecycleobserver.AppLifecycleObserver
import com.dansdev.libapplifecycleobserver.listener.addAppLifecycleListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupAppLifecycleListener()
    }

    private fun setupAppLifecycleListener() {
        AppLifecycleObserver.instance?.addAppLifecycleListener {
            onAppStart { addLog("onAppStart") }
            onAppResumed { _, _ -> addLog("onAppResumed") }
            onAppPaused { _, _ -> addLog("onAppPaused") }
            onAppClose { addLog("onAppClose") }
        }
    }

    private fun addLog(log: String) {
        val lastActivity = AppLifecycleObserver.instance?.lastOpenedActivity()
        tv_log.append("$log -> ${lastActivity?.javaClass?.simpleName}\n")
    }
}
