package com.fireninja.service.todo.params

data class NewTodoParams(
  val title: String,
  val description: String,
  var completed: Boolean? = false,
)
