package nl.mpcjanssen.simpletask.util

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


open class ActionQueue(val qName: String) : Thread() {


    fun add(description: String, r: () -> Unit) {
        Log.i(qName, "-> $description")
        val coroutineScope = CoroutineScope(Dispatchers.Main + Job())
        coroutineScope.launch {
            withContext(Dispatchers.IO) {
                Log.i(qName, "<- $description")
                r.invoke()
            }
        }
    }
}

object FileStoreActionQueue : ActionQueue("FSQ")
