package tech.proudmuslim.lwjgl.learning.scenes;

import tech.proudmuslim.lwjgl.learning.Window;

public class LevelScene extends SceneMgr{
    public LevelScene() {
        System.out.println("Inside Level scene");
        Window.changeScene(0);
    }

    @Override
    public void deltaTime(float dt) {}

    @Override
    public void update(float dt) {}
}
