package com.broll.voxeldungeon.map

import com.badlogic.gdx.math.Vector2

class VoxelObjects {
    fun getVoxelObject(id: Byte): Voxel {
        val v = Voxel(id)
        if (id.toInt() == -1) {
            v.isVisible = false
        } else {
            v.setDefaultBoundingBox()
            v.setTexture(Vector2((id % 10).toFloat(), (id / 10).toFloat()))
        }
        return v
    }
}