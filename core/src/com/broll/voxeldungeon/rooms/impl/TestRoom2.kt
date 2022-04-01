package com.broll.voxeldungeon.rooms.impl

import com.broll.voxeldungeon.rooms.DoorDirection
import com.broll.voxeldungeon.rooms.DoorEast
import com.broll.voxeldungeon.rooms.DoorWest
import com.broll.voxeldungeon.rooms.RoomTemplate

class TestRoom2 : RoomTemplate() {
    override fun buildRoom() {
        val y: Int = RoomTemplate.Companion.CENTER - 1
        rc.platformToGround(0, y, 0, RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER)
        rc.platformToGround(0, y, RoomTemplate.Companion.CENTER + 1, RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER)
        rc.platform(RoomTemplate.Companion.CENTER, RoomTemplate.Companion.ROOM_SIZE - 1, 0, RoomTemplate.Companion.CENTER + 1, RoomTemplate.Companion.CENTER + 1, RoomTemplate.Companion.ROOM_SIZE)
        rc.stairsDown(0, y, RoomTemplate.Companion.CENTER, DoorDirection.EAST, 1, 10)
        rc.fill(0.toByte(), RoomTemplate.Companion.CENTER, y, 0, 1, 1, RoomTemplate.Companion.ROOM_SIZE)


        //addDoor(new DoorWest(CENTER, CENTER));
        addDoor(DoorWest(RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER))
        addDoor(DoorEast(RoomTemplate.Companion.CENTER - 2, 0))
        addDoor(DoorEast(RoomTemplate.Companion.CENTER + 2, 0))


        //addDoor(new DoorNorth(CENTER, CENTER));
    }
}