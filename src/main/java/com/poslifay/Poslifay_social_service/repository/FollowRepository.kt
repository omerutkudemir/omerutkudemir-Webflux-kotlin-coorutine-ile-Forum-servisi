package com.poslifay.Poslifay_social_service.repository

import model.AuthorFollow
import org.springframework.data.r2dbc.repository.R2dbcRepository
import java.util.*

interface FollowRepository : R2dbcRepository<AuthorFollow, Long> {

    suspend fun deleteByReaderIdAndAuthorId(readerId: UUID, authorId: UUID)

}
