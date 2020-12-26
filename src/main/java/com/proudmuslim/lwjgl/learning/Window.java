package com.proudmuslim.lwjgl.learning;

import com.proudmuslim.lwjgl.learning.listeners.KeyboardListener;
import com.proudmuslim.lwjgl.learning.listeners.KeyListenerKt;
import com.proudmuslim.lwjgl.learning.listeners.MouseListener;

import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.glfw.GLFW.*;

import org.lwjgl.opengl.*;
import org.lwjgl.system.*;
import org.lwjgl.glfw.*;
import org.lwjgl.*;

import java.nio.*;


public class Window {

    // The window handle
    private long window;

    public void run() {
        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the window callbacks and destroy the window
        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);

        // Terminate GLFW and free the error callback
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        int width = 1920;
        int height = 1080;
        String title = "First time messing with LWJGL";


        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if ( !glfwInit() )
            throw new IllegalStateException("Unable to initialize GLFW");

        // Configure GLFW
        glfwDefaultWindowHints(); // optional, the current window hints are already the default
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE); // the window will stay hidden after creation
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        // Create the window
        window = glfwCreateWindow(width, height, title, NULL, NULL);
        if ( window == NULL )
            throw new RuntimeException("Failed to create the GLFW window");

        // Set mouse related callbacks
        glfwSetCursorPosCallback(window, MouseListener::cursorPositionCallback);
        glfwSetMouseButtonCallback(window, MouseListener::mouseButtonCallback);
        glfwSetScrollCallback(window, MouseListener::scrollCallback);

        // Set key callbacks
        // glfwSetKeyCallback(window, KeyListenerKt::keyCallback);
        glfwSetKeyCallback(window, KeyboardListener::keyCallback);

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        /*
        glfwSetKeyCallback(window, (window, key, scancode, action, mods) -> {
        });
*/
        // Get the thread stack and push a new frame
        try ( MemoryStack stack = stackPush() ) {
            IntBuffer pWidth = stack.mallocInt(1); // int*
            IntBuffer pHeight = stack.mallocInt(1); // int*

            // Get the window size passed to glfwCreateWindow
            glfwGetWindowSize(window, pWidth, pHeight);

            // Get the resolution of the primary monitor
            GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

            // Center the window
            glfwSetWindowPos(
                    window,
                    (vidmode.width() - pWidth.get(0)) / 2,
                    (vidmode.height() - pHeight.get(0)) / 2
            );
        } // the stack frame is popped automatically

        // Make the OpenGL context current
        glfwMakeContextCurrent(window);
        // Enable v-sync
        glfwSwapInterval(1);

        // Make the window visible
        glfwShowWindow(window);
    }

    private void loop() {
        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        // Set the clear color
        glClearColor(0.0f, 0.0f, 0.0f, 1.0f);

        // Run the rendering loop until the user has attempted to close
        // the window or has pressed the ESCAPE key.
        while ( !glfwWindowShouldClose(window) ) {
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer

            glfwSwapBuffers(window); // swap the color buffers

            // Replace with KeyListenerKt to test kotlin class
            if(KeyboardListener.isKeyPressed(GLFW_KEY_SPACE)) {
                System.out.println("Space bar pressed");
            } else if(KeyboardListener.isKeyPressed(GLFW_KEY_LEFT_CONTROL)) {
                System.out.println("Left control key pressed");
            } else if (KeyboardListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
                System.out.println("Escape key pressed, exiting...");
                glfwSetWindowShouldClose(window, true);
            }

            // Poll for window events. The key callback above will only be
            // invoked during this call.
            glfwPollEvents();
        }
    }

}



