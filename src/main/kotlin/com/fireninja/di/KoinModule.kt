package com.fireninja.di

import com.fireninja.repository.Repository
import com.fireninja.repository.RepositoryImpl
import com.fireninja.service.todo.TodoService
import com.fireninja.service.todo.TodoServiceImpl
import com.fireninja.service.user.UserService
import com.fireninja.service.user.UserServiceImpl
import org.koin.dsl.module

val koinModule = module {
  single<UserService> {
    UserServiceImpl()
  }
  single<TodoService> {
    TodoServiceImpl()
  }
  single<Repository> {
    val userService by inject<UserService>()
    val todoService by inject<TodoService>()
    RepositoryImpl(userService, todoService)
  }
}
