package tech.proudmuslim.lwjgl.learning.scenes;


import tech.proudmuslim.lwjgl.learning.listeners.KeyboardListener;
import tech.proudmuslim.lwjgl.learning.Window;

import java.awt.event.KeyEvent;

public class LevelEditor extends SceneMgr {
    private boolean changingScene = false;
    float timeChange = 2.0f;

    public LevelEditor() {
        System.out.println("Inside level editor");
    }

    @Override
    public void deltaTime(float dt) {}

    @Override
    public void update(float dt) {
        if (!changingScene && KeyboardListener.isKeyPressed(KeyEvent.VK_SPACE)) {
            changingScene = true;
        }

        if (changingScene && timeChange > 0) {
            timeChange -= dt;
            Window.r -= dt * 5.0f;
            Window.g -= dt * 5.0f;
            Window.b -= dt * 5.0f;
        } else if (changingScene) {
            Window.changeScene(1);
        }
    }

}
