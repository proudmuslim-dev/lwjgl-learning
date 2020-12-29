package tech.proudmuslim.lwjgl.learning.scenes;

public abstract class SceneMgr {
    public SceneMgr() {}

    public abstract void deltaTime(float dt);
    public abstract void update(float dt);

}
