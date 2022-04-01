package com.broll.voxeldungeon.rooms

import com.broll.voxeldungeon.rooms.impl.TestRoom
import com.broll.voxeldungeon.rooms.impl.TestRoom2
import com.broll.voxeldungeon.rooms.impl.TestRoom3
import java.util.*

class TemplateList {
    val templates: MutableList<RoomTemplate?> = ArrayList()
    fun shuffle() {
        templates.shuffle()
    }

    init {
        templates.add(TestRoom())
        templates.add(TestRoom2())
        templates.add(TestRoom3())
    }
}