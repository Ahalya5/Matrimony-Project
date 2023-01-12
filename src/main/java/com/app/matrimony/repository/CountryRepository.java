package com.app.matrimony.repository;

import java.util.Optional;
import java.util.UUID;

import com.app.matrimony.config.WriteableRepository;
import com.app.matrimony.entity.Country;

	public interface CountryRepository extends WriteableRepository<Country, UUID> {

		public Optional<Country> findByCountryName(String name);
	}



