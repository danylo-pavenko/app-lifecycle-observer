package com.dansdev.libalo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
        AppLifecycleObserver.instance?.addAppLifecycleListener("MainActivity") {
            onAppStart { addLog("onAppStart") }
            onAppResumed { _, _ -> addLog("onAppResumed") }
            onAppPaused { _, _ -> addLog("onAppPaused") }
            onAppClose { addLog("onAppClose") }
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d(javaClass.simpleName, AppLifecycleObserver.instance?.lastOpenedActivity()?.localClassName)
    }

    private fun addLog(log: String) {
        val lastActivity = AppLifecycleObserver.instance?.lastOpenedActivity()
        tv_log.append("$log -> ${lastActivity?.javaClass?.simpleName}\n")
        Log.d(javaClass.simpleName, log)
    }

    fun onOpenSecondActivity(v: View) {
        val intent = Intent(this, SecondActivity::class.java)
        startActivity(intent)
    }
}
