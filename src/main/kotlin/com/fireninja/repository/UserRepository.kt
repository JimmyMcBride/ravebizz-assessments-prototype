package com.fireninja.repository

import com.fireninja.service.CreateUserParams
import com.fireninja.utils.BaseResponse

interface UserRepository {
  suspend fun registerUser(params: CreateUserParams): BaseResponse<Any>
  suspend fun loginUser(email: String, password: String): BaseResponse<Any>
}