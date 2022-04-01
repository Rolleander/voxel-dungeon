package com.broll.voxeldungeon.rooms

import com.broll.voxeldungeon.map.ChunkData
import com.broll.voxeldungeon.map.Map
import com.broll.voxeldungeon.rooms.impl.StartRoom
import java.util.*

class RoomGenerator(private val map: Map) {
    private val templates = TemplateList()
    private val rooms = HashMap<String, Room>()
    private fun putRoomInList(room: Room, x: Int, y: Int, z: Int) {
        val id = "$x#$y#$z"
        rooms[id] = room
    }

    private fun getRoom(x: Int, y: Int, z: Int): Room? {
        val id = "$x#$y#$z"
        return rooms[id]
    }

    fun generateStartRoom(x: Int, y: Int, z: Int) {
        val t: RoomTemplate = StartRoom()
        val room = Room(t, x, y, z)
        putRoomInList(room, x, y, z)
        println("Start room: $room")
        updateMap(room)
        generateNeighbours(room)
        // generate one stop more
        generateNeighbours(room.bot)
        generateNeighbours(room.top)
        generateNeighbours(room.east)
        generateNeighbours(room.west)
        generateNeighbours(room.north)
        generateNeighbours(room.south)
    }

    fun generateNeighbours(room: Room?) {
        if (room == null) {
            return
        }
        val doors = room.doors
        val x = room.x
        val y = room.y
        val z = room.z
        if (room.bot == null && doors!!.doorsBot.size > 0) {
            room.bot = generateNeighbourRoom(x, y - 1, z)
        }
        if (room.east == null && doors!!.doorsEast.size > 0) {
            room.east = generateNeighbourRoom(x + 1, y, z)
        }
        if (room.north == null && doors!!.doorsNorth.size > 0) {
            room.north = generateNeighbourRoom(x, y, z + 1)
        }
        if (room.south == null && doors!!.doorsSouth.size > 0) {
            room.south = generateNeighbourRoom(x, y, z - 1)
        }
        if (room.top == null && doors!!.doorsTop.size > 0) {
            room.top = generateNeighbourRoom(x, y + 1, z)
        }
        if (room.west == null && doors!!.doorsWest.size > 0) {
            room.west = generateNeighbourRoom(x - 1, y, z)
        }
    }

    private fun generateNeighbourRoom(x: Int, y: Int, z: Int): Room {
        templates.shuffle()
        for (template in templates.templates) {
            template!!.updateRoomTemplate()
            for (i in 0..3) {
                if (checkRoomViolations(template.doors, x, y, z)) {
                    val newRoom = Room(template, x, y, z)
                    println("added new room: $newRoom")
                    putRoomInList(newRoom, x, y, z)
                    updateNeighbours(x, y, z)
                    updateMap(newRoom)
                    return newRoom
                }
                template.rotate()
            }
        }
        System.err.println("Didnt find a matching room for $x $y $z")
        val newRoom = Room(NothingFoundRoom(), x, y, z)
        putRoomInList(newRoom, x, y, z)
        updateNeighbours(x, y, z)
        updateMap(newRoom)
        return newRoom
    }

    private fun checkRoomViolations(doors: RoomDoors?, x: Int, y: Int, z: Int): Boolean {
        var room: Room? = null
        for (dir in DoorDirection.values()) {
            room = when (dir) {
                DoorDirection.BOT -> getRoom(x, y - 1, z)
                DoorDirection.TOP -> getRoom(x, y + 1, z)
                DoorDirection.WEST -> getRoom(x - 1, y, z)
                DoorDirection.EAST -> getRoom(x + 1, y, z)
                DoorDirection.NORTH -> getRoom(x, y, z + 1)
                DoorDirection.SOUTH -> getRoom(x, y, z - 1)
            }
            if (room != null) {
                if (!matchingDoors(doors!!.getDoors(dir), room.doors!!.getDoors(dir.mirror()))) {
                    return false
                }
            }
        }
        return true
    }

    private fun matchingDoors(doors1: List<Door?>?, doors2: List<Door?>?): Boolean {
        if (doors1!!.size == 0) {
            return if (doors2!!.size == 0) {
                true
            } else false
        } else if (doors2!!.size == 0) {
            return false
        }
        for (d1 in doors1) {
            if (!doorMatches(d1, doors2)) {
                return false
            }
        }
        return true
    }

    private fun doorMatches(d1: Door?, doors: List<Door?>?): Boolean {
        for (d2 in doors!!) {
            if (d1!!.matches(d2)) {
                return true
            }
        }
        return false
    }

    private fun updateNeighbours(x: Int, y: Int, z: Int) {
        val me = getRoom(x, y, z) ?: return
        // top
        var r: Room? = getRoom(x, y + 1, z)
        if (r != null) {
            me.top = r
            r.bot = me
        }
        // bot
        r = getRoom(x, y - 1, z)
        if (r != null) {
            me.bot =r
            r.top = me
        }
        // east
        r = getRoom(x + 1, y, z)
        if (r != null) {
            me.east = r
            r.west = me
        }
        // west
        r = getRoom(x - 1, y, z)
        if (r != null) {
            me.west=r
            r.east = me
        }
        // north
        r = getRoom(x, y, z + 1)
        if (r != null) {
            me.north=r
            r.south = me
        }
        // south
        r = getRoom(x, y, z - 1)
        if (r != null) {
            me.south=r
            r.north = me
        }
    }

    fun updateMap(room: Room) {
        val x = room.x
        val y = room.y
        val z = room.z
        // update all doors
        room.updateDoors()
        room.south?.updateDoors()
        room.top?.updateDoors()
        room.west?.updateDoors()
        map.setChunkData(room.chunk, x, y, z)
        // for end rooms render empty chunks with walls for south, west and top
        if (room.west == null) {
            map.setChunkData(createEmptyRoomChunk(), x - 1, y, z)
        }
        if (room.south == null) {
            map.setChunkData(createEmptyRoomChunk(), x, y, z - 1)
        }
        if (room.top == null) {
            map.setChunkData(createEmptyRoomChunk(), x, y + 1, z)
        }
    }

    private fun createEmptyRoomChunk(): ChunkData {
        val emptyChunk = ChunkData(11.toByte())
        for (x in 0 until ChunkData.Companion.CHUNK_SIZE) {
            for (z in 0 until ChunkData.Companion.CHUNK_SIZE) {
                emptyChunk.setVoxel(1.toByte(), x, 0, z)
            }
        }
        return emptyChunk
    }

}