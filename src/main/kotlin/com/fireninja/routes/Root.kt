package com.fireninja.routes

import com.fireninja.security.UserIdPrincipalForUser
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.root() {
  get("/") {
    call.application.environment.log.info("Hello from /api/v1!")
    call.respond(
      message = "Welcome to RaveBizz Assessments API!",
      status = HttpStatusCode.OK
    )
  }
  authenticate {
    get("/check-auth-status") {
      val principal = call.principal<UserIdPrincipalForUser>()
      val userId = principal!!.id

      call.respond(HttpStatusCode.OK, "Success!!! UserID: $userId")
    }
  }
}
