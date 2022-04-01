package com.broll.voxeldungeon.physics

import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.collision.btCollisionShape
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody.btRigidBodyConstructionInfo
import com.badlogic.gdx.utils.Disposable
import java.util.*

class ObjectConstructor(shape: btCollisionShape, mass: Float) : Disposable {
    private val shape: btCollisionShape
    private val info: btRigidBodyConstructionInfo
    fun construct(): btRigidBody {
        return btRigidBody(info)
    }

    override fun dispose() {
        shape.dispose()
        info.dispose()
    }

    companion object {
        private val localInertia = Vector3()
        var constructors: MutableList<ObjectConstructor> = ArrayList()
    }

    init {
        localInertia[0f, 0f] = 0f
        this.shape = shape
        if (mass > 0f) shape.calculateLocalInertia(mass, localInertia) else localInertia[0f, 0f] = 0f
        info = btRigidBodyConstructionInfo(mass, null, shape, localInertia)
        constructors.add(this)
    }
}