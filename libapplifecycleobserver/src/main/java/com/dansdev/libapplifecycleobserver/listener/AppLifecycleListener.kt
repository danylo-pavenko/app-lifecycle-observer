package com.dansdev.libapplifecycleobserver.listener

import android.app.Activity

interface AppLifecycleListener {

    fun onAppStart()

    fun onAppPaused(activity: Activity?, byLocked: Boolean)

    fun onAppResumed(activity: Activity?, byUnlocked: Boolean)

    fun onAppClose()
}