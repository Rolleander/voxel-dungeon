package com.broll.voxeldungeon.objects.impl

import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.collision.btBoxShape
import com.broll.voxeldungeon.objects.RoomObject
import com.broll.voxeldungeon.rooms.DoorDirection
import com.broll.voxeldungeon.rooms.Room

class RoomDoor(pos: Vector3?, dir: DoorDirection?) : RoomObject() {
    private val closed = true
    override fun update(delta: Float) {}

    init {
        modelName = "Gate"
        initBody(btBoxShape(Vector3(0.5f, Room.Companion.DOOR_HEIGHT.toFloat(), 0.2f)), 0f)
    }
}