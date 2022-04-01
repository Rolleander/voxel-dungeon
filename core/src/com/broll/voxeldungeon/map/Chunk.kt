package com.broll.voxeldungeon.map

import com.badlogic.gdx.math.Vector3
import com.broll.voxeldungeon.objects.WorldObject
import java.util.*

class Chunk {
    var location: Vector3? = null
        private set
    var data: ChunkData? = null
        private set
    var isInWorld = false
    val objects: List<WorldObject> = ArrayList()

    fun initLocation(x: Int, y: Int, z: Int) {
        location = Vector3(x.toFloat(), y.toFloat(), z.toFloat())
    }

    fun initData(data: ChunkData?) {
        this.data = data
    }

    val isEmptyChunk: Boolean
        get() = data == null

}