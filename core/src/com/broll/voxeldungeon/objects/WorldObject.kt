package com.broll.voxeldungeon.objects

import com.badlogic.gdx.graphics.g3d.Model
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.physics.bullet.dynamics.btRigidBody
import com.badlogic.gdx.physics.bullet.linearmath.btMotionState

class WorldObject(model: Model?, var body: btRigidBody) : ModelInstance(model) {
    var motionState: MyMotionState

    class MyMotionState : btMotionState() {
        var transform: Matrix4? = null
        override fun getWorldTransform(worldTrans: Matrix4) {
            worldTrans.set(transform)
        }

        override fun setWorldTransform(worldTrans: Matrix4) {
            transform!!.set(worldTrans)
        }
    }

    init {
        motionState = MyMotionState()
        motionState.transform = transform
        body.motionState = motionState
    }
}