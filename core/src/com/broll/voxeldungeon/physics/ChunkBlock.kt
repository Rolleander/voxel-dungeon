package com.broll.voxeldungeon.physics

class ChunkBlock(var x: Int, var y: Int, var z: Int) {
    var w = 1
    var b = 1
    var h = 1
    override fun toString(): String {
        return "$x . $y . $z :  $w x $h x $b"
    }

    override fun hashCode(): Int {
        val prime = 31
        var result = 1
        result = prime * result + b
        result = prime * result + h
        result = prime * result + w
        result = prime * result + x
        result = prime * result + y
        result = prime * result + z
        return result
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as ChunkBlock
        if (b != other.b) return false
        if (h != other.h) return false
        if (w != other.w) return false
        if (x != other.x) return false
        if (y != other.y) return false
        return if (z != other.z) false else true
    }

}