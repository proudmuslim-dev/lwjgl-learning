package com.proudmuslim.lwjgl.learning.listeners;

import static org.lwjgl.glfw.GLFW.GLFW_RELEASE;
import static org.lwjgl.glfw.GLFW.GLFW_PRESS;

public class MouseListener {
    private static MouseListener instance;

    private double posX, posY, finalX, finalY;
    private double scrollX, scrollY;

    private boolean mouseButtonPressed[] = new boolean[3];
    private boolean isDragging;

    private MouseListener() {
        this.scrollX = 0.0;
        this.scrollY = 0.0;
        this.finalX = 0.0;
        this.finalY = 0.0;
        this.posX = 0.0;
        this.posY = 0.0;
    }

    public static MouseListener get() {
        if(MouseListener.instance == null) {
            MouseListener.instance = new MouseListener();
        }

        return MouseListener.instance;
    }
    public static void cursorPositionCallback(long window, double xpos, double ypos) {
        get().isDragging = get().mouseButtonPressed[0] || get().mouseButtonPressed[1] || get().mouseButtonPressed[2];

        get().finalX = get().posX;
        get().finalY = get().posY;
        get().posX = xpos;
        get().posY = ypos;
    }

    public static void mouseButtonCallback(long window, int button, int action, int mods) {
        switch(action) {
            case GLFW_PRESS:
                if(button > get().mouseButtonPressed.length) {
                    get().mouseButtonPressed[button] = true;
                }
                break;

            case GLFW_RELEASE:
                if(button > get().mouseButtonPressed.length) {
                    get().mouseButtonPressed[button] = false;
                    get().isDragging = false;
                }
                break;
        }
    }


    public static void scrollCallback(long window, double xoffset, double yoffset) {
        get().scrollX = xoffset;
        get().scrollY = yoffset;

    }

    public static void endFrame() {
        get().finalX = get().posX;
        get().finalY = get().posY;
        get().scrollX = 0.0;
        get().scrollY = 0.0;
    }

    public static boolean buttonDown(int button) {
        if(button > get().mouseButtonPressed.length) {
            return get().mouseButtonPressed[button];
        } else {
            return false;
        }
    }

    public static float getDx() { return (float)(get().finalX - get().posX); }
    public static float getDy() { return (float)(get().finalY - get().posY); }

    public static float getScrollX() { return (float)get().scrollX; }
    public static float getScrollY() { return (float)get().scrollY; }

    public static boolean isDragging() { return get().isDragging; }

    public static float getX() { return (float)get().posX; }
    public static float getY() { return (float)get().posY; }

}
