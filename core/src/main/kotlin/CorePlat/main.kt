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
import com.badlogic.gdx.scenes.scene2d.ui.Image


/** [com.badlogic.gdx.ApplicationListener] implementation shared by all platforms.  */
class main : ApplicationAdapter() {
    private var batch: SpriteBatch? = null
    private var image: Texture? = null
    private var player: entity? = null
    private var camera: OrthographicCamera? = null
    private var world: World? = null
    private val entities = mutableListOf<entity>()

    fun createObject(createThis: entity) : entity // will create reference or copy issues probably...
    {
        if(!entities.contains(createThis))
                entities.add(createThis)
        createThis.body = world!!.createBody(createThis.bodyDefinition) // i cant fix this rn, enough coding for today
        var temporaryShape = PolygonShape()
        temporaryShape.setAsBox(50f, 1f)
        createThis.body!!.createFixture(temporaryShape, 1f)
        temporaryShape.dispose()
        return createThis
    }
    fun createFloor()
    {
        val bodyDef = BodyDef()
        bodyDef.type = BodyDef.BodyType.StaticBody
        bodyDef.position[0f] = -10f
        // add it to the world
        var bodys = world!!.createBody(bodyDef)
        // set the shape (here we use a box 50 meters wide, 1 meter tall )
        val shape = PolygonShape()
        shape.setAsBox(50f, 1f)
        // create the physical object in our bodyDefinition)
        // without this our bodyDefinition would just be data in the world
        bodys.createFixture(shape, 0.0f)
        // we no longer use the shape object here so dispose of it.
        shape.dispose()
    }
    fun deleteObject(deleteThis: entity)
    {

        if(entities.contains(deleteThis))
        {
            entities.remove(deleteThis)
            world!!.destroyBody(deleteThis.body)
        }
    }

    override fun create() {
        batch = SpriteBatch()
        image = Texture("badlogic.png")
        camera = OrthographicCamera()
        world = World(Vector2(0f,0f), true)
        world!!.gravity.set(0f, 1f)
        createFloor()
        player = createObject(entity(Vector2(0f,0f), 100f, 10, Texture("png//Idle (1).png")))
        player!!.body!!.massData.mass = 10f;
    }
    override fun render() {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f)
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT)
        applyLogic()
        batch!!.begin()
        for(element in entities)
        {
            batch!!.draw(element.texture, element.body!!.position.x, element.body!!.position.y)
        }
        batch!!.end()

    }

    override fun dispose() {
        batch!!.dispose()
        image!!.dispose()
    }
    fun applyLogic()
    {
        world!!.step(Gdx.graphics.deltaTime, 3,3 ) //no idea what those last2 args mean
        for(element in entities)
        {
            element.body!!.applyForceToCenter(world!!.gravity,true)
            element.update()
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT))
        {
            player!!.move(Input.Keys.RIGHT, Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
        {
            player!!.move(Input.Keys.LEFT, Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT))
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN))
        {

        }
        if(Gdx.input.isKeyPressed(Input.Keys.UP))
        {

        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE))
        {
            player!!.jump()
        }
        if(varOrCache.DebugMode)
        {
            println("Vel: ${player!!.body!!.linearVelocity.x}, ${player!!.body!!.linearVelocity.y} " +
                    "Dir: ${when(player!!.xDirection) {Input.Keys.RIGHT-> "Right"
                                                       Input.Keys.LEFT-> "Left";else -> "unknown" }} "+
                    "Pos: ${player!!.body!!.position.x}, ${player!!.body!!.position.y}"+
                    "Jdr: ${player!!.jumpDuration} IsJ: ${player!!.isJumping}")
        }
    }

}