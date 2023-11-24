package com.develop.war.developWar.controller.security.token

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.exceptions.JWTVerificationException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.stream.Collectors

@Component
class GeradorJWTToken {
    val ROLES_AUTHORITIES = "authorities"

    @Value("\${security.config.key}")
    private val secret: String? = null

    @Value("\${security.config.prefix}")
    private val prefix: String? = null

    fun criaToken(jwtObject: GeradorToken): String {
        return try {
            println("O segredo é este aqui: $secret")
            val token: String = JWT.create().withIssuer("auth-api")
                .withSubject(jwtObject.usuario)
                .withIssuedAt(jwtObject.dataCriacaoToken)
                .withExpiresAt(jwtObject.dataExpiracaoToken)
                .withClaim(ROLES_AUTHORITIES, jwtObject.regras?.let { checkRoles(it) })
                .sign(Algorithm.HMAC256(secret))
            "$prefix $token"
        } catch (exception: Exception) {
            throw RuntimeException("Error while generating token", exception)
        }
    }

    fun verificaToken(token: String?): String {
        return try {
            JWT.require(Algorithm.HMAC256(secret))
                .withIssuer("auth-api")
                .build()
                .verify(token)
                .subject
        } catch (exception: JWTVerificationException) {
            ""
        }
    }

    private fun checkRoles(roles: List<String>): List<String> {
        return roles.stream().map { s: String ->
            "ROLE_" + s.replace(
                "ROLE_".toRegex(),
                ""
            )
        }.collect(Collectors.toList())
    }
}