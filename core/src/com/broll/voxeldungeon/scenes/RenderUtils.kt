package com.broll.voxeldungeon.scenes

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20

class RenderUtils {
    fun clearScreen() {
        Gdx.graphics.gL20.glClearColor(0f, 0f, 0f, 1f)
        Gdx.graphics.gL20.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT)
    }
}