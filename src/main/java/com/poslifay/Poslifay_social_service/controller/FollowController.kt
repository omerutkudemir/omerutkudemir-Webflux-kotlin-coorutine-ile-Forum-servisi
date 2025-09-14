package com.poslifay.Poslifay_social_service.controller

import com.poslifay.Poslifay_social_service.service.FollowService
import com.poslifay.Poslifay_social_service.service.JwtValidator
import dto.FollowReq
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/follow")
class FollowController(
    private val followService: FollowService,
    private val jwtValidator: JwtValidator
) {

    @PostMapping("/author_follow")
    suspend fun followAuthor(
        @RequestHeader("Authorization") authorizationHeader: String,
        @RequestBody followReq: FollowReq
    ): Boolean {
        val token = authorizationHeader.substring(7)
        val userId = jwtValidator.validateAndExtractUserId(token)
        return userId?.let {
            followService.followAuthor(followReq.authorId, it)
        } ?: false
    }

    @PostMapping("/author_unfollow")
    suspend fun unFollowAuthor(
        @RequestHeader("Authorization") authorizationHeader: String,
        @RequestBody followReq: FollowReq
    ): Boolean {
        val token = authorizationHeader.substring(7)
        val userId = jwtValidator.validateAndExtractUserId(token)
        return userId?.let {
            followService.unFollowAuthor(followReq.authorId, it)
        } ?: false
    }
}
