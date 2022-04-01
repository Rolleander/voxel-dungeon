package com.broll.voxeldungeon.rooms.impl

import com.broll.voxeldungeon.rooms.*

class StartRoom : RoomTemplate() {
    override fun buildRoom() {
        val r = 2
        val y: Int = RoomTemplate.Companion.CENTER - 1
        val x: Int = RoomTemplate.Companion.CENTER - r
        val z: Int = RoomTemplate.Companion.CENTER - r
        val l = r * 2 + 1
        rc.fill(0.toByte(), 0, y, RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER, 1, 1)
        rc.fill(0.toByte(), RoomTemplate.Companion.CENTER + 1, y, RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER, 1, 1)
        rc.fill(0.toByte(), RoomTemplate.Companion.CENTER, y, 0, 1, 1, RoomTemplate.Companion.CENTER)
        rc.fill(0.toByte(), RoomTemplate.Companion.CENTER, y, RoomTemplate.Companion.CENTER + 1, 1, 1, RoomTemplate.Companion.CENTER)
        rc.platformToGround(x, y, z, l, l)
        val r2 = 2
        rc.platformToGround(0, y, z, r2, l)
        rc.platformToGround(RoomTemplate.Companion.ROOM_SIZE - r2, y, z, r2, l)
        rc.platformToGround(x, y, 0, l, r2)
        rc.platformToGround(x, y, RoomTemplate.Companion.ROOM_SIZE - r2, l, r2)

        //	rc.fill((byte)0, 0, CENTER+1, z, 1, 1,1);
        //rc.fill((byte)0, 1, CENTER+2, z, 1, 1,1);
        rc.fill(0.toByte(), 0, 5, z - 1, 1, 8, 1)
        rc.fill(0.toByte(), 0, 4, z - 2, 1, 8, 1)
        rc.fill(0.toByte(), 0, 3, z - 3, 1, 8, 1)
        rc.fill(0.toByte(), 0, 2, z - 4, 1, 8, 1)
        rc.fill(0.toByte(), 0, 1, z - 5, 1, 8, 1)
        rc.fill(0.toByte(), 1, 0, z - 5, 1, 8, 1)
        addDoor(DoorEast(RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER))
        addDoor(DoorWest(RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER))
        addDoor(DoorNorth(RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER))
        addDoor(DoorSouth(RoomTemplate.Companion.CENTER, RoomTemplate.Companion.CENTER))
        room[RoomTemplate.Companion.CENTER][RoomTemplate.Companion.CENTER - 1]!![RoomTemplate.Companion.CENTER] = 10

//		rc.stairsDown(5, 6, 4, DoorDirection.EAST, 4, 5);
    }
}