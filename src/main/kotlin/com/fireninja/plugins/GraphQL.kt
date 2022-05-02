package com.fireninja.plugins

import com.apurebase.kgraphql.Context
import com.apurebase.kgraphql.GraphQL
import com.fireninja.repository.Repository
import com.fireninja.schemas.authSchema
import com.fireninja.schemas.todoSchema
import com.fireninja.security.UserIdPrincipalForUser
import com.fireninja.service.user.UserService
import io.ktor.application.*
import io.ktor.auth.*
import org.koin.ktor.ext.inject

fun Application.configureGraphQL() {
  install(GraphQL) {
    val repository by inject<Repository>()
    val userService by inject<UserService>()

    playground = true
    wrap {
      authenticate(
        optional = true,
        build = it
      )
    }
    context { call ->
      // access to authentication is only available if this is wrapped inside a `authenticate` before hand.
      call.authentication.principal<UserIdPrincipalForUser>()?.let {
        +it
      }
    }
    schema {
      authSchema(repository)
      todoSchema(repository)
      query("hello") {
        resolver { ctx: Context ->
          val userIdPrincipal = ctx.get<UserIdPrincipalForUser>()
          if (userIdPrincipal != null) {
            val user = userService.findUserById(userIdPrincipal.id) ?: throw Exception("Invalid token")
            "Hello ${user.firstName} ${user.lastName}"
          } else {
            "No user is currently authenticated"
          }
        }
      }
    }
  }
}
