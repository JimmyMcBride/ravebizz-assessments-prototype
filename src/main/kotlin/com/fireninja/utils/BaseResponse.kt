package com.fireninja.utils

import io.ktor.http.*

sealed class BaseResponse<T>(
  open val statusCode: HttpStatusCode = HttpStatusCode.OK
) {
  data class SuccessResponse<T>(
    val data: T? = null,
    val message: String? = null,
  ) : BaseResponse<T>()

  data class ErrorResponse<Nothing>(
    val message: String? = null,
    val exception: Exception? = null,
    override var statusCode: HttpStatusCode = HttpStatusCode.BadRequest
  ) : BaseResponse<Nothing>(statusCode)
}
