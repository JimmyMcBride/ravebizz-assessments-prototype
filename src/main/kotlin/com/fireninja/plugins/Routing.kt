package com.fireninja.plugins

import com.fireninja.repository.Repository
import com.fireninja.routes.authRoutes
import com.fireninja.routes.root
import io.ktor.application.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureRouting() {
  val userRepository by inject<Repository>()

  routing {
    root()
    authRoutes(repository = userRepository)
  }
}
