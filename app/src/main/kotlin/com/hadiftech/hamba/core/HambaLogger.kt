package com.hadiftech.hamba.core

import android.util.Log

class HambaLogger {

    private val TAG = Constants.LOGGER_TAG
    private val Logs_Enabled = true

    private fun buildMsg(msg: String): String {

        val buffer = StringBuilder()
        val stackTraceElement = Thread.currentThread().stackTrace[4]

        buffer.append("[ ")
        buffer.append(Thread.currentThread().name)
        buffer.append(": ")
        buffer.append(stackTraceElement.fileName)
        buffer.append(": ")
        buffer.append(stackTraceElement.lineNumber)
        buffer.append(": ")
        buffer.append(stackTraceElement.methodName)
        buffer.append("() ] ===> ")
        buffer.append(msg)

        return buffer.toString()
    }

    fun v(msg: String) {
        if (Logs_Enabled && Log.isLoggable(TAG, Log.VERBOSE)) {
            Log.v(TAG, buildMsg(msg))
        }
    }

    fun d(msg: String) {
        if (Logs_Enabled && Log.isLoggable(TAG, Log.DEBUG)) {
            Log.d(TAG, buildMsg(msg))
        }
    }

    fun i(msg: String) {
        if (Logs_Enabled && Log.isLoggable(TAG, Log.INFO)) {
            Log.i(TAG, buildMsg(msg))
        }
    }

    fun w(msg: String) {
        if (Logs_Enabled && Log.isLoggable(TAG, Log.WARN)) {
            Log.w(TAG, buildMsg(msg))
        }
    }

    fun w(msg: String, e: Exception) {
        if (Logs_Enabled && Log.isLoggable(TAG, Log.WARN)) {
            Log.w(TAG, buildMsg(msg), e)
        }
    }

    fun e(msg: String) {
        if (Logs_Enabled && Log.isLoggable(TAG, Log.ERROR)) {
            Log.e(TAG, buildMsg(msg))
        }
    }

    fun e(msg: String, e: Exception) {
        if (Logs_Enabled && Log.isLoggable(TAG, Log.ERROR)) {
            Log.e(TAG, buildMsg(msg), e)
        }
    }

}