package com.dansdev.libapplifecycleobserver.listener

import android.app.Activity
import android.content.res.Configuration

interface AppLifecycleListener {

    fun onAppStart()

    fun onAppPaused(activity: Activity?, byLocked: Boolean)

    fun onAppResumed(activity: Activity?, byUnlocked: Boolean)

    fun onAppClose()

    fun onAppConfigurationChanged(newConfig: Configuration?)
}