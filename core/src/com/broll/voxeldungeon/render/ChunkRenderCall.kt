package com.broll.voxeldungeon.render

import com.broll.voxeldungeon.map.Chunk

interface ChunkRenderCall {
    fun prepare()
    fun finished()
    fun renderChunk(chunk: Chunk?)
}