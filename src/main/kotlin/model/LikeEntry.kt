package model


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID


@Table(name = "like_entry")
data class LikeEntry(
    @Id
    val id:Long=0L,
    @Column("user_id")
    val userId:UUID=UUID.randomUUID(),
    @Column("entry_id")
    val entryId:Long
) {
    constructor(userId: UUID,entryId: Long) : this(
        id=0L,
        userId = userId,
        entryId = entryId
    ) {

    }
}
