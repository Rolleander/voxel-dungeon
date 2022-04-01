package com.broll.voxeldungeon.rooms

import com.broll.voxeldungeon.map.ChunkData

abstract class RoomTemplate {
    protected var isRandomized = false
    var doors = RoomDoors()
        protected set
    var room = Array(ROOM_SIZE) { Array<ByteArray?>(ROOM_SIZE) { ByteArray(ROOM_SIZE) } }
        protected set
    protected var rc: RoomConstructor = RoomConstructor(this)
    var rotation = 0
        private set

    protected fun addDoor(door: Door) {
        doors.addDoor(door)
    }

    protected fun cleanRoom() {
        fillRoom((-1).toByte())
        doors = RoomDoors()
    }

    protected fun fillRoom(v: Byte) {
        for (x in 0 until ROOM_SIZE) {
            for (z in 0 until ROOM_SIZE) {
                for (y in 0 until ROOM_SIZE) {
                    room[x][y]!![z] = v
                }
            }
        }
    }

    protected abstract fun buildRoom()
    fun updateRoomTemplate() {
        if (isRandomized) {
            cleanRoom()
            buildRoom()
        }
    }

    fun rotate() {
        rotation++
        if (rotation >= 4) {
            rotation = 3
        }
        for (y in 0 until ROOM_SIZE) {
            val ret = Array(ROOM_SIZE) { ByteArray(ROOM_SIZE) }
            for (r in 0 until ROOM_SIZE) {
                for (c in 0 until ROOM_SIZE) {
                    ret[c][ROOM_SIZE - 1 - r] = room[r][y]!![c]
                }
            }
            for (r in 0 until ROOM_SIZE) {
                for (c in 0 until ROOM_SIZE) {
                    room[r][y]!![c] = ret[r][c]
                }
            }
        }
        doors.rotate()
    }

    fun buildChunk(): ChunkData {
        val data = ChunkData((-1).toByte())
        for (x in 0 until ChunkData.Companion.CHUNK_SIZE) {
            for (z in 0 until ChunkData.Companion.CHUNK_SIZE) {
                // ground
                data.setVoxel(1.toByte(), x, 0, z)
                for (y in 1 until ChunkData.Companion.CHUNK_SIZE) {
                    // walls
                    if (x == ChunkData.Companion.CHUNK_SIZE - 1 || z == ChunkData.Companion.CHUNK_SIZE - 1) {
                        data.setVoxel(11.toByte(), x, y, z)
                    }
                    // room
                    if (x < ChunkData.Companion.CHUNK_SIZE - 1 && z < ChunkData.Companion.CHUNK_SIZE - 1) {
                        data.setVoxel(room[x][y - 1]!![z], x, y, z)
                    }
                }
            }
        }
        return data
    }

    companion object {
        const val ROOM_SIZE: Int = ChunkData.Companion.CHUNK_SIZE - 1
        const val CENTER = ROOM_SIZE / 2
    }

    init {
        cleanRoom()
        buildRoom()
    }
}