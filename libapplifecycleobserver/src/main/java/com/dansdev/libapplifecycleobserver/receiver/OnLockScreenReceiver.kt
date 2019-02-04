package com.dansdev.libapplifecycleobserver.receiver

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.dansdev.libapplifecycleobserver.listener.AppLifecycleListener
import java.util.*

class OnLockScreenReceiver(listeners: WeakHashMap<String, AppLifecycleListener>,
                           private val onPausedAppByActivity: () -> Boolean) : BroadcastReceiver() {

    val lifecycleListeners = WeakHashMap<String, AppLifecycleListener>()

    init {
        for ((key, listener) in listeners) {
            lifecycleListeners[key] = listener
        }
    }

    private var isLocked = false

    override fun onReceive(context: Context?, intent: Intent?) {
        intent?.let { data ->
            when (data.action) {
                Intent.ACTION_SCREEN_OFF -> {
                    if (!onPausedAppByActivity() && !isLocked) {
                        isLocked = true
                        lifecycleListeners.values.forEach { it.onAppPaused(null, true) }
                    }
                }
                Intent.ACTION_USER_PRESENT -> {
                    if (!onPausedAppByActivity() && isLocked) {
                        isLocked = false
                        lifecycleListeners.values.forEach { it.onAppResumed(null, true) }
                    }
                }
            }
        }
    }
}