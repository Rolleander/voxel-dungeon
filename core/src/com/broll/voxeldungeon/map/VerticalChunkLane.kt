package com.broll.voxeldungeon.map

import com.badlogic.gdx.utils.Array

class VerticalChunkLane {
    private val chunksForwad = Array<Chunk>()
    private val chunksBackward = Array<Chunk>()
    var x = 0
        private set
    var z = 0
        private set

    fun init(x: Int, z: Int) {
        this.x = x
        this.z = z
    }

    fun existsChunk(y: Int): Boolean {
        return y < chunksForwad.size && y >= chunksBackward.size * -1
    }

    fun getChunkEndY(forward: Boolean): Int {
        return if (forward) {
            chunksForwad.size - 1
        } else {
            chunksBackward.size * -1
        }
    }

    fun getNextChunkY(forward: Boolean): Int {
        val nextY = getChunkEndY(forward)
        return if (forward) {
            nextY + 1
        } else nextY - 1
    }

    fun addChunk(chunk: Chunk, forward: Boolean) {
        if (forward) {
            chunksForwad.add(chunk)
            chunk.initLocation(x, getChunkEndY(true), z)
        } else {
            chunksBackward.add(chunk)
            chunk.initLocation(x, getChunkEndY(false), z)
        }
    }

    @Throws(ChunkNotFoundException::class)
    fun getChunk(y: Int): Chunk {
        if (y >= 0) {
            if (y < chunksForwad.size) {
                return chunksForwad[y]
            }
        } else {
            val chunkNr = y * -1 - 1
            if (chunkNr < chunksBackward.size) {
                return chunksBackward[chunkNr]
            }
        }
        throw ChunkNotFoundException("No chunk found for y=$y")
    }

}