package com.fireninja.di

import com.fireninja.repository.UserRepository
import com.fireninja.repository.UserRepositoryImpl
import com.fireninja.service.UserService
import com.fireninja.service.UserServiceImpl
import org.koin.dsl.module

val koinModule = module {
  single<UserService> {
    UserServiceImpl()
  }
  single<UserRepository> {
    val userService by inject<UserService>()
    UserRepositoryImpl(userService)
  }
}
