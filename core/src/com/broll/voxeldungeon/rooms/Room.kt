package com.broll.voxeldungeon.rooms

import com.badlogic.gdx.math.Vector3
import com.broll.voxeldungeon.map.ChunkData

class Room(template: RoomTemplate?, x: Int, y: Int, z: Int) {
    val chunk: ChunkData?
    val doors: RoomDoors?
    var north: Room? = null
    var south: Room? = null
    var east: Room? = null
    var west: Room? = null
    var top: Room? = null
    var bot: Room? = null
    val x: Int
    val y: Int
    val z: Int
    private val rotation: Int
    override fun toString(): String {
        return "x:" + x + " y:" + y + " z:" + z + "   rotation: " + rotation + "  " + doors.toString()
    }

    fun updateDoors() {
        // set doors
        val door_block: Byte = -1
        if (bot != null) {
            for (door in doors!!.doorsBot) {
                chunk!!.setVoxel(door_block, door.x, 0, door.z)
            }
        }
        if (east != null) {
            for (door in doors!!.doorsEast) {
                for (i in 1..DOOR_HEIGHT) {
                    chunk!!.setVoxel(door_block, ChunkData.Companion.CHUNK_SIZE - 1, door.y + i, door.z)
                }
            }
        }
        if (north != null) {
            for (door in doors!!.doorsNorth) {
                for (i in 1..DOOR_HEIGHT) {
                    chunk!!.setVoxel(door_block, door.x, door.y + i, ChunkData.Companion.CHUNK_SIZE - 1)
                }
            }
        }
        chunk!!.updateMeshs(Vector3(x.toFloat(), y.toFloat(), z.toFloat()))
    }

    companion object {
        const val DOOR_HEIGHT = 2
    }

    init {
        chunk = template!!.buildChunk()
        doors = template.doors.copy
        this.x = x
        this.y = y
        this.z = z
        rotation = template.rotation
    }
}