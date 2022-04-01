package com.broll.voxeldungeon.scenes.impl

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.GL20
import com.broll.voxeldungeon.create.GameInit
import com.broll.voxeldungeon.hud.HudRender
import com.broll.voxeldungeon.map.ChunkData
import com.broll.voxeldungeon.map.Map
import com.broll.voxeldungeon.physics.PhysicChunkCall
import com.broll.voxeldungeon.physics.PhysicWorld
import com.broll.voxeldungeon.player.CameraControl
import com.broll.voxeldungeon.player.MovementControl
import com.broll.voxeldungeon.player.Player
import com.broll.voxeldungeon.render.MapRender
import com.broll.voxeldungeon.render.ObjectRender
import com.broll.voxeldungeon.render.RenderSettings
import com.broll.voxeldungeon.resource.ResourceManager
import com.broll.voxeldungeon.rooms.RoomGenerator
import com.broll.voxeldungeon.scenes.Scene
import com.broll.voxeldungeon.scenes.SceneControl

class InGameScene(resourceManager: ResourceManager, sceneControl: SceneControl) : Scene(resourceManager, sceneControl) {
    private val map: Map
    private val mapRender: MapRender
    private val objectRender: ObjectRender
    private val renderSettings: RenderSettings?
    private val cameraControl: CameraControl
    private val player: Player
    private val hudRender: HudRender
    private val roomGenerator: RoomGenerator
    private val physicWorld: PhysicWorld
    private val playerMovementControl: MovementControl
    override fun render() {
        playerMovementControl.update()
        player.update()
        physicWorld.update()
        player.resestMovement()
        cameraControl.setLocation(player.cameraLocation)
        renderSettings!!.camera!!.update()
        Gdx.gl20.glViewport(0, 0, Gdx.graphics.width, Gdx.graphics.height)
        //Gdx.gl20.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT or GL20.GL_DEPTH_BUFFER_BIT or if (Gdx.graphics.bufferFormat.coverageSampling) GL20.GL_COVERAGE_BUFFER_BIT_NV else 0)
        Gdx.gl20.glEnable(GL20.GL_DEPTH_TEST)
        Gdx.gl20.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA)
        Gdx.gl20.glEnable(GL20.GL_CULL_FACE)
        Gdx.gl20.glCullFace(GL20.GL_BACK)
        mapRender.renderMap(renderSettings)
        objectRender.renderObjects(renderSettings)
        Gdx.gl20.glDisable(GL20.GL_CULL_FACE)

        //debug render for physic engine
        //physicWorld.render(renderSettings.getCamera());
        hudRender.render()
    }

    override fun onShow() {
        Gdx.input.isCursorCatched = true
        Gdx.input.inputProcessor = cameraControl
    }

    override fun onQuit() {
        Gdx.input.isCursorCatched = false
    }

    init {
        val init = GameInit()
        init.initDebugGame(resourceManager)
        val s = 0
        map = Map()
        roomGenerator = RoomGenerator(map)
        roomGenerator.generateStartRoom(s, s, s)
        mapRender = MapRender(map)
        renderSettings = init.renderSettings
        cameraControl = CameraControl(renderSettings!!.camera)
        player = Player()
        val p: Int = s * ChunkData.Companion.CHUNK_SIZE + ChunkData.Companion.CHUNK_SIZE / 2
        // player.getMovement().teleport(new Vector3(p, p, p));
        player.teleport(p, p + 1, p)
        hudRender = HudRender(resourceManager, player)
        objectRender = ObjectRender(resourceManager, player)
        physicWorld = PhysicWorld(player)
        mapRender.addRenderCall(PhysicChunkCall(physicWorld))
        playerMovementControl = MovementControl(player.playerMovement, cameraControl)
    }
}