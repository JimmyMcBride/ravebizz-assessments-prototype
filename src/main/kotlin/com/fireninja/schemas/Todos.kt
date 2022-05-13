package com.fireninja.schemas

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.fireninja.repository.Repository
import com.fireninja.security.UserIdPrincipalForUser
import com.fireninja.service.todo.params.EditTodoParams
import com.fireninja.service.todo.params.NewTodoParams

fun SchemaBuilder.todoSchema(repository: Repository) {
  query("todos") {
    resolver { ctx: Context ->
      val userIdPrincipal = ctx.get<UserIdPrincipalForUser>()
      if (userIdPrincipal != null) {
        repository.getTodosByUserId(userIdPrincipal.id)
      } else {
        throw Exception("No user is currently authenticated")
      }
    }
  }
  query("todo") {
    resolver { todoId: Int, ctx: Context ->
      val userIdPrincipal = ctx.get<UserIdPrincipalForUser>()
      if (userIdPrincipal != null) {
        repository.getTodoById(todoId)
      } else {
        throw Exception("No user is currently authenticated")
      }
    }
  }
  mutation("addTodo") {
    resolver { newTodo: NewTodoParams, ctx: Context ->
      val userIdPrincipal = ctx.get<UserIdPrincipalForUser>()
      if (userIdPrincipal != null) {
        repository.addTodo(newTodo,
          userIdPrincipal.id)
      } else {
        throw Exception("Try logging in to add todos.")
      }
    }
  }
  mutation("editTodo") {
    resolver { editTodo: EditTodoParams, ctx: Context ->
      val userIdPrincipal = ctx.get<UserIdPrincipalForUser>()
      if (userIdPrincipal != null) {
        repository.editTodo(editTodo,
          userIdPrincipal.id)
      } else {
        throw Exception("Try logging in to add todos.")
      }
    }
  }
  mutation("deleteTodo") {
    resolver { todoId: Int, ctx: Context ->
      val userIdPrincipal = ctx.get<UserIdPrincipalForUser>()
      if (userIdPrincipal != null) {
        repository.deleteTodo(todoId)
      } else {
        throw Exception("Try logging in to add todos.")
      }
    }
  }
}