package com.broll.voxeldungeon.map

import com.badlogic.gdx.math.Vector3
import com.broll.voxeldungeon.map.mesh.VoxelUtils

class Map {
    val chunks = ChunkContainer()
    private val voxelObjects: VoxelObjects

    //gets chunk or throws excpetion if not existing
    @Throws(ChunkNotFoundException::class)
    fun getChunk(x: Int, y: Int, z: Int): Chunk? {
        val horizontalLane = chunks.getHorizontalChunkLane(x)
        val verticalLane = horizontalLane!!.getVerticalChunkLane(z)
        return verticalLane!!.getChunk(y)
    }

    // sets or replace chunk
    fun setChunkData(data: ChunkData?, x: Int, y: Int, z: Int) {
        val horizontalChunkLane = ChunkControlUtils.getOrCreateHorizontalChunkLane(chunks, x)
        val verticalChunkLane = ChunkControlUtils.getOrCreateVerticalChunkLane(horizontalChunkLane, z)
        val chunk = ChunkControlUtils.getOrCreateChunk(verticalChunkLane, y)
        chunk!!.initData(data)
        data!!.updateMeshs(Vector3(x.toFloat(), y.toFloat(), z.toFloat()))
    }

    fun log() {
        ChunkControlUtils.logAllChunks(chunks)
    }

    init {
        voxelObjects = VoxelObjects()
        VoxelUtils.init(voxelObjects)
    }
}