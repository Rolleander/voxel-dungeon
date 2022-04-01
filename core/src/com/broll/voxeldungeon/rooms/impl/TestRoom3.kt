package com.broll.voxeldungeon.rooms.impl

import com.broll.voxeldungeon.rooms.DoorDirection
import com.broll.voxeldungeon.rooms.DoorWest
import com.broll.voxeldungeon.rooms.RoomTemplate

class TestRoom3 : RoomTemplate() {
    override fun buildRoom() {
        val y: Int = RoomTemplate.Companion.CENTER - 1
        rc.stairsDown(0, y, RoomTemplate.Companion.CENTER, DoorDirection.EAST, 1, 10)


        //addDoor(new DoorWest(CENTER, CENTER));
        addDoor(DoorWest(RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER))


        //addDoor(new DoorNorth(CENTER, CENTER));
    }
}