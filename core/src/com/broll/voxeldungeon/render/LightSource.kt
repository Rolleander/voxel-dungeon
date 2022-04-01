package com.broll.voxeldungeon.render

import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.graphics.VertexAttribute
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3

class LightSource(x: Int, y: Int, z: Int) {
    var pos: Vector3
    val color: FloatArray
    fun getLightViewPosition(view: Matrix4?): FloatArray {
        val position = FloatArray(3)
        val tempVec = Vector3(pos)
        //tempVec.mul(view);
        position[0] = tempVec.x
        position[1] = tempVec.y
        position[2] = tempVec.z
        return position
    }

    companion object {
        var lightMesh: Mesh? = null
    }

    init {
        pos = Vector3(x.toFloat(), y.toFloat(), z.toFloat())
        color = FloatArray(4)
        color[0] = 1f
        color[1] = 1f
        color[2] = 1f
        color[3] = 1f
        if (lightMesh == null) {
            val verts = FloatArray(180)
            var i = 0
            // Front
            verts[i++] = -1f // x1
            verts[i++] = -1f// y1
            verts[i++] = 1f
            verts[i++] = 0.5f // u1
            verts[i++] = 0.5f // v1
            verts[i++] = 1f // x2
            verts[i++] = -1f // y2
            verts[i++] = 1f
            verts[i++] = 1f // u2
            verts[i++] = 0.5f // v2
            verts[i++] = 1f // x3
            verts[i++] = 1f // y2
            verts[i++] = 1f
            verts[i++] = 1f // u3
            verts[i++] = 0f // v3
            verts[i++] = 1f // x3
            verts[i++] = 1f // y2
            verts[i++] = 1f
            verts[i++] = 1f // u3
            verts[i++] = 0f // v3
            verts[i++] = -1f // x4
            verts[i++] = 1f // y4
            verts[i++] = 1f
            verts[i++] = 0.5f // u4
            verts[i++] = 0f // v4
            verts[i++] = -1f // x1
            verts[i++] = -1f// y1
            verts[i++] = 1f
            verts[i++] = 0.5f // u1
            verts[i++] = 0.5f // v1

            // top
            verts[i++] = -1f // x1
            verts[i++] = 1f // y1
            verts[i++] = 1f // z1
            verts[i++] = 0f
            verts[i++] = 0.5f
            verts[i++] = 1f// x1
            verts[i++] = 1f // y1
            verts[i++] = 1f // z1
            verts[i++] = 0.5f
            verts[i++] = 0.5f
            verts[i++] = 1f // x1
            verts[i++] = 1f // y1
            verts[i++] = -1f // z1
            verts[i++] = 0.5f
            verts[i++] = 0f
            verts[i++] = 1f // x1
            verts[i++] = 1f // y1
            verts[i++] = -1f // z1
            verts[i++] = 0.5f
            verts[i++] = 0f
            verts[i++] = -1f // x1
            verts[i++] = 1f // y1
            verts[i++] = -1f // z1
            verts[i++] = 0f
            verts[i++] = 0f
            verts[i++] = -1f// x1
            verts[i++] = 1f // y1
            verts[i++] = 1f // z1
            verts[i++] = 0f
            verts[i++] = 0.5f

            // Right
            verts[i++] = 1f // x1
            verts[i++] = -1f// y1
            verts[i++] = 1f // z1
            verts[i++] = 0.5f
            verts[i++] = 0.5f
            verts[i++] = 1f // x1
            verts[i++] = -1f // y1
            verts[i++] = -1f// z1
            verts[i++] = 1f
            verts[i++] = 0.5f
            verts[i++] = 1f // x1
            verts[i++] = 1f // y1
            verts[i++] = -1f // z1
            verts[i++] = 1f
            verts[i++] = 0f
            verts[i++] = 1f // x1
            verts[i++] = 1f // y1
            verts[i++] = -1f // z1
            verts[i++] = 1f
            verts[i++] = 0f
            verts[i++] = 1f // x1
            verts[i++] = 1f // y1
            verts[i++] = 1f // z1
            verts[i++] = 0.5f
            verts[i++] = 0f
            verts[i++] = 1f // x1
            verts[i++] = -1f // y1
            verts[i++] = 1f // z1
            verts[i++] = 0.5f
            verts[i++] = 0.5f

            // bot
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = 0f
            verts[i++] = 1f
            verts[i++] = 1f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = 0.5f
            verts[i++] = 1f
            verts[i++] = 1f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = 0.5f
            verts[i++] = 0.5f
            verts[i++] = 1f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = 0.5f
            verts[i++] = 0.5f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = 0f
            verts[i++] = 0.5f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = 0f
            verts[i++] = 1f

            // Back
            verts[i++] = 1f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = 0.5f
            verts[i++] = 0.5f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = 0.5f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = 0f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = 0f
            verts[i++] = 1f
            verts[i++] = 1f
            verts[i++] = -1f
            verts[i++] = 0.5f
            verts[i++] = 0f
            verts[i++] = 1f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = 0.5f
            verts[i++] = 0.5f

            // Left
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = 0.5f
            verts[i++] = 0.5f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = 1f
            verts[i++] = 0.5f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = 1f
            verts[i++] = 1f
            verts[i++] = 0f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = 1f
            verts[i++] = 1f
            verts[i++] = 0f
            verts[i++] = -1f
            verts[i++] = 1f
            verts[i++] = -1f
            verts[i++] = 0.5f
            verts[i++] = 0f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = -1f
            verts[i++] = 0.5f
            verts[i++] = 0.5f
            lightMesh = Mesh(true, 36, 0,
                    VertexAttribute(VertexAttributes.Usage.Position, 3, ShaderProgram.POSITION_ATTRIBUTE),
                    VertexAttribute(VertexAttributes.Usage.TextureCoordinates, 2, ShaderProgram.TEXCOORD_ATTRIBUTE))
            lightMesh!!.setVertices(verts)
        }
    }
}