package com.dansdev.libapplifecycleobserver.listener

import com.dansdev.libapplifecycleobserver.AppLifecycleObserver

class AppLifecycleAdapter : AppLifecycleListener {

    private var _appStartListener: (() -> Unit)? = null
    private var _appPausedListener: ((Boolean) -> Unit)? = null
    private var _appResumedListener: ((Boolean) -> Unit)? = null
    private var _appCloseListener: (() -> Unit)? = null

    fun onAppStart(appStartListener: () -> Unit) {
        this._appStartListener = appStartListener
    }

    fun onAppPaused(appPaused: (Boolean) -> Unit) {
        this._appPausedListener = appPaused
    }

    fun onAppResumed(appResumed: (Boolean) -> Unit) {
        this._appResumedListener = appResumed
    }

    fun onAppClose(appClose: () -> Unit) {
        this._appCloseListener = appClose
    }

    override fun onAppStart() {
        _appStartListener?.invoke()
    }

    override fun onAppPaused(byLocked: Boolean) {
        _appPausedListener?.invoke(byLocked)
    }

    override fun onAppResumed(byUnlocked: Boolean) {
        _appResumedListener?.invoke(byUnlocked)
    }

    override fun onAppClose() {
        _appCloseListener?.invoke()
    }
}

fun AppLifecycleObserver.addAppLifecycleListener(func: AppLifecycleAdapter.() -> Unit) {
    val listener = AppLifecycleAdapter()
    listener.func()
    addListener(listener)
}