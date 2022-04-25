package com.fireninja.security

import io.ktor.util.*
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

const val SECRET_KEY = "35462468"
const val ALGORITHM = "HmacSHA1"

val HASH_KEY = hex(SECRET_KEY)
val HMAC_KEY = SecretKeySpec(HASH_KEY, ALGORITHM)

fun hash(password: String): String {
  val hmac = Mac.getInstance(ALGORITHM)
  hmac.init(HMAC_KEY)
  return hex(hmac.doFinal(password.toByteArray(Charsets.UTF_8)))
}
