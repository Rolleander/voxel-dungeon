package com.broll.voxeldungeon.physics

import com.broll.voxeldungeon.map.ChunkData
import java.util.*

object ChunkBlockMerger {
    fun debugMergeBlocks(data: ChunkData): Set<ChunkBlock> {
        val blocks: MutableSet<ChunkBlock> = HashSet()
        for (z in 0 until ChunkData.Companion.CHUNK_SIZE) {
            for (x in 0 until ChunkData.Companion.CHUNK_SIZE) {
                for (y in 0 until ChunkData.Companion.CHUNK_SIZE) {
                    if (data.getVoxel(x, y, z).toInt() != -1) {
                        blocks.add(ChunkBlock(x, y, z))
                    }
                }
            }
        }
        return blocks
    }

    fun mergeBlocks(data: ChunkData?): Set<ChunkBlock> {
        val slices = arrayOfNulls<Slice>(ChunkData.Companion.CHUNK_SIZE)

        // build slices
        for (y in 0 until ChunkData.Companion.CHUNK_SIZE) {
            slices[y] = mergeLines(data, y)
        }
        // merge slices
        for (y in 0 until ChunkData.Companion.CHUNK_SIZE - 1) {
            mergeSlices(slices[y], slices[y + 1])
        }
        // collect
        val blocks: MutableSet<ChunkBlock> = HashSet()
        for (z in 0 until ChunkData.Companion.CHUNK_SIZE) {
            for (x in 0 until ChunkData.Companion.CHUNK_SIZE) {
                for (y in 0 until ChunkData.Companion.CHUNK_SIZE) {
                    val block = slices[y]!!.lines[z]!!.blocks[x]
                    if (block != null) {
                        blocks.add(block)
                    }
                }
            }
        }
        return blocks
    }

    private fun mergeSlices(sliceBot: Slice?, sliceTop: Slice?) {
        for (z in 0 until ChunkData.Companion.CHUNK_SIZE) {
            for (x in 0 until ChunkData.Companion.CHUNK_SIZE) {
                val bBot = sliceBot!!.lines[z]!!.blocks[x]
                val bTop = sliceTop!!.lines[z]!!.blocks[x]
                if (bBot != null && bTop != null && bBot !== bTop) {
                    if (bBot.w == bTop.w && bBot.b == bTop.b) {
                        bBot.h += 1
                        for (i in 0 until bBot.w) {
                            for (j in 0 until bBot.b) {
                                sliceTop.lines[z + j]!!.blocks[x + i] = sliceBot.lines[z + j]!!.blocks[x + i]
                            }
                        }
                    }
                }
            }
        }
    }

    private fun mergeLines(data: ChunkData?, y: Int): Slice {
        val slice = Slice()
        // build lines
        for (z in 0 until ChunkData.Companion.CHUNK_SIZE) {
            slice.lines[z] = buildLine(data, z, y)
        }
        // merge lines
        for (z in ChunkData.Companion.CHUNK_SIZE - 1 downTo 1) {
            mergeLines(slice.lines[z], slice.lines[z - 1])
        }
        return slice
    }

    private fun mergeLines(lineNorth: Line?, lineSouth: Line?) {
        for (x in 0 until ChunkData.Companion.CHUNK_SIZE) {
            val bSouth = lineSouth!!.blocks[x]
            val bNorth = lineNorth!!.blocks[x]
            if (bSouth != null && bNorth != null && bSouth !== bNorth) {
                if (bNorth.w == bSouth.w) {
                    bNorth.b += 1
                    lineSouth.blocks[x] = lineNorth.blocks[x]
                }
            }
        }
    }

    private fun buildLine(data: ChunkData?, z: Int, y: Int): Line {
        val line = Line()
        var currentBlock: ChunkBlock? = null
        for (x in 0 until ChunkData.Companion.CHUNK_SIZE) {
            val v = data!!.getVoxel(x, y, z)
            if (v.toInt() != -1) {
                if (currentBlock == null) {
                    currentBlock = ChunkBlock(x, y, z)
                    line.blocks[x] = currentBlock
                } else {
                    currentBlock.w += 1
                }
            } else {
                currentBlock = null
            }
        }
        return line
    }

    private class Line {
        var blocks = arrayOfNulls<ChunkBlock>(ChunkData.Companion.CHUNK_SIZE)
    }

    private class Slice {
        var lines = arrayOfNulls<Line>(ChunkData.Companion.CHUNK_SIZE)
    }
}