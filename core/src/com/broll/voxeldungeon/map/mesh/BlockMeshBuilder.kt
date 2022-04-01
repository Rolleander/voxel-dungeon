package com.broll.voxeldungeon.map.mesh

import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.FloatArray
import com.broll.voxeldungeon.map.ChunkData
import com.broll.voxeldungeon.shader.VertexAttributes

object BlockMeshBuilder {
    const val DIRECTIONS = 6
    fun createMesh(voxels: Array<Array<ByteArray>>): Mesh {
        val fa = FloatArray()
        for (x in 0 until ChunkData.Companion.CHUNK_SIZE) {
            for (y in 0 until ChunkData.Companion.CHUNK_SIZE) {
                for (z in 0 until ChunkData.Companion.CHUNK_SIZE) {
                    val voxel = VoxelUtils.getVoxelObject(voxels[x][y][z])
                    val texture = voxel!!.texture
                    if (voxel!!.isVisible) {
                        val pos = Vector3(x.toFloat(), y.toFloat(), z.toFloat())
                        for (direction in 0 until DIRECTIONS) {
                            val visible = MeshUtils.isSideVisible(voxels, x, y, z, direction)
                            if (visible) {
                                val texPos = MeshUtils.getTexturePosition(texture!![direction])
                                MeshUtils.addVerticeRect(fa, direction, pos, texPos)
                            }
                        }
                    }
                }
            }
        }
        val mesh = Mesh(true, fa.size, 0, VertexAttributes.position, VertexAttributes.normal,
                VertexAttributes.textureCoords, VertexAttributes.occlusion)
        mesh.setVertices(fa.items)
        return mesh
    }
}