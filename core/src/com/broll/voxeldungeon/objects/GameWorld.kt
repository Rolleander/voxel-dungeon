package com.broll.voxeldungeon.objects

import com.badlogic.gdx.math.Vector3
import com.broll.voxeldungeon.player.Player
import java.util.*

class GameWorld(val player: Player) {
    val objects: List<WorldObject> = ArrayList()
    fun getDistanceToPlayer(`object`: WorldObject?): Float {
        return player.location.dst(`object`!!.transform.getTranslation(Vector3()))
    }

    fun getDistance(o1: WorldObject, o2: WorldObject): Float {
        val v1 = o1.transform.getTranslation(Vector3())
        val v2 = o2.transform.getTranslation(Vector3())
        return v1.dst(v2)
    }

}