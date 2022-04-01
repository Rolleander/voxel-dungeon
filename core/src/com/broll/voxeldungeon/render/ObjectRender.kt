package com.broll.voxeldungeon.render

import com.badlogic.gdx.graphics.g3d.Environment
import com.badlogic.gdx.graphics.g3d.ModelBatch
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.math.Vector3
import com.broll.voxeldungeon.player.Player
import com.broll.voxeldungeon.resource.ResourceManager

class ObjectRender(resourceManager: ResourceManager, player: Player) {
    private val environment: Environment
    private val modelBatch: ModelBatch
    private val instance: ModelInstance
    private val player: Player
    fun renderObjects(settings: RenderSettings?) {
        modelBatch.begin(settings!!.camera)
        modelBatch.render(instance, environment)
        //modelBatch.render(player.getPlayerModel().getModel(), environment);
        instance.transform.rotate(Vector3.Y, 1f)
        // instance.transform.rotate(Vector3.Z, 2);

        // instance.transform.translate(0.1f,0,0);
        modelBatch.end()
    }

    init {
        modelBatch = ModelBatch()
        environment = Environment()
        environment.set(ColorAttribute(ColorAttribute.AmbientLight, 1f, 1f, 1f, 1f))
        // environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f,
        // -0.8f, -0.2f));
        val model = resourceManager.getModel("ship.obj")
        instance = ModelInstance(model)
        instance.transform.translate(8f, 9f, 8f)
        //instance.transform.scale(1.f, 1.00f, 1.00f);
        val scale = 1f
        instance.transform.scale(scale, scale, scale)
        instance.calculateTransforms()
        this.player = player
        // new btPairCachingGhostObject();
    }
}