package CorePlat

import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.*


object varOrCache {
    //caches
    var HorizontalDirectionArray: IntArray = IntArray(2)
    var DebugMode: Boolean = true;
    init {
        HorizontalDirectionArray[0] = Keys.LEFT
        HorizontalDirectionArray[1] = Keys.RIGHT
    }
    fun Int.GetDirectionValue() : Int?
    {
        if(this == Input.Keys.LEFT) return -1
        else if(this == Input.Keys.RIGHT) return 1
        else return null
    }
}