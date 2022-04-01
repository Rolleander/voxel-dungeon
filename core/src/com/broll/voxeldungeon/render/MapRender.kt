package com.broll.voxeldungeon.render

import com.broll.voxeldungeon.map.Map
import java.util.*

class MapRender(private val map: Map) {
    private val chunkLocationSelector: ChunkLocationSelector
    private val chunkRender = ChunkRender()
    private val renderCalls: MutableList<ChunkRenderCall> = ArrayList()
    fun addRenderCall(call: ChunkRenderCall) {
        renderCalls.add(call)
    }

    fun renderMap(settings: RenderSettings?) {
        val camera = settings!!.camera
        val light = settings.light
        val blockTexture = settings.blockTexture
        val ambientLight = settings.ambientLight
        val blockShader = settings.shaderCollection!!.blockShader
        light!!.pos = camera!!.position
        // bind texture
        val blockTextureBind = 0
        blockTexture!!.bind(blockTextureBind)
        //anisotropische filterung
        //Gdx.gl20.glTexParameterf(GL20.GL_TEXTURE_2D, GL20.GL_TEXTURE_MAX_ANISOTROPY_EXT, Math.min(16, settings.getMaxAnisotropy()));
        blockShader!!.bind()
        blockShader.setUniform4fv("scene_light", light.color, 0, 4)
        blockShader.setUniformf("scene_ambient_light", ambientLight!!.r, ambientLight.g, ambientLight.b, ambientLight.a)
        blockShader.setUniformi("s_texture", blockTextureBind)
        blockShader.setUniformf("material_diffuse", 1f, 1f, 1f, 1f)
        blockShader.setUniformf("material_specular", 0.0f, 0.0f, 0.0f, 1f)
        blockShader.setUniformf("material_shininess", 0.5f)
        blockShader.setUniform3fv("u_lightPos", light!!.getLightViewPosition(camera!!.view), 0, 3)
        val position = camera!!.position
        chunkRender.setBlockShader(blockShader)
        chunkRender.setCamera(camera)
        chunkLocationSelector.selectNearChunks(position, renderCalls)
    }

    init {
        chunkLocationSelector = ChunkLocationSelector(map)
        chunkLocationSelector.setHorizontalChunkRange(5)
        chunkLocationSelector.setVerticalChunkRange(5)
        renderCalls.add(chunkRender)
    }
}