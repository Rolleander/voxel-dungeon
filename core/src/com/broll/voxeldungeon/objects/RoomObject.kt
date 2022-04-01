package com.broll.voxeldungeon.objects

import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody
import com.broll.voxeldungeon.map.Chunk
import com.broll.voxeldungeon.physics.ObjectConstructor
import com.broll.voxeldungeon.resource.ResourceManager

abstract class RoomObject {
    var `object`: WorldObject? = null
        protected set
    protected var modelName: String? = null
    protected var body: btRigidBody? = null
    var interactionRadius = 0f
        protected set
    protected var world: GameWorld? = null
    protected fun initBody(shape: btCollisionShape, mass: Float) {
        body = ObjectConstructor(shape, mass).construct()
    }

    protected fun initLocation(pos: Vector3?) {}
    protected fun playerInRange(): Boolean {
        return world!!.getDistanceToPlayer(`object`) <= interactionRadius
    }

    fun init(chunk: Chunk?, world: GameWorld?, resourceManager: ResourceManager) {
        this.world = world
        if (body == null || modelName == null) {
            throw RuntimeException("Can't init RoomObject with body:$body model:$modelName")
        }
        val model = resourceManager.getModel("$modelName.obj")
        `object` = WorldObject(model, body!!)
        `object`!!.userData = this
        //	object.t
    }

    abstract fun update(delta: Float)

}