package com.fireninja.plugins

import com.fireninja.repository.UserRepository
import com.fireninja.routes.authRoutes
import com.fireninja.routes.root
import com.fireninja.security.UserIdPrincipalForUser
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
  val userRepository by inject<UserRepository>()

  routing {
    root()
    authRoutes(repository = userRepository)
  }
}
