package com.broll.voxeldungeon.render

import com.badlogic.gdx.math.Vector3
import com.broll.voxeldungeon.map.ChunkData
import com.broll.voxeldungeon.map.ChunkNotFoundException
import com.broll.voxeldungeon.map.Map

class ChunkLocationSelector(private val map: Map) {
    private var horizontalChunkRange = 0
    private var verticalChunkRange = 0
    fun setHorizontalChunkRange(horizontalChunkRange: Int) {
        this.horizontalChunkRange = horizontalChunkRange
    }

    fun setVerticalChunkRange(verticalChunkRange: Int) {
        this.verticalChunkRange = verticalChunkRange
    }

    fun selectNearChunks(location: Vector3, renderCalls: List<ChunkRenderCall>) {
        val x = location.x
        val y = location.y
        val z = location.z
        val chunkX = Math.floor(x / ChunkData.Companion.CHUNK_SIZE.toDouble()).toInt()
        val chunkY = Math.floor(y / ChunkData.Companion.CHUNK_SIZE.toDouble()).toInt()
        val chunkZ = Math.floor(z / ChunkData.Companion.CHUNK_SIZE.toDouble()).toInt()
        for (call in renderCalls) {
            call.prepare()
        }
        val chunks = map.chunks
        for (cx in chunkX - horizontalChunkRange..chunkX + horizontalChunkRange) {
            try {
                val horizontalChunkLane = chunks!!.getHorizontalChunkLane(cx)
                for (cz in chunkZ - horizontalChunkRange..chunkZ + horizontalChunkRange) {
                    try {
                        val verticalChunkLane = horizontalChunkLane!!.getVerticalChunkLane(cz)
                        for (cy in chunkY - verticalChunkRange..chunkY + verticalChunkRange) {
                            try {
                                val chunk = verticalChunkLane!!.getChunk(cy)
                                for (call in renderCalls) {
                                    call.renderChunk(chunk)
                                }
                            } catch (e: ChunkNotFoundException) {
                            }
                        }
                    } catch (e: ChunkNotFoundException) {
                    }
                }
            } catch (e: ChunkNotFoundException) {
            }
        }
        for (call in renderCalls) {
            call.finished()
        }
    }

}