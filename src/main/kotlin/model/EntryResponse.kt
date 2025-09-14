package model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table(name = "entry_response")
data class EntryResponse(
    @Id
    val id: Long = 0L,

    @Column("user_id")
    val userId: UUID = UUID.randomUUID(),

    val response: String = "",

    @Column("like_count")
    val likeCount: Long = 0L,


    @Column("entry_id")
    val entryId: Long = 0L,

    @Column("created_at")
    val createdAt: LocalDateTime? = null
) {
    constructor(userId: UUID, entryResponse: String,entryId: Long) : this(
        id=0L,
        userId=userId,
        response = entryResponse,
        entryId = entryId

    )
}
