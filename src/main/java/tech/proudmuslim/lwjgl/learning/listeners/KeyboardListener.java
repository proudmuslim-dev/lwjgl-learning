package tech.proudmuslim.lwjgl.learning.listeners;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class KeyboardListener {
    private static KeyboardListener instance;
    private KeyboardListener() {}

    private boolean keyPressed[] = new boolean[350];

    public static KeyboardListener get() {
        if(KeyboardListener.instance == null) {
            KeyboardListener.instance = new KeyboardListener();
        }

        return KeyboardListener.instance;
    }


    public static void keyCallback(long window, int key, int scancode, int action, int mods) {
        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        } else if(action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
    }

    public static boolean isKeyPressed(int keyCode) { return get().keyPressed[keyCode]; }

}
