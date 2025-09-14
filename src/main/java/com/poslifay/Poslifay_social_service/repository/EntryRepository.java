package com.poslifay.Poslifay_social_service.repository;

import model.Entry;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.data.repository.query.Param;
import reactor.core.publisher.Mono;

public interface EntryRepository extends R2dbcRepository<Entry,Long> {
    @Modifying
    @Query("UPDATE Entry e SET e.likeCount = e.likeCount + 1 WHERE e.id = :id")
    Mono<Integer> incrementLikeCount(@Param("id") Long id);

}
