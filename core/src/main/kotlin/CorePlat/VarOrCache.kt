package CorePlat

import com.badlogic.gdx.Input.*

var varOrCache = varOrCacheInitiator()

class varOrCacheInitiator {
    //caches
    var HorizontalDirectionArray: IntArray = IntArray(2)
    var DebugMode: Boolean = true;
    init {
        HorizontalDirectionArray[0] = Keys.LEFT
        HorizontalDirectionArray[1] = Keys.RIGHT
    }
}