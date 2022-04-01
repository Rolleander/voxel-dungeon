package com.broll.voxeldungeon.rooms

class NothingFoundRoom : RoomTemplate() {
    override fun buildRoom() {
        fillRoom(11.toByte())
    }
}