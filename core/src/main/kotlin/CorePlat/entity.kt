package CorePlat

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.math.Vector2
import com.badlogic.gdx.physics.box2d.Body
import com.badlogic.gdx.physics.box2d.BodyDef
import com.badlogic.gdx.physics.box2d.FixtureDef
import sun.jvm.hotspot.oops.CellTypeState.ref
import java.lang.Exception


class entity() {
    operator fun set(startPosition: Vector2, hp: Float, jumpMaxDuration: UInt): entity
    { // Fully complete setter
        this.hp = hp
        this.jumpMaxDuration = jumpMaxDuration
        body.type = BodyDef.BodyType.DynamicBody;
        body.position.set(startPosition)
        return this
    }

    //Variables itself
    var body: BodyDef = BodyDef();
    var hp: Float = 0f
    var position: Vector2 get() {return body.position} set(newPos: Vector2) {body.position.set(newPos.x, newPos.y)}
    var velocity = Float
    var isJumping: Boolean = false
    var isAfloat: Boolean = true
    var jumpPower: Float = 10f
    var jumpMaxDuration: UInt = 0.toUInt()
    var jumpDuration = jumpMaxDuration
    var attackDefence: Int = 0 // really should make this float but not now
    var attackResistance: Float = 1f
    var attackStrength: Float = 1f
    val bodyInTheWorld: Body? = null // nulls mean that it will be initialised later
    var fixture: FixtureDef? = null
    var xDirection: Int = Input.Keys.RIGHT;
    var maxMachTime: UInt = 0.toUInt() // time (frames in this case) it takes to get from 0 velocity to the max
    var MachTimer: UInt =  0.toUInt()
    var maxWalkSpeed: Float = 0f //incomplete
    var maxRunSpeed: Float = 0f //incomplete

    fun jump() // this will make the player go up only when this is pressed
    {
        if(!(isJumping || isAfloat))
        {
            if(jumpDuration > 0.toUInt()) //it can still be 0!
            {
                position.y+= jumpPower * jumpDuration.toFloat()/10f
                jumpDuration--
                //continue later
            }
            else
            {
                isJumping = false
                jumpDuration = jumpMaxDuration
            }
        }
        else
        {
            if(!isAfloat)
            {
                isJumping = true
                jump()
            }
        }
    }
    fun takeDamage(damageTaken: Float)
    {
        var damageToSubtract = (damageTaken * (1f-attackResistance)) - attackDefence
        if(hp >= 0)
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
    fun move(direction: Int)
    {
        if(!varOrCache.HorizontalDirectionArray.contains(direction))
        {
            throw Exception("Inputted an invalid direction (Horizontal needed)")
        }
        else
        {
            position.x += 1f//change this later
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
            body.
        }
    }
    //Getters

}