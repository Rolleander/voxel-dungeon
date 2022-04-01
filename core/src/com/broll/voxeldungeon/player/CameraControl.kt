package com.broll.voxeldungeon.player

import com.badlogic.gdx.InputAdapter
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.math.Vector3

class CameraControl(val camera: PerspectiveCamera?) : InputAdapter() {
    private var lastX = -1f
    private var lastY = 0f
    private var angleLeft = 0f
    private var angleUp = 0f
    fun setLocation(position: Vector3?) {
        camera!!.position.set(Vector3(position!!.x, position.y + 1, position.z))
    }

    override fun mouseMoved(x: Int, y: Int): Boolean {
        if (lastX != -1f) {
            val deltaX = (x - lastX) * 2
            var deltaY = (y - lastY) * 2
            deltaY = deltaY * -1
            angleLeft -= deltaX / 10
            angleUp += deltaY / 10
            if (angleUp < -90) angleUp = -90f
            if (angleUp > 90) angleUp = 90f
            camera!!.direction[0f, 0f] = -1f
            camera.up[0f, 1f] = 0f
            camera.rotate(angleUp, 1f, 0f, 0f)
            camera.rotate(angleLeft, 0f, 1f, 0f)
        }
        lastX = x.toFloat()
        lastY = y.toFloat()
        return false
    }

    fun moveCamera(move: Vector3?) {
        camera!!.position.add(move)
    }

}