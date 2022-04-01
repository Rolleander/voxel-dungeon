package com.broll.voxeldungeon.scenes

import com.broll.voxeldungeon.resource.ResourceManager

abstract class Scene(protected var resources: ResourceManager?, private val sceneControl: SceneControl) {
    fun switchScene(scene: Class<out Scene?>?) {
        sceneControl.openScene(scene)
    }

    abstract fun render()
    abstract fun onShow()
    abstract fun onQuit()

    companion object {
        protected val renderUtils = RenderUtils()
    }

}