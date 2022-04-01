package com.broll.voxeldungeon.rooms.impl

import com.broll.voxeldungeon.rooms.DoorEast
import com.broll.voxeldungeon.rooms.DoorNorth
import com.broll.voxeldungeon.rooms.RoomTemplate

class TestRoom : RoomTemplate() {
    override fun buildRoom() {
        val y: Int = RoomTemplate.Companion.CENTER - 1
        /*	rc.platformToGround(0, y, 0,CENTER,CENTER);
		rc.platformToGround(0, y, CENTER+1,CENTER,CENTER);
		rc.platform(CENTER, ROOM_SIZE-1, 0, CENTER+1, CENTER+1, ROOM_SIZE);
		rc.stairsDown(0, y, CENTER, DoorDirection.EAST, 1, 10);
*/rc.fill(0.toByte(), RoomTemplate.Companion.CENTER, y, RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER + 1, 1, 1)
        rc.fill(0.toByte(), RoomTemplate.Companion.CENTER, y, RoomTemplate.Companion.CENTER, 1, 1, RoomTemplate.Companion.CENTER + 1)

        //addDoor(new DoorWest(CENTER, CENTER));
        addDoor(DoorNorth(RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER))
        addDoor(DoorEast(RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER))

        //addDoor(new DoorNorth(CENTER, CENTER));
    }
}