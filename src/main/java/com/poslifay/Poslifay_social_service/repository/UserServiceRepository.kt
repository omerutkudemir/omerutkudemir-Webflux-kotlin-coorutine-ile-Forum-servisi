package com.poslifay.Poslifay_social_service.repository

import com.netflix.appinfo.InstanceInfo
import com.netflix.discovery.EurekaClient
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.bodyToMono
import java.util.*
@Service
class UserServiceRepository(
    private val eurekaClient: EurekaClient,
    private val webClient: WebClient
) {

    companion object {
        private const val SERVICE_NAME = "poslifay"
        private const val USERNAME_PATH = "/api/v1/users/get_username"
    }

    suspend fun getUserNames(userIds: List<UUID>): List<String> {
        val application = eurekaClient.getApplication(SERVICE_NAME)
            ?: throw IllegalStateException("Service not found in Eureka: $SERVICE_NAME")

        val instances = application.instances
        if (instances.isEmpty()) {
            throw IllegalStateException("No instances available for service: $SERVICE_NAME")
        }

        // UP durumundaki instance'ları filtrele
        val upInstances = instances.filter { it.status == InstanceInfo.InstanceStatus.UP }
        if (upInstances.isEmpty()) {
            throw IllegalStateException("No UP instances for service: $SERVICE_NAME")
        }

        // Random bir UP instance seç
        val instance = upInstances.random()
        val userIdsParam = userIds.joinToString(",") { it.toString() }

        // URL oluştur (http://host:port/path)
        val url = "http://${instance.hostName}:${instance.port}$USERNAME_PATH?userIds=$userIdsParam"

        return webClient.get()
            .uri(url)
            .retrieve()
            .bodyToMono<List<String>>()
            .awaitSingle()
    }

    suspend fun getUserName(userIds: Flow<UUID>): Flow<String> = flow {
        val allUserIds = userIds.toList()

        if (allUserIds.isNotEmpty()) {
            val usernames = getUserNames(allUserIds)
            allUserIds.forEachIndexed { index, _ ->
                emit(usernames[index])
            }
        }
    }

    // Eureka durumunu kontrol etmek için yardımcı metod
    fun checkEurekaStatus(): String {
        return try {
            val app = eurekaClient.getApplication(SERVICE_NAME)
            if (app == null) {
                "Service $SERVICE_NAME not found in Eureka"
            } else {
                val upInstances = app.instances.count { it.status == InstanceInfo.InstanceStatus.UP }
                "Service $SERVICE_NAME found with ${app.instances.size} instances ($upInstances UP)"
            }
        } catch (e: Exception) {
            "Eureka check failed: ${e.message}"
        }
    }
}