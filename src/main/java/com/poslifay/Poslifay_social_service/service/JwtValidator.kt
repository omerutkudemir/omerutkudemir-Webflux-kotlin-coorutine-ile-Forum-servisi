package com.poslifay.Poslifay_social_service.service

import com.nimbusds.jose.JOSEException
import com.nimbusds.jose.JWSVerifier
import com.nimbusds.jose.crypto.RSASSAVerifier
import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.RSAKey
import com.nimbusds.jwt.JWTClaimsSet
import com.nimbusds.jwt.SignedJWT
import com.poslifay.Poslifay_social_service.exception.InvalidTokenException
import org.springframework.stereotype.Service
import java.util.*
import java.security.interfaces.RSAPublicKey

@Service
class JwtValidator(
    private val jwkProvider: JwkProvider
) {

    suspend fun validateAndExtractUserId(token: String): UUID {
        try {
            val signedJWT = SignedJWT.parse(token)
            val kid = signedJWT.header.keyID

            val jwk: JWK = jwkProvider.getJwk(kid)
            println(jwk)

            return verifySignatureAndClaims(signedJWT, jwk)
        } catch (e: Exception) {
            throw InvalidTokenException("Token parse hatası", e)
        }
    }

    private fun verifySignatureAndClaims(signedJWT: SignedJWT, jwk: JWK): UUID {
        try {
            val publicKey: RSAPublicKey = (jwk as RSAKey).toRSAPublicKey()
            val verifier: JWSVerifier = RSASSAVerifier(publicKey)

            if (!signedJWT.verify(verifier)) throw InvalidTokenException("Geçersiz imza")

            val claimsSet: JWTClaimsSet = signedJWT.jwtClaimsSet

            if (claimsSet.expirationTime.before(Date())) throw InvalidTokenException("Token süresi dolmuş")

            val userIdClaim = claimsSet.getClaim("userId")
                ?: throw InvalidTokenException("userId claim'i bulunamadı")

            return try {
                UUID.fromString(userIdClaim.toString())
            } catch (e: IllegalArgumentException) {
                throw InvalidTokenException("Geçersiz UUID formatı: $userIdClaim", e)
            }

        } catch (e: JOSEException) {
            throw InvalidTokenException("Doğrulama hatası", e)
        }
    }
}
