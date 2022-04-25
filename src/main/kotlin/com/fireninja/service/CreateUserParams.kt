package com.fireninja.service

data class CreateUserParams(
  val firstName: String,
  val lastName: String,
  val email: String,
  val password: String
)
