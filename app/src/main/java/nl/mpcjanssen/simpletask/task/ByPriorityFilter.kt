/**
 * This file is part of Todo.txt Touch, an Android app for managing your todo.txt file (http://todotxt.com).

 * Copyright (c) 2009-2012 Todo.txt contributors (http://todotxt.com)

 * LICENSE:

 * Todo.txt Touch is free software: you can redistribute it and/or modify it under the terms of the GNU General Public
 * License as published by the Free Software Foundation, either version 2 of the License, or (at your option) any
 * later version.

 * Todo.txt Touch is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more
 * details.

 * You should have received a copy of the GNU General Public License along with Todo.txt Touch.  If not, see
 * //www.gnu.org/licenses/>.

 * @author Todo.txt contributors @yahoogroups.com>
 * *
 * @license http://www.gnu.org/licenses/gpl.html
 * *
 * @copyright 2009-2012 Todo.txt contributors (http://todotxt.com)
 */
package nl.mpcjanssen.simpletask.task

/**
 * A applyFilter that matches Tasks containing the specified priorities

 * @author Tim Barlotta
 */
class ByPriorityFilter(
    private val priorities: List<Priority>, private val not: Boolean
) : TaskFilter {

    // FOR TESTING ONLY, DO NOT USE IN APPLICATION

    override fun apply(task: Task): Boolean {
        return if (not) !filter(task) else filter(task)
    }

    fun filter(input: Task): Boolean {
        return priorities.isEmpty() || priorities.contains(input.priority)
    }
}