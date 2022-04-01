package com.broll.voxeldungeon.player

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector3

class MovementControl(private val movement: PlayerMovement?, private val cameraControl: CameraControl) {
    private val forwardControl = Input.Keys.W
    private val backwardControl = Input.Keys.S
    private val leftControl = Input.Keys.A
    private val rightControl = Input.Keys.D
    private val jumpControl = Input.Keys.SPACE
    fun update() {
        val camera = cameraControl.camera
        val direction = camera!!.direction
        if (Gdx.input.isKeyPressed(forwardControl)) {
            movement!!.move(Vector3(direction.x, 0f, direction.z))
        }
        if (Gdx.input.isKeyPressed(backwardControl)) {
            movement!!.move(Vector3(-direction.x, 0f, -direction.z))
        }
        if (Gdx.input.isKeyPressed(leftControl)) {
            movement!!.move(Vector3(-direction.x, 0f, -direction.z).crs(camera.up))
        }
        if (Gdx.input.isKeyPressed(rightControl)) {
            movement!!.move(Vector3(direction.x, 0f, direction.z).crs(camera.up))
        }
        if (Gdx.input.isKeyPressed(jumpControl)) {
            movement!!.jump()
        }
    }

}