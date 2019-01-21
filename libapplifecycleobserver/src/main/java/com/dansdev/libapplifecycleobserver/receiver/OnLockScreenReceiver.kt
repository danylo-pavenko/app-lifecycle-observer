package com.dansdev.libapplifecycleobserver.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dansdev.libapplifecycleobserver.listener.AppLifecycleListener

class OnLockScreenReceiver(private val lifecycleListeners: List<AppLifecycleListener>,
                           private val onPausedAppByActivity: () -> Boolean) : BroadcastReceiver() {

    private var isLocked = false

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let { data ->
            when (data.action) {
                Intent.ACTION_SCREEN_OFF -> {
                    if (!onPausedAppByActivity() && !isLocked) {
                        isLocked = true
                        lifecycleListeners.forEach { it.onAppPaused(null, true) }
                    }
                }
                Intent.ACTION_USER_PRESENT -> {
                    if (!onPausedAppByActivity() && isLocked) {
                        isLocked = false
                        lifecycleListeners.forEach { it.onAppResumed(null, true) }
                    }
                }
            }
        }
    }
}