package com.broll.voxeldungeon.player

import com.badlogic.gdx.math.Vector3

class Player {
    val playerModel: PlayerModel
    val playerMovement: PlayerMovement

    val location: Vector3
        get() = playerModel.model.transform.getTranslation(Vector3())

    fun teleport(x: Int, y: Int, z: Int) {
        playerModel.model.transform.translate(x.toFloat(), y.toFloat(), z.toFloat())
        playerModel.physic.worldTransform = playerModel.model.transform
    }

    fun update() {
        playerMovement.update()
    }

    fun resestMovement() {
        playerMovement.resetMovement()
    }

    val cameraLocation: Vector3
        get() {
            val v = location
            v.y -= 0.3f
            return v
        }

    init {
        playerModel = PlayerModel()
        playerMovement = PlayerMovement(playerModel)
        playerMovement.setSpeed(3f)
        playerMovement.setJumpSpeed(5f)
    }
}