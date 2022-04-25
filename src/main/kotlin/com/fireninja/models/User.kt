package com.fireninja.models

data class User(
  val id: Int,
  val firstName: String,
  val lastName: String,
  val email: String,
  var authToken: String? = null,
  val createdAt: String
)