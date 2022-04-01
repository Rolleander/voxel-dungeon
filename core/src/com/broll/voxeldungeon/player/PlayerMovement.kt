package com.broll.voxeldungeon.player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.math.Vector3

class PlayerMovement(private val playerModel: PlayerModel) {
    private val movement = Vector3(0f, 0f, 0f)
    private var speed = 0f
    private var jumpSpeed = 0f
    fun setJumpSpeed(jumpSpeed: Float) {
        this.jumpSpeed = jumpSpeed
    }

    fun setSpeed(speed: Float) {
        this.speed = speed
    }

    fun move(move: Vector3?) {
        movement.add(move)
    }

    fun update() {
        movement.nor().scl(Gdx.graphics.deltaTime * speed)
        if (!onGround()) {
            movement.scl(0.7f)
        }
        playerModel.characterController.setWalkDirection(movement.cpy())
    }

    fun jump() {
        if (playerModel.characterController.canJump()) {
            playerModel.characterController.applyImpulse(Vector3(0f, jumpSpeed, 0f))
        }
    }

    fun resetMovement() {
        playerModel.physic.getWorldTransform(playerModel.model.transform)
        movement.set(0f, 0f ,0f)
    }

    fun onGround(): Boolean {
        return playerModel.characterController.onGround()
    }

}