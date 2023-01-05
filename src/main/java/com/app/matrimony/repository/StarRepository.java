package com.app.matrimony.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.Star;

@Repository
public interface StarRepository extends WriteableRepository<Star, UUID>{

}
