package com.broll.voxeldungeon.scenes

import com.broll.voxeldungeon.resource.ResourceManager
import com.broll.voxeldungeon.scenes.impl.InGameScene
import com.broll.voxeldungeon.scenes.impl.MainMenuScene
import java.util.*

class SceneManager {
    private val scenes: MutableMap<Class<out Scene>, Scene>
    private val control: SceneControl
    var currentScene: Scene? = null
        private set

    fun initScenes(resource: ResourceManager) {
        addScene(MainMenuScene(resource, control))
        addScene(InGameScene(resource, control))
    }

    fun showStartScene(scene: Class<out Scene?>?) {
        control.openScene(scene)
    }

    private fun addScene(scene: Scene) {
        scenes[scene.javaClass] = scene
    }

    private inner class SceneControlImpl : SceneControl {
        override fun openScene(scene: Class<out Scene?>?) {
            if (currentScene != null) {
                currentScene!!.onQuit()
            }
            val newScene = scenes[scene]
            newScene!!.onShow()
            currentScene = newScene
        }
    }

    init {
        scenes = HashMap()
        control = SceneControlImpl()
    }
}