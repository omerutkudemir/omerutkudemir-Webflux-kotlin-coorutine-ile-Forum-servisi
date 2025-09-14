package model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID

@Table("like_entry_response")
data class LikeEntryResponse(
    @Id
    val id: Long? = null,
    @Column("user_id")
    val userId: UUID,
    @Column("entry_response_id")
    val entryResponseId: Long?
) {
    constructor(userId: UUID,entryResponseId: Long?) : this(
        id=null,
        userId=userId,
        entryResponseId = entryResponseId
    )
}
