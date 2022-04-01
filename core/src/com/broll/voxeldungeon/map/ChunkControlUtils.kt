package com.broll.voxeldungeon.map

object ChunkControlUtils {
    fun logAllChunks(chunks: ChunkContainer) {
        try {
            val minX = chunks.getLaneEndX(false)
            val maxX = chunks.getLaneEndX(true)
            for (x in minX..maxX) {
                val horizontalChunkLane = chunks.getHorizontalChunkLane(x)
                val minZ = horizontalChunkLane!!.getLaneEndZ(false)
                val maxZ = horizontalChunkLane.getLaneEndZ(true)
                for (z in minZ..maxZ) {
                    val verticalChunkLane = horizontalChunkLane.getVerticalChunkLane(z)
                    val minY = verticalChunkLane!!.getChunkEndY(false)
                    val maxY = verticalChunkLane.getChunkEndY(true)
                    for (y in minY..maxY) {
                        val chunk = verticalChunkLane.getChunk(y)
                        println("x: " + x + "  z: " + z + "  y: " + y + "  empty:" + chunk!!.isEmptyChunk)
                    }
                }
            }
        } catch (e: ChunkNotFoundException) {
            e.printStackTrace()
        }
    }

    fun getOrCreateHorizontalChunkLane(chunks: ChunkContainer, x: Int): HorizontalChunkLane? {
        if (!chunks.existsLane(x)) {
            // fill up horizontal lanes until given x
            val forward = x >= 0
            val nextX = chunks.getNextLaneX(forward)
            if (forward) {
                for (i in nextX..x) {
                    chunks.addHorizontalChunkLane(HorizontalChunkLane(), forward)
                }
            } else {
                println("$nextX  $x")
                for (i in nextX downTo x) {
                    println("add")
                    chunks.addHorizontalChunkLane(HorizontalChunkLane(), forward)
                }
            }
        }
        return try {
            chunks.getHorizontalChunkLane(x)
        } catch (e: ChunkNotFoundException) {
            // should never happen
            e.printStackTrace()
            null
        }
    }

    fun getOrCreateVerticalChunkLane(lane: HorizontalChunkLane?, z: Int): VerticalChunkLane? {
        if (!lane!!.existsLane(z)) {
            // fill up vertical lanes until given z
            val forward = z >= 0
            val nextZ = lane.getNextLaneZ(forward)
            if (forward) {
                for (i in nextZ..z) {
                    lane.addVerticalChunkLine(VerticalChunkLane(), forward)
                }
            } else {
                for (i in nextZ downTo z) {
                    lane.addVerticalChunkLine(VerticalChunkLane(), forward)
                }
            }
        }
        return try {
            lane.getVerticalChunkLane(z)
        } catch (e: ChunkNotFoundException) {
            // should never happen
            e.printStackTrace()
            null
        }
    }

    fun getOrCreateChunk(lane: VerticalChunkLane?, y: Int): Chunk? {
        if (!lane!!.existsChunk(y)) {
            // fill up vertical lanes until given z
            val forward = y >= 0
            val nextY = lane.getNextChunkY(forward)
            if (forward) {
                for (i in nextY..y) {
                    lane.addChunk(Chunk(), forward)
                }
            } else {
                for (i in nextY downTo y) {
                    lane.addChunk(Chunk(), forward)
                }
            }
        }
        return try {
            lane.getChunk(y)
        } catch (e: ChunkNotFoundException) {
            // should never happen
            e.printStackTrace()
            null
        }
    }
}