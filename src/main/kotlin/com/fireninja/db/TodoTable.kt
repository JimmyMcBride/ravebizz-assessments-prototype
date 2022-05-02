package com.fireninja.db

import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object TodoTable : Table(name = "todos") {
  val id = integer("id").primaryKey().autoIncrement()
  val title = varchar("title",
    256)
  val description = text("description")
  val completed = bool("completed")
  val userId = integer("user_id")
    .references(
      ref = UserTable.id,
      onDelete = ReferenceOption.CASCADE,
      onUpdate = ReferenceOption.CASCADE
    )
}