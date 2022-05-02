package com.fireninja.service.todo

import com.fireninja.models.Todo
import com.fireninja.service.todo.params.EditTodoParams
import com.fireninja.service.todo.params.NewTodoParams

interface TodoService {
  suspend fun addTodo(params: NewTodoParams, userId: Int): Todo
  suspend fun editTodo(params: EditTodoParams): Todo
  suspend fun deleteTodo(todoId: Int): Boolean
  suspend fun getTodosByUserId(userId: Int): List<Todo>
}