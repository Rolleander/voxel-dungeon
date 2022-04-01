package com.broll.voxeldungeon.create

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.broll.voxeldungeon.map.mesh.MeshUtils
import com.broll.voxeldungeon.render.LightSource
import com.broll.voxeldungeon.render.RenderSettings
import com.broll.voxeldungeon.resource.ResourceManager
import com.broll.voxeldungeon.shader.ShaderCollection

class GameInit {
    var renderSettings: RenderSettings? = null
        private set

    fun initDebugGame(resources: ResourceManager) {
        //	Texture blockTexture = resources.getTexture("blocks_.png");
        //	MeshUtils.initTextureDimensions(blockTexture, 256);
        val blockTexture = resources.getTexture("blocks.png")
        MeshUtils.initTextureDimensions(blockTexture, 64)


        //	blockTexture.setFilter(TextureFilter.Linear, TextureFilter.Linear);
        renderSettings = RenderSettings()
        renderSettings!!.ambientLight = (Color(0.3f, 0.3f, 0.3f, 1.0f))
        //renderSettings.setAmbientLight(new Color(0.01f, 0.01f, 0.01f, 1.0f));
        renderSettings!!.blockTexture = blockTexture
        val camera = PerspectiveCamera(67f, Gdx.graphics.width.toFloat(), Gdx.graphics.height.toFloat())
        camera.near = 0.1f
        camera.far = 300f
        camera.update()
        renderSettings!!.camera=camera
        val light = LightSource(0, 10, 0)
        renderSettings!!.light=light
        val shaderCollection = ShaderCollection()
        shaderCollection.init()
        renderSettings!!.shaderCollection=shaderCollection
    }

}