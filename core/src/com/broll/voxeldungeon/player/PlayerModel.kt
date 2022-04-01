package com.broll.voxeldungeon.player

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.VertexAttributes
import com.badlogic.gdx.graphics.g3d.Material
import com.badlogic.gdx.graphics.g3d.ModelInstance
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.collision.btCapsuleShape
import com.badlogic.gdx.physics.bullet.collision.btCollisionObject
import com.badlogic.gdx.physics.bullet.collision.btPairCachingGhostObject
import com.badlogic.gdx.physics.bullet.dynamics.btKinematicCharacterController

class PlayerModel {
    val model: ModelInstance
    val physic: btPairCachingGhostObject
    val characterController: btKinematicCharacterController
    private val width = 0.6f
    private val height = 1.7f

    companion object {
        const val USER_VALUE = -1
    }

    init {
        val mb = ModelBuilder()
        mb.begin()
        mb.node().id = "player"
        mb.part("box", GL20.GL_TRIANGLES, (VertexAttributes.Usage.Position or VertexAttributes.Usage.Normal).toLong(),
                Material(ColorAttribute.createDiffuse(Color.RED))).capsule(width / 2, height, 15)
        model = ModelInstance(mb.end())
        physic = btPairCachingGhostObject()
        physic.userValue = USER_VALUE
        physic.worldTransform = model.transform
        val shape = btCapsuleShape(width / 2, height / 2)
        physic.collisionShape = shape
        physic.collisionFlags = btCollisionObject.CollisionFlags.CF_CHARACTER_OBJECT
        characterController = btKinematicCharacterController(physic, shape, 0.35f, Vector3.Y)
        characterController.gravity = Vector3(0f, (-10).toFloat(), 0f)
    }
}