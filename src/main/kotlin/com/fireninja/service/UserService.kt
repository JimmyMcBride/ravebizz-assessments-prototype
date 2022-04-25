package com.fireninja.service

import com.fireninja.models.User

interface UserService {
  suspend fun registerUser(params: CreateUserParams): User?
  suspend fun findUserByEmail(email: String): User?
}