package com.broll.voxeldungeon.rooms

class RoomConstructor(private val room: RoomTemplate) {
    var platformTop: Byte = 1
    var platformFill: Byte = 0
    var platformEdge: Byte = 11
    var stairs: Byte = 0
    var overwrite = false
    fun stairsDown(px: Int, py: Int, pz: Int, dir: DoorDirection?, w: Int, l: Int) {
        var py = py
        py -= 1
        var step = 0
        for (y in py downTo py - l) {
            var x = -1
            var z = -1
            for (i in 0 until w) {
                when (dir) {
                    DoorDirection.EAST -> {
                        x = px + step
                        z = pz - i
                    }
                    DoorDirection.SOUTH -> {
                        x = px + i
                        z = pz - step
                    }
                    DoorDirection.WEST -> {
                        x = px - step
                        z = pz - i
                    }
                    DoorDirection.NORTH -> {
                        x = px + i
                        z = pz + step
                    }
                }
                placeToGround(stairs, x, y, z)
            }
            step++
        }
    }

    fun fill(b: Byte, px: Int, py: Int, pz: Int, pw: Int, ph: Int, pb: Int) {
        var pw = pw
        var ph = ph
        var pb = pb
        pw -= 1
        pb -= 1
        ph -= 1
        for (y in py downTo py - ph) {
            for (x in px..px + pw) {
                for (z in pz..pz + pb) {
                    place(b, x, y, z)
                }
            }
        }
    }

    fun platform(px: Int, py: Int, pz: Int, pw: Int, ph: Int, pb: Int) {
        var pw = pw
        var ph = ph
        var pb = pb
        pw -= 1
        pb -= 1
        ph -= 1
        for (y in py downTo py - ph) {
            for (x in px..px + pw) {
                for (z in pz..pz + pb) {
                    if (y == py) {
                        place(platformTop, x, y, z)
                    } else {
                        if (x == px && (z == pz || z == pz + pb)) {
                            place(platformEdge, x, y, z)
                        } else if (x == px + pw && (z == pz || z == pz + pb)) {
                            place(platformEdge, x, y, z)
                        } else {
                            place(platformFill, x, y, z)
                        }
                    }
                }
            }
        }
    }

    fun platformToGround(px: Int, py: Int, pz: Int, pw: Int, pb: Int) {
        val ph = py + 1
        platform(px, py, pz, pw, ph, pb)
    }

    fun placeToGround(b: Byte, x: Int, y: Int, z: Int) {
        if (y > 0) {
            for (i in y downTo -1 + 1) {
                place(b, x, i, z)
            }
        } else {
            place(b, x, y, z)
        }
    }

    fun place(b: Byte, x: Int, y: Int, z: Int) {
        if (x >= 0 && y >= 0 && z >= 0 && x < RoomTemplate.Companion.ROOM_SIZE && y < RoomTemplate.Companion.ROOM_SIZE && z < RoomTemplate.Companion.ROOM_SIZE) {
            if (overwrite || room!!.room[x][y]!![z] == (-1).toByte()) {
                room!!.room[x][y]?.set(z, b)
            }
        }
    }

}