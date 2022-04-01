package com.broll.voxeldungeon.scenes.impl

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input.Buttons
import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.broll.voxeldungeon.resource.ResourceManager
import com.broll.voxeldungeon.scenes.Scene
import com.broll.voxeldungeon.scenes.SceneControl
import com.broll.voxeldungeon.scenes.impl.InGameScene

class MainMenuScene(resourceManager: ResourceManager?, sceneControl: SceneControl) : Scene(resourceManager, sceneControl) {
    private val batch = SpriteBatch()
    private val background: Sprite
    override fun render() {
        batch.begin()
        background.draw(batch)
        batch.end()
    }

    override fun onShow() {
        Gdx.input.inputProcessor = object : InputAdapter() {
            override fun touchDown(screenX: Int, screenY: Int, pointer: Int, button: Int): Boolean {
                if (button == Buttons.LEFT) {
                    switchScene(InGameScene::class.java)
                }
                return true
            }
        }
    }

    override fun onQuit() {}

    init {
        background = Sprite(resources!!.getTexture("wall.jpg"))
    }
}