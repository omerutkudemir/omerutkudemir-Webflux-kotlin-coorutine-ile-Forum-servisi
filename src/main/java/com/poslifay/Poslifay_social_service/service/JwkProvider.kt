package com.poslifay.Poslifay_social_service.service

import com.nimbusds.jose.jwk.JWK
import com.nimbusds.jose.jwk.JWKSet
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.cloud.client.ServiceInstance
import org.springframework.cloud.client.discovery.DiscoveryClient
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import java.text.ParseException
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

@Service
class JwkProvider(
        private val discoveryClient: DiscoveryClient,
        private val webClient: WebClient
) {

    companion object {
        const val SERVICE_NAME = "poslifay"
        const val JWKS_PATH = "/.well-known/jwks.json"
    }

    private val jwkCache = ConcurrentHashMap<String, JWK>()

    suspend fun getJwk(kid: String): JWK {
        jwkCache[kid]?.let { return it }

        val instances: List<ServiceInstance> = discoveryClient.getInstances(SERVICE_NAME)
        if (instances.isEmpty()) throw IllegalStateException("Eureka'da '$SERVICE_NAME' servisi bulunamadı!")

        val instance = instances[Random.nextInt(instances.size)]
        val jwksUrl = instance.uri.toString() + JWKS_PATH

        val jwksResponse = webClient.get()
                .uri(jwksUrl)
                .retrieve()
                .bodyToMono(String::class.java)
            .awaitSingle() // non-blocking olarak bekler

        return try {
            val jwkSet = JWKSet.parse(jwksResponse)
            val jwk = jwkSet.getKeyByKeyId(kid)
                    ?: throw RuntimeException("JWKS'de uygun anahtar bulunamadı: $kid")
            jwkCache[kid] = jwk
            jwk
        } catch (e: ParseException) {
            throw RuntimeException("JWKS parse edilemedi", e)
        }
    }
}
