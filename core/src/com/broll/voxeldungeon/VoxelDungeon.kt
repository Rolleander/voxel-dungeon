package com.broll.voxeldungeon

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.physics.bullet.Bullet
import com.broll.voxeldungeon.resource.ResourceManager
import com.broll.voxeldungeon.scenes.SceneManager
import com.broll.voxeldungeon.scenes.impl.MainMenuScene

class VoxelDungeon : ApplicationAdapter() {
    private var resourceManager: ResourceManager? = null
    private var sceneManager: SceneManager? = null

    override fun create() {
        Gdx.app.log("Init", "Init Bullet Engine...")
        Bullet.init()
        Gdx.app.log("Init", "Start loading resources...")
        resourceManager = ResourceManager()
        resourceManager!!.startLoading()
        Gdx.app.log("Init", "Start creating scenes...")
        sceneManager = SceneManager()
        sceneManager!!.initScenes(resourceManager!!)
        Gdx.app.log("Init", "Finished loading!")
        Gdx.app.log("Init", "Start Game...")
        sceneManager!!.showStartScene(MainMenuScene::class.java)
        Gdx.app.log("Init", "Game started!")
    }

    override fun render() {
        sceneManager!!.currentScene!!.render()
    }

    override fun dispose() {}
    override fun resize(width: Int, height: Int) {}
    override fun pause() {}
    override fun resume() {}
}