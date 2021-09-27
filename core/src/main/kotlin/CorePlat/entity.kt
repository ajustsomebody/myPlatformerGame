package CorePlat

import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import java.lang.Exception
import kotlin.math.abs


class entity(startPositionT: Vector2,
             hpT: Float,
             jumpMaxDurationT: Int,
             textureT: Texture) {

    operator fun set(startPosition: Vector2,
                     hp: Float,
                     jumpMaxDuration: Int,
                     texture: Texture): entity
    { // Fully complete setter
        this.hp = hp
        this.jumpMaxDuration = jumpMaxDuration.toUInt()
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        bodyDefinition.position.set(startPosition)
        if(!(texture == null)) this.texture = texture
        else this.texture = Texture("badlogic.png")
        return this
    }

    //Variables itself
    var bodyDefinition: BodyDef = BodyDef();
    var hp: Float = 0f
    var position: Vector2 get() {return body!!.position} set(newPos: Vector2) { body!!.position.set(newPos.x, newPos.y)}
    var speed: Float = 10f
    var runSpeed: Float = speed * 1.5f
    var isJumping: Boolean = false
    var isAfloat: Boolean = true
    var jumpPower: Float = 10f
    var jumpMaxDuration: UInt = 0.toUInt()
    var jumpDuration = jumpMaxDuration
    var attackDefence: Int = 0 // really should make this float but not now
    var attackResistance: Float = 1f
    var attackStrength: Float = 1f
    var body: Body? = null // nulls mean that it will be initialised later
    var xDirection: Int = Input.Keys.RIGHT
    var isRunning: Boolean = false
    var isWalking : Boolean = false
    var isRunningOrMoving: Boolean = false
        get() {return isRunning || isWalking}
    var movedThisFrame: Boolean = false
    //var maxMachTime: UInt = 0.toUInt() // time (frames in this case) it takes to get from 0 velocity to the max
    //var MachTimer: UInt =  0.toUInt() i actually dont need these two probably
    var maxWalkVelocity: Float = 0f //incomplete
    var maxRunVelocity: Float = 0f //incomplete
    var texture : Texture? = null


    init {
        this.hp = hpT
        this.jumpMaxDuration = jumpMaxDurationT.toUInt()
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        bodyDefinition.position.set(startPositionT)
        if(!(textureT == null)) this.texture = textureT
        else this.texture = Texture("badlogic.png")
    }


    fun jump() //
    {
        if(!(isJumping || isAfloat))
        {
            jumpDuration = jumpMaxDuration
            isJumping == true
        }
        /*if(canInfiniJump)
        {
            jumpDuration = jumpMaxDuration
            isJumping == true
        }*/
    }
    fun takeDamage(damageTaken: Float)
    {
        var damageToSubtract = (damageTaken * (1f-attackResistance)) - attackDefence
        if(hp > 0)
        {
            if((hp - damageToSubtract) < 0f) damageToSubtract = hp // possible float decimal miscalculation
        }
        else
        {
            die()
        }

        this.hp -= damageToSubtract // what if hp is less than 0 by normal?
        if(hp<=0) die()

    }
    fun move(direction: Int, run:Boolean)
    {
        if(!varOrCache.HorizontalDirectionArray.contains(direction))
        {
            throw Exception("Inputted an invalid direction (Horizontal needed)")
        }
        else { // if you want to display velocity it will show up as minuses as well, if this velocity system
            movedThisFrame = true
            if (run == false) { // at its base doesn't cause any problems, just get the value without the
                isWalking = true
                if(abs(body!!.linearVelocity.x) + speed <= maxWalkVelocity)
                {
                    xDirection = direction // minus or plus
                    if(xDirection == Input.Keys.RIGHT) body!!.linearVelocity.add(Vector2(speed, 0f))
                    if(xDirection ==  Input.Keys.LEFT) body!!.linearVelocity.add(Vector2(speed * -1, 0f))
                }
                else
                {
                    var tempVar: Float = maxWalkVelocity - speed
                    xDirection = direction
                    if(xDirection == Input.Keys.RIGHT) body!!.linearVelocity.add(Vector2(tempVar, 0f))
                    if(xDirection ==  Input.Keys.LEFT) body!!.linearVelocity.add(Vector2(tempVar * -1, 0f))
                }

            }
            else // run == true
            {
                isRunning = true
                if(abs(body!!.linearVelocity.x) + speed <= maxRunVelocity)
                {
                    xDirection = direction // minus or plus
                    if(xDirection == Input.Keys.RIGHT) body!!.linearVelocity.add(Vector2(runSpeed, 0f))
                    if(xDirection ==  Input.Keys.LEFT) body!!.linearVelocity.add(Vector2(runSpeed * -1, 0f))
                }
                else
                {
                    var tempVar: Float = maxRunVelocity - runSpeed
                    xDirection = direction
                    if(xDirection == Input.Keys.RIGHT) body!!.linearVelocity.add(Vector2(tempVar, 0f))
                    if(xDirection ==  Input.Keys.LEFT) body!!.linearVelocity.add(Vector2(tempVar * -1, 0f))
                }

            }
        }
    }
    fun die()
    {

    }
    fun attack(victim: entity)
    {
        victim.takeDamage(attackStrength)
    }
    fun update()
    {

        if(isJumping)
        {

            if(jumpDuration> 0.toUInt()) {
                body!!.applyForce(Vector2(0f, jumpPower / 10f), body!!.worldCenter, true)
                jumpDuration--
            }


            else
                isJumping=false
                jumpDuration = jumpMaxDuration
        }
        movedThisFrame = false
    }
    //Getters
}