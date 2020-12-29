package tech.proudmuslim.lwjgl.learning;

import tech.proudmuslim.lwjgl.learning.listeners.KeyboardListener;
import tech.proudmuslim.lwjgl.learning.listeners.MouseListener;
import tech.proudmuslim.lwjgl.learning.scenes.LevelEditor;
import tech.proudmuslim.lwjgl.learning.scenes.LevelScene;
import tech.proudmuslim.lwjgl.learning.scenes.SceneMgr;
import tech.proudmuslim.lwjgl.learning.util.Time;

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
    private static SceneMgr currentScene;
    public static float r, g, b, a; // Color
    private long window; // The window handle

    public static void changeScene(int newScene) {
        switch(newScene){
            case 0:
                currentScene = new LevelEditor();
                break;
            case 1:
                currentScene = new LevelScene();
                break;
            default:
                assert false : "Unknown Scene scene".replace("scene", Integer.toString(newScene)); // I should've done this in a better way but all of this code is extremely hacky so whatever
                break;
        }
    }

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

        this.r = 1.0f;
        this.g = 1.0f;
        this.b = 1.0f;
        this.a = 1.0f;


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

        float timeBegun = Time.getTime();
        float dt = -1.0f;
        float timeEnded;

        while ( !glfwWindowShouldClose(window) ) {
            // Set the clear color
            glClearColor(r, g, b, a);
            glfwPollEvents();

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT); // clear the framebuffer
            glfwSwapBuffers(window); // swap the color buffers

            changeScene(0);

            currentScene.update(dt);

            // Replace with KeyListenerKt to test kotlin class
            if (KeyboardListener.isKeyPressed(GLFW_KEY_ESCAPE)) {
                System.out.println("Escape key pressed, exiting...");
                glfwSetWindowShouldClose(window, true);
            }
            timeEnded = Time.getTime();
            dt = timeEnded - timeBegun;
            timeBegun = timeEnded;

        }
    }

}
