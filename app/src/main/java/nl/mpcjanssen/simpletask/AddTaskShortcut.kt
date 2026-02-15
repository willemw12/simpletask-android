/**
 * This file is part of Simpletask.

 * Copyright (c) 2009-2012 Todo.txt contributors (http://todotxt.com)
 * Copyright (c) 2013- Mark Janssen

 * LICENSE:

 * Todo.txt Touch is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any
 * later version.

 * Todo.txt Touch is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.

 * You should have received a copy of the GNU General Public License along with Todo.txt Touch.  If not, see
 * //www.gnu.org/licenses/>.

 * @author Mark Janssen
 * *
 * @license http://www.gnu.org/licenses/gpl.html
 * *
 * @copyright 2009-2012 Todo.txt contributors (http://todotxt.com)
 * *
 * @copyright 2013- Mark Janssen
 */
package nl.mpcjanssen.simpletask

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.core.content.pm.ShortcutInfoCompat
import androidx.core.content.pm.ShortcutManagerCompat
import androidx.core.graphics.drawable.IconCompat

class AddTaskShortcut : ThemedNoActionBarActivity() {
    public override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, "onCreate()")
        super.onCreate(savedInstanceState)
        setupShortcut()
        finish()
    }

    private fun setupShortcut() {
        val shortcutIntent = Intent(this, AddTask::class.java).apply {
            action = Intent.ACTION_MAIN
        }

        val name = getString(R.string.shortcut_addtask_name)
        val iconRes = IconCompat.createWithResource(this, R.mipmap.ic_launcher)

        val shortcutInfo = ShortcutInfoCompat.Builder(this, "add_task_shortcut")
            .setShortLabel(name)
            .setIcon(iconRes)
            .setIntent(shortcutIntent)
            .build()

        val resultIntent = ShortcutManagerCompat.createShortcutResultIntent(this, shortcutInfo)
        if (resultIntent != null) {
            setResult(RESULT_OK, resultIntent)
        } else {
            setResult(RESULT_CANCELED)
        }
    }

    companion object {
        private val TAG = AddTaskShortcut::class.java.simpleName
    }
}
