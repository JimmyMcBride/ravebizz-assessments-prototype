package com.fireninja.service.user

import com.fireninja.models.User
import com.fireninja.service.user.params.RegisterUserParams

interface UserService {
  fun registerUser(params: RegisterUserParams): User?
  suspend fun findUserByEmail(email: String): User?
  suspend fun getUserHashedPassword(email: String): String
  suspend fun findUserById(id: Int): User?
}