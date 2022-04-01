package com.broll.voxeldungeon.physics

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.PerspectiveCamera
import com.badlogic.gdx.math.Matrix4
import com.badlogic.gdx.math.Vector3
import com.badlogic.gdx.physics.bullet.DebugDrawer
import com.badlogic.gdx.physics.bullet.collision.*
import com.badlogic.gdx.physics.bullet.dynamics.btDiscreteDynamicsWorld
import com.badlogic.gdx.physics.bullet.dynamics.btDynamicsWorld
import com.badlogic.gdx.physics.bullet.dynamics.btSequentialImpulseConstraintSolver
import com.badlogic.gdx.physics.bullet.linearmath.btIDebugDraw
import com.broll.voxeldungeon.map.Chunk
import com.broll.voxeldungeon.map.ChunkData
import com.broll.voxeldungeon.objects.WorldObject
import com.broll.voxeldungeon.player.Player

class PhysicWorld(player: Player) {
    private val world: btDynamicsWorld
    private val collisionConfiguration: btDefaultCollisionConfiguration = btDefaultCollisionConfiguration()
    private val dispatcher: btCollisionDispatcher
    private val sweep: btAxisSweep3
    private val solver: btSequentialImpulseConstraintSolver
    private val ghostPairCallback: btGhostPairCallback
    private val debugDrawer: DebugDrawer
    fun render(camera: PerspectiveCamera?) {
        debugDrawer.begin(camera)
        world.debugDrawWorld()
        debugDrawer.end()
    }

    fun update() {
        val delta = Math.min(1f / 30f, Gdx.graphics.deltaTime)
        world.stepSimulation(delta, 5, 1f / 60f)
    }

    fun addObject(`object`: WorldObject?) {}
    fun addBox(x: Int, y: Int, z: Int, w: Float, b: Float, h: Float) {
        val mass = 0
        val body = ObjectConstructor(btBoxShape(Vector3(w / 2, h / 2, b / 2)), mass.toFloat()).construct()
        val trans = Matrix4()
        trans.translate(x + w / 2, y + h / 2, z - b / 2 + 1)
        body!!.worldTransform = trans
        body.collisionFlags = btBroadphaseProxy.CollisionFilterGroups.StaticFilter
        body.userValue = 0
        world.addRigidBody(body, btBroadphaseProxy.CollisionFilterGroups.StaticFilter,
                btBroadphaseProxy.CollisionFilterGroups.CharacterFilter)
    }

    fun addChunk(chunk: Chunk?) {
        if (!chunk!!.isInWorld) {
            val x = chunk.location!!.x * ChunkData.Companion.CHUNK_SIZE
            val y = chunk.location!!.y * ChunkData.Companion.CHUNK_SIZE
            val z = chunk.location!!.z * ChunkData.Companion.CHUNK_SIZE
            val blocks = ChunkBlockMerger.mergeBlocks(chunk.data)
            for (block in blocks!!) {
                addBox((x + block!!.x).toInt(), (y + block.y).toInt(), (z + block.z).toInt(), block.w.toFloat(), block.b.toFloat(), block.h.toFloat())
            }
            chunk.isInWorld = true
        }
    }

    fun removeChunk(chunk: Chunk?) {
        if (chunk!!.isInWorld) {
            chunk.isInWorld = false
        }
    }

    fun dispose() {
        for (c in ObjectConstructor.Companion.constructors) {
            c.dispose()
        }
    }

    init {
        dispatcher = btCollisionDispatcher(collisionConfiguration)
        sweep = btAxisSweep3(Vector3((-1000).toFloat(), (-1000).toFloat(), (-1000).toFloat()), Vector3(1000f, 1000f, 1000f))
        solver = btSequentialImpulseConstraintSolver()
        world = btDiscreteDynamicsWorld(dispatcher, sweep, solver, collisionConfiguration)
        ghostPairCallback = btGhostPairCallback()
        sweep.overlappingPairCache.setInternalGhostPairCallback(ghostPairCallback)
        world.gravity = Vector3(0f, -10f, 0f)
        world.addCollisionObject(
                player.playerModel.physic,
                btBroadphaseProxy.CollisionFilterGroups.CharacterFilter,
                btBroadphaseProxy.CollisionFilterGroups.StaticFilter or btBroadphaseProxy.CollisionFilterGroups.DefaultFilter)
        world.addAction(player.playerModel.characterController)
        debugDrawer = DebugDrawer()
        world.debugDrawer = debugDrawer
        debugDrawer.debugMode = btIDebugDraw.DebugDrawModes.DBG_MAX_DEBUG_DRAW_MODE
    }
}