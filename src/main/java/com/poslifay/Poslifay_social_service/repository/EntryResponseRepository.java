package com.poslifay.Poslifay_social_service.repository;

import model.EntryResponse;
import model.LikeEntryResponse;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface EntryResponseRepository extends R2dbcRepository<EntryResponse,Long> {
}
