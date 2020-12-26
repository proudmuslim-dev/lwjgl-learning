package com.proudmuslim.lwjgl.learning.listeners

import org.lwjgl.glfw.GLFW.GLFW_RELEASE
import org.lwjgl.glfw.GLFW.GLFW_PRESS

class KeyListenerKt private constructor() {

    private val keyPressed = BooleanArray(350)

    companion object {
        private var instance: KeyListenerKt? = null

        @JvmStatic fun get(): KeyListenerKt? {
            when(KeyListenerKt.instance) {
                null -> KeyListenerKt.instance = KeyListenerKt();
            }

            return KeyListenerKt.instance
        }

        @JvmStatic fun keyCallback(window: Long, key: Int, scancode: Int, action: Int, mods: Int) {
            when(action) {
                GLFW_PRESS -> get()!!.keyPressed[key] = true
                GLFW_RELEASE -> get()!!.keyPressed[key] = false
            }
        }

        @JvmStatic fun isKeyPressed(keyCode: Int): Boolean = get()!!.keyPressed[keyCode]
    }

}
