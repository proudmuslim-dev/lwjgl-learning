package tech.proudmuslim.lwjgl.learning.util

import java.lang.System

class Time {
    companion object {
        @JvmStatic val timeStarted: Long = System.nanoTime()
        @JvmStatic fun getTime(): Float {
            return ((System.nanoTime() - timeStarted) * 1E-9).toFloat()
        }
    }
}
