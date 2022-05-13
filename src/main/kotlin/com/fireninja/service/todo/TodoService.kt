package com.fireninja.service.todo

import com.fireninja.models.Todo
import com.fireninja.service.todo.params.EditTodoParams
import com.fireninja.service.todo.params.NewTodoParams

interface TodoService {
  fun addTodo(params: NewTodoParams, userId: Int): Todo
  fun editTodo(params: EditTodoParams): Todo
  fun deleteTodo(todoId: Int): Boolean
  fun getTodosByUserId(userId: Int): List<Todo>
  fun getTodoById(todoId: Int): Todo
}