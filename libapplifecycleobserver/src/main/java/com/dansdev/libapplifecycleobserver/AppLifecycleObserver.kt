package com.dansdev.libapplifecycleobserver

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Intent
import android.content.IntentFilter
import android.content.res.Configuration
import android.os.Bundle
import com.dansdev.libapplifecycleobserver.listener.AppLifecycleListener
import com.dansdev.libapplifecycleobserver.receiver.OnLockScreenReceiver
import com.dansdev.libapplifecycleobserver.util.LibLogger

/**
 * Observer of app states.
 *
 * Handle state: app START, RESUME, PAUSE, STOP.
 *
 * Add listener @see [AppLifecycleListener]
 * Add adapter for handle only selected method @see [com.dansdev.libapplifecycleobserver.listener.AppLifecycleAdapter]
 *
 * First need to call @see [AppLifecycleObserver.init]
 */
class AppLifecycleObserver : Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {

    companion object {
        @SuppressLint("StaticFieldLeak")
        var instance: AppLifecycleObserver? = null
            get() {
                if (field == null) {
                    field = AppLifecycleObserver()
                }
                return field
            }
            private set

        private val lifecycleListeners = HashMap<String, AppLifecycleListener>()
    }

    private var app: Application? = null

    private var activities = mutableListOf<String>()
    private var isPaused = false
    private var isLastActivityFinished = false
    private var lockScreenReceiver: OnLockScreenReceiver? = null
    private var currentActivity: Activity? = null

    fun init(app: Application) {
        this.app = app
        app.registerActivityLifecycleCallbacks(this)
        app.registerComponentCallbacks(this)
        LibLogger.info("init library")
    }

    fun addListener(tag: String, lifecycleListener: AppLifecycleListener) {
        if (app == null) throw IllegalStateException("First need to call init(), and after that add listeners")
        lifecycleListeners[tag] = lifecycleListener
        LibLogger.info("addListener for tag: $tag | listeners: ${lifecycleListeners.size}")
    }

    fun removeListener(tag: String) {
        if (lifecycleListeners.containsKey(tag)) {
            lifecycleListeners.remove(tag)
        } else {
            System.out.println("is tag for listener not register")
        }
        LibLogger.debug("removeListener for tag: $tag")
    }

    fun removeAllListeners() {
        lifecycleListeners.clear()
        LibLogger.debug("remove ALL Listener")
    }

    fun lastOpenedActivity(): Activity? = currentActivity

    override fun onActivityPaused(activity: Activity?) {
        activity?.let { isLastActivityFinished = it.isFinishing }
        LibLogger.debug("onActivityPaused")
    }

    override fun onTrimMemory(level: Int) {
        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN && !isLastActivityFinished) {
            isPaused = true
            lifecycleListeners.forEach { it.value.onAppPaused(null, false) }
            LibLogger.info("onApp PAUSED listeners: ${lifecycleListeners.size}")
        } else {
            LibLogger.debug("onTrimMemory level: $level")
        }
    }

    override fun onActivityResumed(activity: Activity?) {
        activity?.let { activity ->
            currentActivity = activity
            if (isPaused) {
                lifecycleListeners.forEach { it.value.onAppResumed(activity, false) }
                LibLogger.info("onApp RESUMED listeners: ${lifecycleListeners.size}")
                isPaused = false
            }
        }
    }

    override fun onActivityDestroyed(activity: Activity?) {
        activity?.let { activities.remove(activity.javaClass.simpleName) }
        handleChangeActivities(fromDestroy = true)
        LibLogger.debug("onActivityDestroyed")
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
        handleAppIsCreated()
        activity?.let {
            currentActivity = it
            activities.add(activity.javaClass.simpleName)
        }
        handleChangeActivities()
        LibLogger.debug("onActivityCreated")
    }

    private fun handleAppIsCreated() {
        if (activities.isEmpty()) {
            lifecycleListeners.forEach { it.value.onAppStart() }
            LibLogger.info("onApp START listeners: ${lifecycleListeners.size}")
            registerLockReceiver()
        }
    }

    private fun registerLockReceiver() {
        val intentFilter = IntentFilter(Intent.ACTION_SCREEN_OFF)
        intentFilter.addAction(Intent.ACTION_USER_PRESENT)

        unregisterLockReceiver()

        LibLogger.info("registerLockReceiver listeners: ${lifecycleListeners.size}")
        lockScreenReceiver = OnLockScreenReceiver(lifecycleListeners) { isPaused }
        app?.registerReceiver(lockScreenReceiver, intentFilter)
    }

    private fun handleChangeActivities(fromDestroy: Boolean = false) {
        if (activities.isEmpty() || (fromDestroy && isPaused)) {
            currentActivity = null
            lifecycleListeners.forEach { it.value.onAppClose() }
            LibLogger.info("onApp CLOSE listeners: ${lifecycleListeners.size}")
            unregisterLockReceiver()
        }
    }

    private fun unregisterLockReceiver() {
        LibLogger.info("unregisterLockReceiver listeners: ${lifecycleListeners.size}")
        lockScreenReceiver?.let {
            try {
                app?.unregisterReceiver(it)
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                lockScreenReceiver = null
            }
        }
    }

    override fun onActivityStopped(activity: Activity?) {
        LibLogger.debug("onActivityStopped")
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
        LibLogger.debug("onActivitySaveInstanceState")
    }

    override fun onLowMemory() {
        LibLogger.debug("onLowMemory")
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
        lifecycleListeners.forEach { it.value.onAppConfigurationChanged(newConfig) }
        LibLogger.debug("onConfigurationChanged")
    }

    override fun onActivityStarted(activity: Activity?) {
        LibLogger.debug("onActivityStarted")
    }


}