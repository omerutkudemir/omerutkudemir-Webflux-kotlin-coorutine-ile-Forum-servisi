package com.poslifay.Poslifay_social_service.service

import com.poslifay.Poslifay_social_service.repository.FollowRepository
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import model.AuthorFollow
import org.springframework.stereotype.Service
import java.util.UUID

@Service
class FollowService(private val followRepository: FollowRepository) {

    suspend fun followAuthor(authorId: UUID, userId: UUID): Boolean {
        return try {
            val authorFollow = AuthorFollow(authorId, userId)
            followRepository.save(authorFollow).awaitFirstOrNull() // suspend fonksiyon
            true
        } catch (e: Exception) {
            println("Takip ederken hata: $e")
            false
        }
    }

    suspend fun unFollowAuthor(authorId: UUID, readerId: UUID): Boolean {
        return try {
            followRepository.deleteByReaderIdAndAuthorId(readerId, authorId)
            true
        } catch (e: Exception) {
            println("Takibi kaldırırken hata: $e")
            false
        }
    }
}
