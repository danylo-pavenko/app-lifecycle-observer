package com.dansdev.libapplifecycleobserver.listener

interface AppLifecycleListener {

    fun onAppStart()

    fun onAppPaused(byLocked: Boolean)

    fun onAppResumed(byUnlocked: Boolean)

    fun onAppClose()
}