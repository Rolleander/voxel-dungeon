package com.broll.voxeldungeon.map

import com.badlogic.gdx.utils.Array

class HorizontalChunkLane {
    /**
     * chunksForwad contains vertical chunks from 0 to +z max chunksBackward
     * contains vertical cunks from -1 to -z max
     */
    private val laneForwad = Array<VerticalChunkLane>()
    private val laneBackward = Array<VerticalChunkLane>()
    var x = 0
        private set

    fun init(x: Int) {
        this.x = x
    }

    fun existsLane(z: Int): Boolean {
        return z < laneForwad.size && z >= laneBackward.size * -1
    }

    fun getLaneEndZ(forward: Boolean): Int {
        return if (forward) {
            laneForwad.size - 1
        } else laneBackward.size * -1
    }

    fun getNextLaneZ(forward: Boolean): Int {
        val nextZ = getLaneEndZ(forward)
        return if (forward) {
            nextZ + 1
        } else nextZ - 1
    }

    fun addVerticalChunkLine(chunkLane: VerticalChunkLane, forward: Boolean) {
        if (forward) {
            laneForwad.add(chunkLane)
            chunkLane.init(x, getLaneEndZ(true))
        } else {
            laneBackward.add(chunkLane)
            chunkLane.init(x, getLaneEndZ(false))
        }
    }

    @Throws(ChunkNotFoundException::class)
    fun getVerticalChunkLane(z: Int): VerticalChunkLane {
        if (z >= 0) {
            if (z < laneForwad.size) {
                return laneForwad[z]
            }
        } else {
            val chunkNr = z * -1 - 1
            if (chunkNr < laneBackward.size) {
                return laneBackward[chunkNr]
            }
        }
        throw ChunkNotFoundException("No vertical chunk lane found for z=$z")
    }

}