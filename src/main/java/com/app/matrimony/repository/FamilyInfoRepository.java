package com.app.matrimony.repository;

import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.FamilyInfo;

@Repository
public interface FamilyInfoRepository extends WriteableRepository<FamilyInfo, UUID> {

}
