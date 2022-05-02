package com.fireninja.service.user

import com.fireninja.db.DatabaseFactory.dbQuery
import com.fireninja.db.UserTable
import com.fireninja.models.User
import com.fireninja.service.user.params.RegisterUserParams
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.mindrot.jbcrypt.BCrypt

class UserServiceImpl : UserService {
  override suspend fun registerUser(params: RegisterUserParams): User? {
    return transaction {
      val statement = UserTable.insert {
        it[email] = params.email
        it[password] = BCrypt.hashpw(params.password, BCrypt.gensalt())
        it[firstName] = params.firstName
        it[lastName] = params.lastName
      }
      return@transaction rowToUser(statement.resultedValues?.get(0))
    }

  }

  override suspend fun findUserByEmail(email: String): User? {
    return dbQuery {
      UserTable.select { UserTable.email.eq(email) }.map {
        rowToUser(it)
      }.singleOrNull()
    }
  }

  override suspend fun findUserById(id: Int): User? {
    return dbQuery {
      UserTable.select { UserTable.id.eq(id) }.map {
        rowToUser(it)
      }.singleOrNull()
    }
  }

  override suspend fun getUserHashedPassword(email: String): String {
    return dbQuery {
      UserTable.select { UserTable.email.eq(email) }.map {
        it[UserTable.password]
      }.single()
    }
  }

  private fun rowToUser(row: ResultRow?): User? {
    return if (row == null) null
    else User(
      id = row[UserTable.id],
      firstName = row[UserTable.firstName],
      lastName = row[UserTable.lastName],
      email = row[UserTable.email],
      createdAt = row[UserTable.createdAt].toString(),
    )
  }
}