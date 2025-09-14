package com.poslifay.Poslifay_social_service.repository;

import model.LikeEntry;
import org.springframework.data.r2dbc.repository.R2dbcRepository;

public interface LikeEntryRepository extends R2dbcRepository<LikeEntry,Long> {

}
