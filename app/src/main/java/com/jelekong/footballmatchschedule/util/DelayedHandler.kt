package com.jelekong.footballmatchschedule.util

import android.os.Handler

class DelayedHandler(var delay: Long = 1000) {

    /** The [Handler] object  */
    private val handler = Handler()

    fun doDelayedTask(task: () -> Unit) {
        // Stop execution of the previous handler
        handler.removeCallbacksAndMessages(null)

        // Create a new runnable
        val runnable = Runnable { task.invoke() }

        // Restart the handler with the specified delay
        handler.postDelayed(runnable, delay)
    }

    fun cancelDelayedTask() {
        // Stop execution of the previous handler
        handler.removeCallbacksAndMessages(null)
    }
}