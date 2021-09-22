package CorePlat

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch


/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class main : ApplicationAdapter() {
    private var batch: SpriteBatch? = null
    private var image: Texture? = null



    override fun create() {
        batch = SpriteBatch()
        image = Texture("badlogic.png")
        var position = arrayOf<Int>(0,0)
        var player = entity()
    }

    fun applyLogic()
    {
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {

        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {

        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {

        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {

        }
        if(Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
        {

        }
        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {

        }
    }
    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        batch!!.begin()
        batch!!.draw(image, 165f, 180f)
        batch!!.end()
    }

    override fun dispose() {
        batch!!.dispose()
        image!!.dispose()
    }
}