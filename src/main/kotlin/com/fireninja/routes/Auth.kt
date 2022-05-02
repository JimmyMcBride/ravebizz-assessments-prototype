package com.fireninja.routes

import com.fireninja.repository.Repository
import com.fireninja.service.user.params.RegisterUserParams
import com.fireninja.service.user.params.LoginUserParams
import io.ktor.application.*
import io.ktor.request.*
import io.ktor.routing.*

fun Application.authRoutes(repository: Repository) {
  routing {
    route("/auth") {
      post("/register") {
        val params = call.receive<RegisterUserParams>()
        val result = repository.registerUser(params)
//        call.respond(result.statusCode, result)
      }
      post("/login") {
        val params = call.receive<LoginUserParams>()
        val result = repository.loginUser(params)
//        call.respond(result.statusCode, result)
      }
    }
  }
}
