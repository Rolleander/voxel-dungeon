package com.broll.voxeldungeon.rooms

enum class DoorDirection {
    TOP, BOT, EAST, WEST, NORTH, SOUTH;

    fun mirror(): DoorDirection? {
        return when (this) {
            TOP -> BOT
            BOT -> TOP
            EAST -> WEST
            WEST -> EAST
            NORTH -> SOUTH
            SOUTH -> NORTH
        }
        return null
    }
}