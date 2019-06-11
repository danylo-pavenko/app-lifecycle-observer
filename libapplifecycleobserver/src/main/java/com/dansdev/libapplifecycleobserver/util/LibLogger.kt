package com.dansdev.libapplifecycleobserver.util

import android.util.Log
import com.dansdev.libapplifecycleobserver.BuildConfig

object LibLogger {

    var enable = BuildConfig.DEBUG

    private const val TAG = "AppLifecycler"

    fun debug(message: String) {
        if (enable) {
            Log.d(TAG, message)
        }
    }

    fun error(message: String) {
        if (enable) {
            Log.e(TAG, message)
        }
    }

    fun info(message: String) {
        if (enable) {
            Log.i(TAG, message)
        }
    }
}