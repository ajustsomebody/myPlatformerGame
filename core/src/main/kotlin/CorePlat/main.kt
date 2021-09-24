package CorePlat

import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.*
import com.badlogic.gdx.utils.viewport.Viewport
import javax.swing.text.html.parser.Entity


/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class main : ApplicationAdapter() {
    private var batch: SpriteBatch? = null
    private var image: Texture? = null
    private var player: entity? = null
    private var camera: OrthographicCamera? = null
    private var world: World? = null
    private val entities = mutableListOf<entity>()

    fun createObject(createThis: entity) // will create reference or copy issues probably...
    {
        if(!entities.contains(createThis))
                entities.add(createThis)
        createThis.bodyInTheWorld = world!!.createBody(createThis.body) // i cant fix this rn, enough coding for today
        var temporaryShape = PolygonShape()
        temporaryShape.setAsBox(50f, 1f)
        createThis.bodyInTheWorld.createFixture(temporaryShape, 1f)

        createThis.bodyInTheWorld.createFixture(temporaryShape, 1f )
    }
    fun deleteObject(deleteThis: entity)
    {

        if(entities.contains(deleteThis))
        {
            entities.remove(deleteThis)
            world!!.destroyBody(deleteThis.bodyInTheWorld)
        }
    }

    override fun create() {
        batch = SpriteBatch()
        image = Texture("badlogic.png")
        player = entity()
        camera = OrthographicCamera()
        world = World(Vector2(0f,0f), true)

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
    fun applyLogic()
    {
        world!!.step(Gdx.graphics.deltaTime, 3,3 ) //no idea what those last2 args mean
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

}