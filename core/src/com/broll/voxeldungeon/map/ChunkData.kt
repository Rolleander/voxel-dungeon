package com.broll.voxeldungeon.map

import com.badlogic.gdx.graphics.Mesh
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.math.collision.BoundingBox
import com.broll.voxeldungeon.map.mesh.BlockMeshBuilder
import com.broll.voxeldungeon.objects.WorldObject

class ChunkData(fill: Byte) {
    var voxels: Array<Array<ByteArray>>
    val meshes: com.badlogic.gdx.utils.Array<Mesh?>
    val objects = com.badlogic.gdx.utils.Array<WorldObject>()
    var boundingBox: BoundingBox? = null
        private set

    fun updateMeshs(location: Vector3) {
        for (m in meshes) {
            m!!.dispose()
        }
        meshes.clear()
        val blockMesh = BlockMeshBuilder.createMesh(voxels)
        boundingBox = blockMesh.calculateBoundingBox()
        val matrix = Matrix4()
        location.scl(CHUNK_SIZE.toFloat())
        matrix.setToTranslation(location)
        boundingBox!!.mul(matrix)
        meshes.add(blockMesh)
    }

    fun getVoxel(x: Int, y: Int, z: Int): Byte {
        return if (isInChunk(x, y, z)) {
            voxels[x][y][z]
        } else 0
    }

    fun setVoxel(voxelId: Byte, x: Int, y: Int, z: Int) {
        if (isInChunk(x, y, z)) {
            voxels[x][y][z] = voxelId
        }
    }

    private fun isInChunk(x: Int, y: Int, z: Int): Boolean {
        if (x in 0 until CHUNK_SIZE) {
            if (y in 0 until CHUNK_SIZE) {
                if (z in 0 until CHUNK_SIZE) {
                    return true
                }
            }
        }
        return false
    }

    companion object {
        const val CHUNK_SIZE = 16
        const val BLOCK_MESH_INDEX = 0
    }

    init {
        voxels = Array(CHUNK_SIZE) { Array(CHUNK_SIZE) { ByteArray(CHUNK_SIZE) } }
        for (i in 0 until CHUNK_SIZE) {
            for (h in 0 until CHUNK_SIZE) {
                for (j in 0 until CHUNK_SIZE) {
                    voxels[i][h][j] = fill
                }
            }
        }
        meshes = com.badlogic.gdx.utils.Array()
    }
}