package com.poslifay.Poslifay_social_service.controller

import com.poslifay.Poslifay_social_service.service.EntryService
import com.poslifay.Poslifay_social_service.service.JwtValidator
import dto.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.toList
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/entry")
class EntryController(
    private val entryService: EntryService,
    private val jwtValidator: JwtValidator
) {

    @GetMapping("/entry_list")
    suspend fun getEntryList(): Flow<EntryDtoForList> {
        return try {
            entryService.getEntryList() // Flow -> List
        } catch (e: Exception) {
            println("Hata: $e")
            throw e
        }
    }

    @PostMapping("/create_entry")
    suspend fun createEntry(
        @RequestHeader("Authorization") authorizationHeader: String,
        @RequestBody createEntryReq: CreateEntryReq
    ): Boolean {
        val token = authorizationHeader.substring(7)
        val userId = jwtValidator.validateAndExtractUserId(token)
        return entryService.createEntry(userId, createEntryReq)
    }

    @PostMapping("/create_entry_response")
    suspend fun createEntryResponse(
        @RequestHeader("Authorization") authorizationHeader: String,
        @RequestHeader("entry_id") entryId: Long,
        @RequestBody entryResponse: EntryResponseReq
    ): Boolean {
        val token = authorizationHeader.substring(7)
        val userId = jwtValidator.validateAndExtractUserId(token)
        return entryService.createEntryResponse(userId, entryResponse, entryId)
    }

    @PostMapping("/like_entry")
    suspend fun likeEntry(
        @RequestHeader("Authorization") authorizationHeader: String,
        @RequestBody likeReq: LikeReq
    ): Boolean {
        val token = authorizationHeader.substring(7)
        val userId = jwtValidator.validateAndExtractUserId(token)
        println()
        return entryService.likeEntry(userId, likeReq.entryId)

    }

    @PostMapping("/like_entry_response")
    suspend fun likeEntryResponse(
        @RequestHeader("Authorization") authorizationHeader: String,
        @RequestBody likeReq: LikeEntryResonseReq
    ): Boolean {
        val token = authorizationHeader.substring(7)
        val userId = jwtValidator.validateAndExtractUserId(token)
        return entryService.likeEntryResonse(userId, likeReq.entryResponseId)

    }
}
