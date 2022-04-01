package com.broll.voxeldungeon.render

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.utils.BufferUtils
import com.broll.voxeldungeon.shader.ShaderCollection

class RenderSettings {

    var light: LightSource? = null
    var camera: PerspectiveCamera? = null
    var blockTexture: Texture? = null
    var shaderCollection: ShaderCollection? = null
    var ambientLight: Color? = null
    val maxAnisotropy: Float

    init {
        val buffer = BufferUtils.newFloatBuffer(64)
        Gdx.gl20.glGetFloatv(GL20.GL_MAX_TEXTURE_MAX_ANISOTROPY_EXT, buffer)
        maxAnisotropy = buffer[0]
        println("MAX ANISOTROPY $maxAnisotropy")
    }
}