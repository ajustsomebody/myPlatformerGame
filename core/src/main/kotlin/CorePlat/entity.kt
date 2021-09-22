package CorePlat

import com.badlogic.gdx.Input
import java.lang.Exception


class entity() {
    operator fun set(startPosition: Position, hp: Float, jumpMaxDuration: UInt): entity
    { // Fully complete setter
        this.hp = hp;
        this.position = startPosition;
        this.jumpMaxDuration = jumpMaxDuration;
        return this;
    }

    //Variables itself
    public var hp: Float = 0f;
    public var position = Position();
    public var velocity = Float;
    public var isJumping: Boolean = false;
    public var isAfloat: Boolean = true;
    public var jumpPower: Float = 10f;
    public var jumpMaxDuration: UInt = 0.toUInt();
    public var jumpDuration = jumpMaxDuration;
    public var attackDefence: Int = 0; // really should make this float but not now
    public var attackResistance: Float = 1f;
    public var attackStrength: Float = 1f;
    public var xDirection: Int = Input.Keys.RIGHT;

    fun jump()
    {
        if(!(isJumping || isAfloat))
        {
            if(jumpDuration > 0.toUInt())
            {
                jumpDuration--
                position.Y+= jumpPower * 
                //continue later
            }
            else
            {

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
        if(!varOrCache.HorizontalDirectionArray.contains(direction)) throw Exception("Inputted an invalid direction (Horizontal needed)")

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

    }
    //Getters

}