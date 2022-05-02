package com.fireninja.models

data class Todo(
  val id: Int,
  val title: String,
  val description: String,
  val completed: Boolean,
)
