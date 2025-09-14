package com.poslifay.Poslifay_social_service.service

import com.poslifay.Poslifay_social_service.converters.ConvertToEntryDtoForList
import com.poslifay.Poslifay_social_service.repository.*
import dto.CreateEntryReq
import dto.EntryDtoForList
import dto.EntryResponseReq
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.reactive.awaitFirstOrNull
import kotlinx.coroutines.reactive.awaitSingle
import model.Entry
import model.EntryResponse
import model.LikeEntry
import model.LikeEntryResponse
import org.springframework.stereotype.Service
import reactor.core.publisher.Flux
import java.util.*
import kotlinx.coroutines.flow.map
@Service
class EntryService(
    private val entryRepository: EntryRepository,
    private val userServiceRepository: UserServiceRepository,
    private val entryResponseRepository: EntryResponseRepository,
    private val convertToEntryDtoForList: ConvertToEntryDtoForList,
    private val likeEntryResponseRepository: LikeEntryResponseRepository,
    private val likeEntryRepository: LikeEntryRepository

) {

    suspend fun getEntryList(): Flow<EntryDtoForList> {
        val entries = entryRepository.findAll().asFlow() // mono -> flow
        val userIds: Flow<UUID> = entries.map { it.userId }
        val names = userServiceRepository.getUserName(userIds)
        return convertToEntryDtoForList.convert(entries, names)
    }

    suspend fun createEntry(userId: UUID, createEntryReq: CreateEntryReq): Boolean {
        val entry = Entry(userId, createEntryReq.subjectHeader, createEntryReq.subjectDescription)
        val savedEntry = entryRepository.save(entry).awaitFirstOrNull()
        return savedEntry != null
    }

    suspend fun createEntryResponse(userId: UUID, entryResponse: EntryResponseReq, entryId: Long): Boolean {
        val response = EntryResponse(userId, entryResponse.entryResponse,entryId)
        entryResponseRepository.save(response).awaitFirstOrNull()
        return true
    }

    suspend fun likeEntry(userId: UUID, entryId: Long): Boolean {
        val like = LikeEntry(userId,entryId)
        likeEntryRepository.save(like).awaitFirstOrNull()
        return true
    }

    suspend fun likeEntryResonse(userId: UUID, entryResponseId: Long): Boolean {
        val entryResponse = entryResponseRepository.findById(entryResponseId).awaitFirstOrNull() ?: return false
        val likeResponse = LikeEntryResponse(userId,entryResponse.id)
        likeEntryResponseRepository.save(likeResponse).asFlow()
        return true
    }
}
