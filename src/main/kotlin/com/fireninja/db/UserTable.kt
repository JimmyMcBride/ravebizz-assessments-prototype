package com.fireninja.db

import org.jetbrains.exposed.sql.Table
import org.joda.time.DateTime

object UserTable : Table(name = "users") {
  val id = integer("id").primaryKey().autoIncrement()
  val email = varchar("email", 256)
  val firstName = varchar("first_name", 256)
  val lastName = varchar("last_name", 256)
  val password = text("password")
  val createdAt = datetime("created_at").clientDefault { DateTime.now() }
}