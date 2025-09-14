package model


import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
import org.springframework.data.relational.core.mapping.Table
import java.util.UUID


@Table(name="author_follow")
data class AuthorFollow(
    @Id
    val id:Long=0L,
    @Column("author_id")
    val authorId:UUID=UUID.randomUUID(),
    @Column("reader_id")
    val readerId: UUID= UUID.randomUUID()
) {
    constructor(authorId: UUID, userId: UUID) : this(
        id=0L,
        authorId=authorId,
        readerId=userId
    )
}
