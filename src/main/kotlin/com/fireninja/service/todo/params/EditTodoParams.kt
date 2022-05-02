package com.fireninja.service.todo.params

data class EditTodoParams(
  val id: Int,
  val title: String? = null,
  val description: String? = null,
  val completed: Boolean? = null,
)
