package com.broll.voxeldungeon

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
object DesktopLauncher {
    @JvmStatic
    fun main(arg: Array<String>) {
        val config = Lwjgl3ApplicationConfiguration()
        config.setForegroundFPS(60)
        val width = 1280
        val height = 720
        config.setWindowSizeLimits(width, height, width, height)
        config.setTitle("Voxel Dungeon")
        Lwjgl3Application(VoxelDungeon(), config)
    }
}