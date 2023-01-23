package com.app.matrimony.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.HoioscopeDetails;

@Repository
public interface HoioscopeDetailsRepository extends WriteableRepository<HoioscopeDetails, UUID>{

}
