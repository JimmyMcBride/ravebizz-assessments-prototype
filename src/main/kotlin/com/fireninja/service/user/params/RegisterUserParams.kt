package com.fireninja.service.user.params

data class RegisterUserParams(
  val firstName: String,
  val lastName: String,
  val email: String,
  val password: String
)
