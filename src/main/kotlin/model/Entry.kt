package model

import org.springframework.data.annotation.Id
import org.springframework.data.annotation.Transient
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.time.LocalDateTime
import java.util.UUID

@Table("entry")
data class Entry(
    @Id
    val id: Long? = null,

    @Column("user_id")
    val userId: UUID,

    @Column("subject_header")
    val subjectHeader: String,

    @Column("subject_description")
    val subjectDescription: String,

    @Column("created_at")
    val createdAt: LocalDateTime? = null,

    @Column("like_count")
    val likeCount: Long? = 0L,


) {
    constructor(userId: UUID, subjectHeader: String,subjectDescription: String) : this(
        id=null,
        userId = userId,
        subjectHeader = subjectHeader,
        subjectDescription = subjectDescription
    )
}
