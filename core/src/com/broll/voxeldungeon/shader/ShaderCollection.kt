package com.broll.voxeldungeon.shader

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.utils.GdxRuntimeException
import com.broll.voxeldungeon.resource.ResourceManager

class ShaderCollection {
    var blockShader: ShaderProgram? = null
        private set

    fun init() {
        blockShader = loadShader("blocks")
    }

    private fun loadShader(shader: String): ShaderProgram {
        val shaderProgram = ShaderProgram(
                Gdx.files.internal("$SHADER_PATH$shader.vert").readString(), Gdx.files.internal(
                "$SHADER_PATH$shader.frag").readString())
        if (!shaderProgram.isCompiled) {
            throw GdxRuntimeException("Couldn't compile simple shader: " + shaderProgram.log)
        }
        return shaderProgram
    }

    companion object {
        private val SHADER_PATH: String = ResourceManager.Companion.RESOURCE_PATH + "shaders/"
    }
}