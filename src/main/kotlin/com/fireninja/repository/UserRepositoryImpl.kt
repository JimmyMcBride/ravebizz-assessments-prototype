package com.fireninja.repository

import com.fireninja.security.JwtConfig
import com.fireninja.service.CreateUserParams
import com.fireninja.service.UserService
import com.fireninja.utils.BaseResponse

class UserRepositoryImpl(
  private val userService: UserService
) : UserRepository {
  override suspend fun registerUser(params: CreateUserParams): BaseResponse<Any> {
    return if (doesEmailExist(params.email)) {
      BaseResponse.ErrorResponse(
        message = "An account with this email already exists.",
      )
    } else {
      val user = userService.registerUser(params)
      if (user != null) {
        val token = JwtConfig.instance.createAccessToken(user.id)
        user.authToken = token
        BaseResponse.SuccessResponse(data = user)
      } else {
        BaseResponse.ErrorResponse(
          message = "Sorry, something went wrong."
        )
      }
    }
  }

  override suspend fun loginUser(
    email: String,
    password: String
  ): BaseResponse<Any> {
    TODO("Not yet implemented")
  }

  private suspend fun doesEmailExist(email: String): Boolean {
    return userService.findUserByEmail(email) != null
  }
}