package nl.mpcjanssen.simpletask

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationManagerCompat
import nl.mpcjanssen.simpletask.util.broadcastTasklistChanged
import nl.mpcjanssen.simpletask.util.todayAsString

class MarkTaskDone : Service() {
    val TAG = "MarkTaskDone"

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand()")
        val taskId = intent.getStringExtra(Constants.EXTRA_TASK_ID)
        if (taskId == null) {
            Log.e(TAG, "'${Constants.EXTRA_TASK_ID}' not found in intent: $intent")
            return START_STICKY_COMPATIBILITY
        }
        Log.d(TAG, "task ID: $taskId")

        val todoList = TodoApplication.todoList
        val task = todoList.getTaskWithId(taskId)
        if (task == null) {
            Log.e(TAG, "task with id '$taskId' not found in todo list")
            return START_STICKY_COMPATIBILITY
        }
        Log.d(TAG, task.text)

        task.markComplete(todayAsString)

        broadcastTasklistChanged(TodoApplication.app.localBroadCastManager)
        with(NotificationManagerCompat.from(this)) {
            cancel(task.id.hashCode())
        }
        stopSelf()

        return START_STICKY_COMPATIBILITY
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}