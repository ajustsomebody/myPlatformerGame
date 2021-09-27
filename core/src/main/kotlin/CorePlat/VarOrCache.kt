package CorePlat

import com.badlogic.gdx.Input
import com.badlogic.gdx.Input.*

var varOrCache = varOrCacheInitiator()

class varOrCacheInitiator {
    //caches
    val HorizontalDirectionArray: IntArray = IntArray(2){Input.Keys.RIGHT; Input.Keys.LEFT};
    var DebugMode: Boolean = true;
}