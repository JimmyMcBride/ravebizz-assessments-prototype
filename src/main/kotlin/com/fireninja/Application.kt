package com.fireninja

import com.fireninja.db.DatabaseFactory
import com.fireninja.plugins.*
import com.fireninja.security.configureSecurity
import io.ktor.application.*

fun main(args: Array<String>): Unit =
  io.ktor.server.netty.EngineMain.main(args)

fun Application.module() {
  DatabaseFactory.init()
  configureKoin()
  configureSecurity()
  configureRouting()
  configureGraphQL()
  configureSerialization()
  configureMonitoring()
  configureDefaultHeader()
  configureStatusPage()
}
