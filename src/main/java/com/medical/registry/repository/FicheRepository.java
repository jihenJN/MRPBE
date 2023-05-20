package com.medical.registry.repository;

import com.medical.registry.domain.Fiche;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Fiche entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FicheRepository extends MongoRepository<Fiche, String> {}
