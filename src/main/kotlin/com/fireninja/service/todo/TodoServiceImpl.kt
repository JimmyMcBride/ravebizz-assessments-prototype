package com.fireninja.service.todo

import com.fireninja.db.TodoTable
import com.fireninja.models.Todo
import com.fireninja.service.todo.params.EditTodoParams
import com.fireninja.service.todo.params.NewTodoParams
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class TodoServiceImpl : TodoService {
  override suspend fun addTodo(
    params: NewTodoParams,
    userId: Int,
  ): Todo {
    return transaction {
      val statement = TodoTable.insert {
        it[title] = params.title
        it[description] = params.description
        it[completed] = params.completed!!
        it[this.userId] = userId
      }
      return@transaction rowToTodo(statement.resultedValues?.get(0)) ?: throw Exception("Could not add todo.")
    }
  }

  override suspend fun editTodo(params: EditTodoParams): Todo {
    return transaction {
      val currentTodo = TodoTable.select(where = {
        TodoTable.id eq params.id
      }).map {
        rowToTodo(it)
      }[0]
      if (currentTodo != null) {
        val updatedTodoId = TodoTable.update(where = {
          TodoTable.id eq params.id
        }) {
          it[title] = params.title ?: currentTodo.title
          it[description] = params.description ?: currentTodo.description
          it[completed] = params.completed ?: currentTodo.completed
        }
        return@transaction getTodoById(params.id)
      } else {
        throw Exception("Todo was not updated")
      }
    }
  }

  override suspend fun deleteTodo(todoId: Int): Boolean {
    return transaction {
      val statement = TodoTable.deleteWhere {
        TodoTable.id eq todoId
      }
      return@transaction statement != 0
    }
  }

  override suspend fun getTodosByUserId(userId: Int): List<Todo> {
    return transaction {
      val statement = TodoTable.select(where = {
        TodoTable.userId eq userId
      }).map {
        rowToTodo(it) ?: throw Exception("Could not get Todo")
      }
      return@transaction statement
    }
  }

  private fun getTodoById(todoId: Int): Todo {
    val todo = TodoTable.select(where = {
      TodoTable.id eq todoId
    }).map {
      rowToTodo(it)
    }[0]
    if (todo != null) {
      return todo
    } else {
      throw Exception("Could not find todo with that id.")
    }
  }

  private fun rowToTodo(row: ResultRow?): Todo? {
    return if (row == null) null
    else Todo(
      id = row[TodoTable.id],
      title = row[TodoTable.title],
      description = row[TodoTable.description],
      completed = row[TodoTable.completed],
    )
  }
}