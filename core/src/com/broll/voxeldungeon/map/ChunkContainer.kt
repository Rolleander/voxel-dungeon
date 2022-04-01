package com.broll.voxeldungeon.map

import com.badlogic.gdx.utils.Array

class ChunkContainer {
    private val laneForwad = Array<HorizontalChunkLane>()
    private val laneBackward = Array<HorizontalChunkLane>()
    fun getLaneEndX(forward: Boolean): Int {
        return if (forward) {
            laneForwad.size - 1
        } else laneBackward.size * -1
    }

    fun existsLane(x: Int): Boolean {
        return x < laneForwad.size && x >= laneBackward.size * -1
    }

    fun getNextLaneX(forward: Boolean): Int {
        val nextX = getLaneEndX(forward)
        return if (forward) {
            nextX + 1
        } else nextX - 1
    }

    fun addHorizontalChunkLane(chunkLane: HorizontalChunkLane, forward: Boolean) {
        if (forward) {
            laneForwad.add(chunkLane)
            chunkLane.init(getLaneEndX(true))
        } else {
            laneBackward.add(chunkLane)
            chunkLane.init(getLaneEndX(false))
        }
    }

    @Throws(ChunkNotFoundException::class)
    fun getHorizontalChunkLane(x: Int): HorizontalChunkLane {
        if (x >= 0) {
            if (x < laneForwad.size) {
                return laneForwad[x]
            }
        } else {
            val chunkNr = x * -1 - 1
            if (chunkNr < laneBackward.size) {
                return laneBackward[chunkNr]
            }
        }
        throw ChunkNotFoundException("No horizontal chunk lane found for x=$x")
    }
}