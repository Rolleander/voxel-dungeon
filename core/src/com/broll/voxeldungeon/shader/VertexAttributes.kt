package com.broll.voxeldungeon.shader

import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.glutils.ShaderProgram

object VertexAttributes {
    var position: VertexAttribute? = null
    var normal: VertexAttribute? = null
    var textureCoords: VertexAttribute? = null
    var occlusion: VertexAttribute? = null

    init {
        position = VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE)
        normal = VertexAttribute(VertexAttributes.Usage.Normal, 3, ShaderProgram.NORMAL_ATTRIBUTE)
        textureCoords = VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE + "0")
        occlusion = VertexAttribute(VertexAttributes.Usage.Generic, 1, "a_occlusion")
    }
}