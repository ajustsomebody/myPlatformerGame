package CorePlat

import CorePlat.varOrCache.GetDirectionValue
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

/*    operator fun set(startPosition: Vector2,
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
    }*/

    //Variables

    var bodyDefinition: BodyDef = BodyDef();    var body: Body? = null

    var hp: Float = 0f;
    var attackDefence: Float = 0f;
    var attackResistance: Float = 1f;
 // var attackDelay: Float = 1.toseconds();
    var attackStrength: Float = 1f

    var position: Vector2 get() {return body!!.position} set(newPos: Vector2) { body!!.position.set(newPos.x, newPos.y)}
    // ^ I need to keep this for better usability, i highly doubt if i will ever use it though
    var walkSpeed: Float = 1f;
    var runSpeed: Float = walkSpeed * 1.5f;
    var xDirection: Int = Input.Keys.RIGHT;
    var maxWalkVelocity: Float = 10f // needs proper scaling.
    var maxRunVelocity: Float = 15f  // needs proper scaling.
    var changeDirectionBonus: Float = 2f

    var jumpPower: Float = 10f;
    var jumpMaxDuration: UInt = 290.toUInt()
    var jumpDuration = jumpMaxDuration;
    var isJumping: Boolean = false;

    var movementStatus: movement = movement.idle

    var isAfloat: Boolean = false;
    var isIdle: Boolean = true
        get() = movementStatus == movement.idle
    var isRunning: Boolean = false
        get() = movementStatus == movement.run
    var isWalking: Boolean = false
        get() = movementStatus == movement.walk
    var manuallyMovedLastFrame = false;
    var calledMoveThisFrame = false
    var isRunningOrMoving: Boolean = false
        get() {return movementStatus != movement.idle}

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
        println("JUMPEDDDDDDDDDDDDDDDDDDDDDDDDDDDDDD")
        /*if(canInfiniJump)
        {
            jumpDuration = jumpMaxDuration
            isJumping == true
        }*/
    }
    enum class movement
    {
        run,
        walk,
        idle
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
        else
        {
            var xVelToAdd = 0f
            if(xDirection != direction) // if changed direction
            { // a lot of problems will arise if speeds are negative and i cant really convert them to uints rn
                if(manuallyMovedLastFrame) xVelToAdd += if(run) {runSpeed*changeDirectionBonus}
                                                        else {walkSpeed*changeDirectionBonus}
                else xVelToAdd += if(isRunning) runSpeed else walkSpeed
            }
            else
            {
                xVelToAdd += if(run) runSpeed else walkSpeed
            }
            if(run)
            {
                movementStatus = movement.run
                if(xVelToAdd + body!!.linearVelocity.x > maxRunVelocity) xVelToAdd = maxRunVelocity
            }
            else
            {
                movementStatus = movement.walk
                if(xVelToAdd + body!!.linearVelocity.x > maxWalkVelocity) xVelToAdd = maxWalkVelocity
            }
            xVelToAdd *= direction.GetDirectionValue()!!
            body!!.setLinearVelocity(body!!.linearVelocity.x + xVelToAdd, body!!.linearVelocity.y)
            calledMoveThisFrame = true
        }
        /*else { // if you want to display velocity it will show up as minuses as well, if this velocity system
            if (run == false) { // at its base doesn't cause any problems, just get the value without the

                if(direction != xDirection) // sudden change of directions
                {

                    if(direction == Input.Keys.RIGHT)
                    {
                        if(isRunningOrMoving)
                        {
                            xDirection = direction//todo: implement manuallyMovedLastFrame
                            body!!.setLinearVelocity(body!!.linearVelocity.x + (walkSpeed * changeDirectionBonus), body!!.linearVelocity.y)
                        }                             //todo: try to change force to setlinearvelocity in movement
                        else {                        //todo: fix being able to getto 130 vel by pressing left and right fast
                            xDirection = direction
                            body!!.setLinearVelocity(
                                body!!.linearVelocity.x + (walkSpeed * changeDirectionBonus / 1.5f),
                                body!!.linearVelocity.y
                            )
                        }
                        isWalking = true
                        isRunning = false
                        manuallyMovedLastFrame = true;
                    }
                    if(direction == Input.Keys.LEFT)
                    {
                        if(isRunningOrMoving)
                        {
                            xDirection = direction
                            body!!.setLinearVelocity(body!!.linearVelocity.x + (walkSpeed * changeDirectionBonus), body!!.linearVelocity.y)
                        }
                        else {
                            xDirection = direction
                            body!!.setLinearVelocity(
                                body!!.linearVelocity.x + (walkSpeed * changeDirectionBonus),
                                body!!.linearVelocity.y
                            )
                        }
                        isWalking = true
                        isRunning = false
                        manuallyMovedLastFrame = true;
                    }
                }
                else {
                    isWalking = true
                    isRunning = false
                    manuallyMovedLastFrame = true;
                    if (abs(body!!.linearVelocity.x) + walkSpeed <= maxWalkVelocity) {
                        xDirection = direction // minus or plus
                        if (xDirection == Input.Keys.RIGHT) body!!.applyForce(
                            Vector2(walkSpeed * 1, 0f),
                            body!!.worldCenter,
                            true
                        )
                        if (xDirection == Input.Keys.LEFT) body!!.applyForce(
                            Vector2(walkSpeed * -1, 0f),
                            body!!.worldCenter,
                            true
                        )
                    } else {
                        var tempVar: Float = maxWalkVelocity - walkSpeed
                        xDirection = direction
                        if (xDirection == Input.Keys.RIGHT) body!!.applyForce(
                            Vector2(tempVar * 1, 0f),
                            body!!.worldCenter,
                            true
                        )
                        if (xDirection == Input.Keys.LEFT) body!!.applyForce(
                            Vector2(tempVar * -1, 0f),
                            body!!.worldCenter,
                            true
                        )
                    }
                }
                println("player walked")
            }
            else // run == true
            {
                if(direction != xDirection) // sudden change of directions
                {
                    if(direction == Input.Keys.RIGHT)
                    {
                        if(isRunningOrMoving)
                        {
                            xDirection = direction
                            body!!.setLinearVelocity(body!!.linearVelocity.x + (walkSpeed * changeDirectionBonus), body!!.linearVelocity.y)
                        }
                        else {
                            xDirection = direction
                            body!!.setLinearVelocity(
                                body!!.linearVelocity.x + (walkSpeed * changeDirectionBonus / 1.5f),
                                body!!.linearVelocity.y
                            )
                        }
                        isWalking = false
                        isRunning = true
                        manuallyMovedLastFrame = true;
                    }
                    if(direction == Input.Keys.LEFT)
                    {
                        if(isRunningOrMoving)
                        {
                            xDirection = direction
                            body!!.setLinearVelocity(body!!.linearVelocity.x + (walkSpeed * changeDirectionBonus), body!!.linearVelocity.y)
                        }
                        else {
                            xDirection = direction
                            body!!.setLinearVelocity(
                                body!!.linearVelocity.x + (walkSpeed * changeDirectionBonus),
                                body!!.linearVelocity.y
                            )
                        }
                        isWalking = false
                        isRunning = true
                        manuallyMovedLastFrame = true;
                    }
                }
                else {
                    isWalking = false;
                    isRunning = true;
                    manuallyMovedLastFrame = true;
                    if (abs(body!!.linearVelocity.x) + walkSpeed <= maxRunVelocity) {
                        xDirection = direction // minus or plus
                        if (xDirection == Input.Keys.RIGHT) body!!.applyForce(
                            Vector2(runSpeed * 1, 0f),
                            body!!.worldCenter,
                            true
                        )
                        if (xDirection == Input.Keys.LEFT) body!!.applyForce(
                            Vector2(runSpeed * -1, 0f),
                            body!!.worldCenter,
                            true
                        )
                        manuallyMovedLastFrame = true
                    } else {
                        var tempVar: Float = maxRunVelocity - runSpeed
                        xDirection = direction

                        if (xDirection == Input.Keys.RIGHT) body!!.applyForce(
                            Vector2(tempVar, 0f),
                            body!!.worldCenter,
                            true
                        )
                        if (xDirection == Input.Keys.LEFT) body!!.applyForce(
                            Vector2(tempVar * -1, 0f),
                            body!!.worldCenter,
                            true
                        )
                    }
                }
                println("player ran")
            }
        }*/

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
            println("at jump body")
            if(jumpDuration> 0.toUInt()) {
                body!!.setLinearVelocity(body!!.linearVelocity.x, body!!.linearVelocity.y + jumpPower * 10f)
                jumpDuration--
            }
            else
                isJumping=false
        }
        if(calledMoveThisFrame == false)
        {
            body!!.linearDamping = 6.8f
            /*var thresholdVelToStop = 0.10
            var velDecreased =  1f
            if(abs(body!!.linearVelocity.x)>0)
            {

                if(abs(body!!.linearVelocity.x)-velDecreased > thresholdVelToStop)
                    if(xDirection == Input.Keys.RIGHT)
                    {
                        body!!.setLinearVelocity()
                    }
                    else
                    {

                    }
                    if(xDirection== Input.Keys.RIGHT) body!!.setLinearVelocity(body!!.linearVelocity.x-velDecreased, body!!.linearVelocity.y)
                    else body!!.setLinearVelocity((abs(body!!.linearVelocity.x-velDecreased))*-1, body!!.linearVelocity.y)
                else
                {
                    body!!.setLinearVelocity(0f, body!!.linearVelocity.y)
                }
            }*/
        }
        manuallyMovedLastFrame = isRunningOrMoving
        calledMoveThisFrame = false
    }
    //Getters
}