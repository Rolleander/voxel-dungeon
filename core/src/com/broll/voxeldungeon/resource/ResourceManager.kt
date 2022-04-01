package com.broll.voxeldungeon.resource

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.assets.AssetManager
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g3d.Model

class ResourceManager {
    private val manager: AssetManager
    fun startLoading() {
        var files = Gdx.files.internal(TEXTURES_PATH).list()
        for (file in files) {
            println("load " + file.name())
            manager.load(TEXTURES_PATH + file.name(), Texture::class.java)
        }
        files = Gdx.files.internal(MODELS_PATH).list()
        for (file in files) {
            if (file.name().toLowerCase().endsWith(".obj")) {
                manager.load(MODELS_PATH + file.name(), Model::class.java)
            }
        }
        manager.finishLoading()
    }

    fun getTexture(name: String): Texture {
        return manager.get(TEXTURES_PATH + name, Texture::class.java)
    }

    fun getModel(model: String): Model {
        return manager.get(MODELS_PATH + model, Model::class.java)
    }

    companion object {
        const val RESOURCE_PATH = "data/"
        private const val TEXTURES_PATH = RESOURCE_PATH + "textures/"
        private const val MODELS_PATH = RESOURCE_PATH + "models/"
    }

    init {
        manager = AssetManager()
    }
}