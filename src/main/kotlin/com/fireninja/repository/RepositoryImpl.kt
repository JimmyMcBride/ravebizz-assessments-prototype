package com.fireninja.repository

import com.fireninja.models.Todo
import com.fireninja.models.User
import com.fireninja.security.JwtConfig
import com.fireninja.service.todo.TodoService
import com.fireninja.service.todo.params.EditTodoParams
import com.fireninja.service.todo.params.NewTodoParams
import com.fireninja.service.user.params.RegisterUserParams
import com.fireninja.service.user.UserService
import com.fireninja.service.user.params.LoginUserParams
import org.mindrot.jbcrypt.BCrypt

class RepositoryImpl(
  private val userService: UserService,
  private val todoService: TodoService,
) : Repository {
  override suspend fun registerUser(params: RegisterUserParams): User {
    return if (doesEmailExist(params.email)) {
      throw Exception("An account with this email already exists.")
    } else {
      val user = userService.registerUser(params)
      if (user != null) {
        val token = JwtConfig.instance.createAccessToken(user.id)
        user.authToken = token
        user
      } else {
        throw Exception("Sorry, something went wrong.")
      }
    }
  }

  override suspend fun loginUser(
    params: LoginUserParams,
  ): User {
    val user = userService.findUserByEmail(params.email)
    return if (user != null) {
      if (BCrypt.checkpw(params.password,
          userService.getUserHashedPassword(user.email))
      ) {
        val token = JwtConfig.instance.createAccessToken(user.id)
        user.authToken = token
        user
      } else {
        throw Exception("Sorry, wrong password. Try again.")
      }
    } else {
      throw Exception("Sorry, can't find a use with that id.")
    }
  }

  override fun addTodo(
    params: NewTodoParams,
    userId: Int,
  ): Todo {
    return todoService.addTodo(params,
      userId)
  }

  override fun getTodosByUserId(userId: Int): List<Todo> {
    return todoService.getTodosByUserId(userId)
  }

  override fun getTodoById(todoId: Int): Todo {
    return todoService.getTodoById(todoId)
  }

  override fun editTodo(
    params: EditTodoParams,
    userId: Int,
  ): Todo {
    return todoService.editTodo(params)
  }

  override fun deleteTodo(todoInt: Int): Boolean {
    return todoService.deleteTodo(todoInt)
  }

  private suspend fun doesEmailExist(email: String): Boolean {
    return userService.findUserByEmail(email) != null
  }
}