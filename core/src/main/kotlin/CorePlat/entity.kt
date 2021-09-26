package CorePlat

import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import java.lang.Exception


class entity(startPositionT: Vector2, hpT: Float, jumpMaxDurationT: Int) {

    operator fun set(startPosition: Vector2, hp: Float, jumpMaxDuration: Int): entity
    { // Fully complete setter
        this.hp = hp
        this.jumpMaxDuration = jumpMaxDuration.toUInt()
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        bodyDefinition.position.set(startPosition)
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
    var maxMachTime: UInt = 0.toUInt() // time (frames in this case) it takes to get from 0 velocity to the max
    var MachTimer: UInt =  0.toUInt()
    var maxWalkVelocity: Float = 0f //incomplete
    var maxRunVelocity: Float = 0f //incomplete


    init {
        this.hp = hpT
        this.jumpMaxDuration = jumpMaxDurationT.toUInt()
        bodyDefinition.type = BodyDef.BodyType.DynamicBody;
        bodyDefinition.position.set(startPositionT)
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
            if (run == false) { // at its base doesn't cause any problems, just get the value without the
                xDirection = direction // minus or plus
                body!!.linearVelocity.add(Vector2(  speed, 0f))//to do: implement - for left and + for right
            }                                   // ^ here
            else (run == true)
            {

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
                bodyDefinition.linearVelocity.set(bodyDefinition.linearVelocity.x, bodyDefinition.linearVelocity.y + 0.5f)
                jumpDuration--
            }


            else
                isJumping=false
                jumpDuration = jumpMaxDuration
        }
    }
    //Getters
}