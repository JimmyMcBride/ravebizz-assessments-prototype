package com.fireninja.security

import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*

fun Application.configureSecurity() {
  JwtConfig.initialize(SECRET_KEY)
  install(Authentication) {
    jwt {
      verifier(JwtConfig.instance.verifier)
      validate { credential ->
        val claim = credential.payload.getClaim(JwtConfig.CLAIM).asInt()
        if (claim != null) {
          UserIdPrincipalForUser(claim)
        } else {
          null
        }
      }
    }
  }
}
