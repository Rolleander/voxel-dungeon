package com.broll.voxeldungeon.map.mesh

import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.utils.FloatArray
import com.broll.voxeldungeon.map.ChunkData

object MeshUtils {
    const val NORMAL_TOP = 0
    const val NORMAL_LEFT = 1
    const val NORMAL_RIGHT = 2
    const val NORMAL_BACK = 3
    const val NORMAL_FRONT = 4
    const val NORMAL_BOT = 5
    var TEXTURE_WIDTH = 0f
    var TEXTURE_HEIGHT = 0f
    fun getTexturePosition(texture: Vector2?): Vector2 {
        val x = texture!!.x * TEXTURE_WIDTH
        val y = texture.y * TEXTURE_HEIGHT
        return Vector2(x, y)
    }

    fun initTextureDimensions(texture: Texture?, blockSize: Int) {
        val width = texture!!.width.toFloat()
        val height = texture.height.toFloat()
        val size = blockSize.toFloat()
        TEXTURE_WIDTH = size / width
        TEXTURE_HEIGHT = size / height
    }

    fun isSideVisible(voxels: Array<Array<ByteArray>>, x: Int, y: Int, z: Int, direction: Int): Boolean {
        val max: Int = ChunkData.Companion.CHUNK_SIZE - 1
        val min = 0
        when (direction) {
            NORMAL_TOP -> return y == max || VoxelUtils.isVoxelInvisible(voxels[x][y + 1][z])
            NORMAL_BOT -> return y == min || VoxelUtils.isVoxelInvisible(voxels[x][y - 1][z])
            NORMAL_BACK -> return z == min || VoxelUtils.isVoxelInvisible(voxels[x][y][z - 1])
            NORMAL_FRONT -> return z == max || VoxelUtils.isVoxelInvisible(voxels[x][y][z + 1])
            NORMAL_RIGHT -> return x == max || VoxelUtils.isVoxelInvisible(voxels[x + 1][y][z])
            NORMAL_LEFT -> return x == min || VoxelUtils.isVoxelInvisible(voxels[x - 1][y][z])
        }
        return false
    }

    fun addVertice(fa: FloatArray, direction: Int, pos: Vector3, texture: Vector2, occlusion: Float) {
        fa.add(pos.x) // x
        fa.add(pos.y) // y
        fa.add(pos.z) // z
        addNormal(fa, direction)
        fa.add(texture.x)
        fa.add(texture.y)
        fa.add(occlusion)
    }

    fun addVerticeRect(fa: FloatArray, direction: Int, startPos: Vector3, texture: Vector2?) {
        val corners = arrayOfNulls<Vector3>(4)
        when (direction) {
            NORMAL_TOP -> {
                corners[0] = Vector3(0f, 1f, 1f)
                corners[1] = Vector3(1f, 1f, 1f)
                corners[2] = Vector3(1f, 1f, 0f)
                corners[3] = Vector3(0f, 1f, 0f)
                addVerticeRect(fa, corners, direction, startPos, texture)
            }
            NORMAL_BOT -> {
                corners[0] = Vector3(0f, 0f, 0f)
                corners[1] = Vector3(1f, 0f, 0f)
                corners[2] = Vector3(1f, 0f, 1f)
                corners[3] = Vector3(0f, 0f, 1f)
                addVerticeRect(fa, corners, direction, startPos, texture)
            }
            NORMAL_LEFT -> {
                corners[0] = Vector3(0f, 0f, 0f)
                corners[1] = Vector3(0f, 0f, 1f)
                corners[2] = Vector3(0f, 1f, 1f)
                corners[3] = Vector3(0f, 1f, 0f)
                addVerticeRect(fa, corners, direction, startPos, texture)
            }
            NORMAL_RIGHT -> {
                corners[0] = Vector3(1f, 0f, 1f)
                corners[1] = Vector3(1f, 0f, 0f)
                corners[2] = Vector3(1f, 1f, 0f)
                corners[3] = Vector3(1f, 1f, 1f)
                addVerticeRect(fa, corners, direction, startPos, texture)
            }
            NORMAL_FRONT -> {
                corners[0] = Vector3(0f, 0f, 1f)
                corners[1] = Vector3(1f, 0f, 1f)
                corners[2] = Vector3(1f, 1f, 1f)
                corners[3] = Vector3(0f, 1f, 1f)
                addVerticeRect(fa, corners, direction, startPos, texture)
            }
            NORMAL_BACK -> {
                corners[0] = Vector3(1f, 0f, 0f)
                corners[1] = Vector3(0f, 0f, 0f)
                corners[2] = Vector3(0f, 1f, 0f)
                corners[3] = Vector3(1f, 1f, 0f)
                addVerticeRect(fa, corners, direction, startPos, texture)
            }
        }
    }

    private fun addVerticeRect(fa: FloatArray, addVectors: Array<Vector3?>, direction: Int, startPos: Vector3,
                               texture: Vector2?) {
        val occlusion = 1f
        addVertice(fa, direction, Vector3(startPos).add(addVectors[0]), Vector2(texture).add(0f, TEXTURE_HEIGHT), occlusion)
        addVertice(fa, direction, Vector3(startPos).add(addVectors[1]), Vector2(texture).add(TEXTURE_WIDTH, TEXTURE_HEIGHT),
                occlusion)
        for (i in 0..1) {
            addVertice(fa, direction, Vector3(startPos).add(addVectors[2]),
                    Vector2(texture).add(TEXTURE_WIDTH, 0f), occlusion)
        }
        addVertice(fa, direction, Vector3(startPos).add(addVectors[3]),
                Vector2(texture).add(0f, 0f), occlusion)
        addVertice(fa, direction, Vector3(startPos).add(addVectors[0]), Vector2(texture).add(0f, TEXTURE_HEIGHT), occlusion)
    }

    fun addNormal(fa: FloatArray, direction: Int) {
        when (direction) {
            NORMAL_TOP -> {
                fa.add(0f) // Normal X
                fa.add(1f) // Normal Y
                fa.add(0f) // Normal Z
            }
            NORMAL_LEFT -> {
                fa.add(-1f) // Normal X
                fa.add(0f) // Normal Y
                fa.add(0f) // Normal Z
            }
            NORMAL_BACK -> {
                fa.add(0f) // Normal X
                fa.add(0f) // Normal Y
                fa.add(-1f) // Normal Z
            }
            NORMAL_BOT -> {
                fa.add(0f) // Normal X
                fa.add(-1f) // Normal Y
                fa.add(0f) // Normal Z
            }
            NORMAL_FRONT -> {
                fa.add(0f) // Normal X
                fa.add(0f) // Normal Y
                fa.add(1f) // Normal Z
            }
            NORMAL_RIGHT -> {
                fa.add(1f) // Normal X
                fa.add(0f) // Normal Y
                fa.add(0f) // Normal Z
            }
        }
    }
}