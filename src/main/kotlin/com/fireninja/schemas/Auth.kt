package com.fireninja.schemas

import com.apurebase.kgraphql.schema.dsl.SchemaBuilder
import com.fireninja.repository.Repository
import com.fireninja.service.user.params.RegisterUserParams
import com.fireninja.service.user.params.LoginUserParams

fun SchemaBuilder.authSchema(repository: Repository) {
    query("register") {
      resolver { params: RegisterUserParams ->
        repository.registerUser(params)
      }
    }
  query("login") {
    resolver { params: LoginUserParams ->
      repository.loginUser(params)
    }
  }
}
