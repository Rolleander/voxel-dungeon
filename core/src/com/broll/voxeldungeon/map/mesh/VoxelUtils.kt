package com.broll.voxeldungeon.map.mesh

import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.collision.BoundingBox
import com.broll.voxeldungeon.map.Voxel
import com.broll.voxeldungeon.map.VoxelObjects

object VoxelUtils {
    private var voxelObjects: VoxelObjects? = null
    fun init(voxelObjects: VoxelObjects?) {
        VoxelUtils.voxelObjects = voxelObjects
    }

    fun getVoxelBoundingBox(voxelId: Byte): BoundingBox? {
        return voxelObjects!!.getVoxelObject(voxelId).boundingBox
    }

    fun isVoxelInvisible(voxelId: Byte): Boolean {
        return voxelObjects!!.getVoxelObject(voxelId).isInvisible
    }

    fun getVoxelObject(voxelId: Byte): Voxel? {
        return voxelObjects!!.getVoxelObject(voxelId)
    }

    fun getTexture(voxelId: Byte): Array<Vector2?>? {
        return voxelObjects!!.getVoxelObject(voxelId).texture
    }
}