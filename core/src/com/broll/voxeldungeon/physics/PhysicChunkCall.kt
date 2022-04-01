package com.broll.voxeldungeon.physics

import com.broll.voxeldungeon.map.Chunk
import com.broll.voxeldungeon.render.ChunkRenderCall
import java.util.*

class PhysicChunkCall(private val world: PhysicWorld) : ChunkRenderCall {
    private var lastRenderedChunks: List<Chunk?> = ArrayList()
    private var renderedChunks: MutableList<Chunk?> = ArrayList()
    override fun prepare() {}
    override fun renderChunk(chunk: Chunk?) {
        world.addChunk(chunk)
        renderedChunks.add(chunk)
    }

    override fun finished() {
        for (chunk in lastRenderedChunks) {
            if (!renderedChunks.contains(chunk)) {
                world.removeChunk(chunk)
            }
        }
        lastRenderedChunks = renderedChunks
        renderedChunks = ArrayList()
    }

}