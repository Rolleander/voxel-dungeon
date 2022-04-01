package com.broll.voxeldungeon.map

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox

class Voxel(private val voxelType: Byte) {
    val texture = arrayOfNulls<Vector2>(6)
    var isVisible = true
    var boundingBox: BoundingBox? = null
        private set

    fun setTexture(text: Vector2?) {
        for (i in texture.indices) {
            texture[i] = text
        }
    }

    fun setDefaultBoundingBox() {
        boundingBox = BoundingBox(Vector3(0f, 0f, 0f), Vector3(1f, 1f, 1f))
    }

    fun getVoxelType(): Int {
        return voxelType.toInt()
    }

    val isInvisible: Boolean
        get() = !isVisible

}