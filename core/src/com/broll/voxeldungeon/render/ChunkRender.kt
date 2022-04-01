package com.broll.voxeldungeon.render

import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.graphics.glutils.ShaderProgram
import com.badlogic.gdx.math.Matrix3
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.broll.voxeldungeon.map.Chunk
import com.broll.voxeldungeon.map.ChunkData

class ChunkRender : ChunkRenderCall {
    val cubeModel = Matrix4()
    val modelViewProjectionMatrix = Matrix4()
    val modelViewMatrix = Matrix4()
    val normalMatrix = Matrix3()
    private var blockShader: ShaderProgram? = null
    private var camera: PerspectiveCamera? = null
    fun setBlockShader(blockShader: ShaderProgram?) {
        this.blockShader = blockShader
    }

    fun setCamera(camera: PerspectiveCamera?) {
        this.camera = camera
    }

    override fun renderChunk(chunk: Chunk?) {
        val blockMesh = chunk!!.data!!.meshes[ChunkData.Companion.BLOCK_MESH_INDEX]
        // check if mesh is not empty
        if (blockMesh != null && blockMesh.numVertices > 0) {
            // check camera frustum
            val boundingBox = chunk.data!!.boundingBox
            if (camera!!.frustum.boundsInFrustum(boundingBox)) {
                val chunkPos = chunk.location
                val x: Float = chunkPos!!.x * ChunkData.Companion.CHUNK_SIZE
                val y: Float = chunkPos!!.y * ChunkData.Companion.CHUNK_SIZE
                val z: Float = chunkPos!!.z * ChunkData.Companion.CHUNK_SIZE
                val chunkLocation = Vector3(x, y, z)
                cubeModel.setToTranslation(chunkLocation)
                modelViewProjectionMatrix.set(camera!!.combined)
                modelViewProjectionMatrix.mul(cubeModel)
                modelViewMatrix.set(camera!!.view)
                modelViewMatrix.mul(cubeModel)
                normalMatrix.set(modelViewMatrix)
                blockShader!!.setUniformMatrix("normalMatrix", normalMatrix)
                blockShader!!.setUniformMatrix("u_modelViewMatrix", modelViewMatrix)
                blockShader!!.setUniformMatrix("u_mvpMatrix", modelViewProjectionMatrix)
                blockMesh.render(blockShader, GL20.GL_TRIANGLES)
            }
        }
    }

    override fun prepare() {}
    override fun finished() {}
}