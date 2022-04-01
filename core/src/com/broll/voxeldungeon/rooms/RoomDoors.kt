package com.broll.voxeldungeon.rooms

import java.util.*

class RoomDoors {
    val doorsBot: MutableList<Door> = ArrayList()
     val doorsEast: MutableList<Door> = ArrayList()
     val doorsNorth: MutableList<Door> = ArrayList()
     val doorsSouth: MutableList<Door> = ArrayList()
     val doorsTop: MutableList<Door> = ArrayList()
     val doorsWest: MutableList<Door> = ArrayList()
     val doors: MutableList<Door> = ArrayList()
    val copy: RoomDoors
        get() {
            val rd = RoomDoors()
            for (d in doors) {
                rd.addDoor(d)
            }
            return rd
        }

    override fun toString(): String {
        var s = "doors: "
        if (doorsBot.size > 0) {
            s += "bot "
        }
        if (doorsEast.size > 0) {
            s += "east "
        }
        if (doorsNorth.size > 0) {
            s += "north "
        }
        if (doorsSouth.size > 0) {
            s += "south "
        }
        if (doorsTop.size > 0) {
            s += "top "
        }
        if (doorsWest.size > 0) {
            s += "west "
        }
        return s
    }

    fun getDoors(dir: DoorDirection?): List<Door>? {
        when (dir) {
            DoorDirection.BOT -> return doorsBot
            DoorDirection.EAST -> return doorsEast
            DoorDirection.NORTH -> return doorsNorth
            DoorDirection.SOUTH -> return doorsSouth
            DoorDirection.TOP -> return doorsTop
            DoorDirection.WEST -> return doorsWest
        }
        return null
    }

    fun rotate() {
        for (d in doors) {
            val r = d.x
            val c = d.z
            val z: Int = RoomTemplate.Companion.ROOM_SIZE - 1 - r
            val y = d.y
            if (d is DoorBot || d is DoorTop) {
                d.x = c
                d.z = z
            } else {
                if (d is DoorEast) {
                    doorsEast.remove(d)
                    doorsSouth.add(DoorSouth(c, y))
                } else if (d is DoorSouth) {
                    doorsSouth.remove(d)
                    doorsWest.add(DoorWest(z, y))
                } else if (d is DoorWest) {
                    doorsWest.remove(d)
                    doorsNorth.add(DoorNorth(c, y))
                } else if (d is DoorNorth) {
                    doorsNorth.remove(d)
                    doorsEast.add(DoorEast(z, y))
                }
            }
        }
        doors.clear()
        doors.addAll(doorsBot)
        doors.addAll(doorsEast)
        doors.addAll(doorsNorth)
        doors.addAll(doorsSouth)
        doors.addAll(doorsTop)
        doors.addAll(doorsWest)
    }

    fun addDoor(door: Door) {
        if (door is DoorBot) {
            doorsBot.add(door)
        } else if (door is DoorEast) {
            doorsEast.add(door)
        } else if (door is DoorNorth) {
            doorsNorth.add(door)
        } else if (door is DoorSouth) {
            doorsSouth.add(door)
        } else if (door is DoorTop) {
            doorsTop.add(door)
        } else if (door is DoorWest) {
            doorsWest.add(door)
        }
        doors.add(door)
    }

    val all: List<Door>
        get() = doors
}