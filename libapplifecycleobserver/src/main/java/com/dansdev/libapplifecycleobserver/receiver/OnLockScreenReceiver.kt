package com.dansdev.libapplifecycleobserver.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dansdev.libapplifecycleobserver.listener.AppLifecycleListener

class OnLockScreenReceiver(private val lifecycleListeners: List<AppLifecycleListener>) : BroadcastReceiver() {

    private var isLocked = false

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let { data ->
            when (data.action) {
                Intent.ACTION_SCREEN_OFF -> {
                    isLocked = true
                    lifecycleListeners.forEach { it.onAppPaused(true) }
                }
                Intent.ACTION_USER_PRESENT -> {
                    if (isLocked) {
                        lifecycleListeners.forEach { it.onAppResumed(true) }
                    }
                }
            }
        }
    }
}