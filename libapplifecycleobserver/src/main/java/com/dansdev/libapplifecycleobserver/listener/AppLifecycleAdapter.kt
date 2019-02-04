package com.dansdev.libapplifecycleobserver.listener

import android.app.Activity
import android.content.res.Configuration
import com.dansdev.libapplifecycleobserver.AppLifecycleObserver

class AppLifecycleAdapter : AppLifecycleListener {

    private var _appStartListener: (() -> Unit)? = null
    private var _appPausedListener: ((Activity?, Boolean) -> Unit)? = null
    private var _appResumedListener: ((Activity?, Boolean) -> Unit)? = null
    private var _appCloseListener: (() -> Unit)? = null
    private var _appConfigurationChangedListener: ((Configuration?) -> Unit)? = null

    fun onAppStart(appStartListener: () -> Unit) {
        this._appStartListener = appStartListener
    }

    fun onAppPaused(appPaused: (Activity?, Boolean) -> Unit) {
        this._appPausedListener = appPaused
    }

    fun onAppResumed(appResumed: (Activity?, Boolean) -> Unit) {
        this._appResumedListener = appResumed
    }

    fun onAppClose(appClose: () -> Unit) {
        this._appCloseListener = appClose
    }

    fun onAppConfigurationChanged(appConfigurationChangedListener: (Configuration?) -> Unit) {
        this._appConfigurationChangedListener = appConfigurationChangedListener
    }

    override fun onAppStart() {
        _appStartListener?.invoke()
    }

    override fun onAppPaused(activity: Activity?, byLocked: Boolean) {
        _appPausedListener?.invoke(activity, byLocked)
    }

    override fun onAppResumed(activity: Activity?, byUnlocked: Boolean) {
        _appResumedListener?.invoke(activity, byUnlocked)
    }

    override fun onAppClose() {
        _appCloseListener?.invoke()
    }
    override fun onAppConfigurationChanged(newConfig: Configuration?) {
        _appConfigurationChangedListener?.invoke(newConfig)
    }
}

fun AppLifecycleObserver.addAppLifecycleListener(tag: String, func: AppLifecycleAdapter.() -> Unit) {
    val listener = AppLifecycleAdapter()
    listener.func()
    addListener(tag, listener)
}