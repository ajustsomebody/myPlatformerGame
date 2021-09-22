package CorePlat


import com.badlogic.gdx.ApplicationAdapter
import com.badlogic.gdx.Gdx
import com.badlogic.gdx.Input
import com.badlogic.gdx.graphics.GL20
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.math.Interpolation.linear

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
    public var jumpPower = Float;
    public var jumpMaxDuration: UInt = 0.toUInt();
    public var jumpDuration = jumpMaxDuration;
    public var linearDefense: Int = 0;
    public var exponentialDefense: Float = 1f;

    fun jump()
    {
        if(!(isJumping || isAfloat))
        {
            if(jumpDuration > 0.toUInt())
            {
                jumpDuration--
                //continue later
            }
            else
            {

            }
        }
    }
    fun takeDamage(damageTaken: Float)
    {
        var damageToSubtract = (damageTaken * (1f-exponentialDefense)) - linearDefense
        if(hp >= 0)
        {
            if((hp - damageToSubtract) < 0f) damageToSubtract = hp // possible float decimal miscalculation
        }
        else
        {

        }

        this.hp -= damageTaken // what if hp is less than 0 by normal?

    }
    fun attack(victim: entity)
    {

    }
    fun update()
    {

    }
    //Getters

}