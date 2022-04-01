package com.broll.voxeldungeon.rooms

abstract class Door {
    var x = 0
    var y = 0
        protected set
    var z = 0
    fun matches(door: Door?): Boolean {
        return door!!.x == x && door.y == y && door.z == z
    }

}