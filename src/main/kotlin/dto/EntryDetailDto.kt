package dto

import java.time.LocalDateTime

data class EntryDetailDto(
    val id:Long,
    val createdBy:String,
    val subjectHeader:String="",
    val subjectDescription:String="",
    val createdAt: LocalDateTime? = null,
    val likeCount:Long=0L
)
