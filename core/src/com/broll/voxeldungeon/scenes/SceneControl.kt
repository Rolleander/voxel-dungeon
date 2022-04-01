package com.broll.voxeldungeon.scenes

interface SceneControl {
    fun openScene(scene: Class<out Scene?>?)
}