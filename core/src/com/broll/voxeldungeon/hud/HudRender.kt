package com.broll.voxeldungeon.hud

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.g2d.BitmapFont
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.broll.voxeldungeon.player.Player
import com.broll.voxeldungeon.resource.ResourceManager

class HudRender(resourceManager: ResourceManager, private val player: Player) {
    private val batch = SpriteBatch()
    private val test: Sprite
    private val font: BitmapFont
    fun render() {
        batch.begin()
        //	test.draw(batch);
        val pos = player.location
        val y = Gdx.graphics.height - 5
        font.draw(batch, "X: " + pos!!.x, 5f, y.toFloat())
        font.draw(batch, "Y: " + pos.y, 5f, y - 15.toFloat())
        font.draw(batch, "Z: " + pos.z, 5f, y - 30.toFloat())
        batch.end()
    }

    init {
        test = Sprite(resourceManager.getTexture("wall.jpg"))
        test.setBounds(10f, 10f, 50f, 50f)
        font = BitmapFont()
    }
}