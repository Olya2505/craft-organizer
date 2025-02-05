package com.craftorganizer.projectservice.repository;

import com.craftorganizer.projectservice.model.Pattern;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PatternRepository extends MongoRepository<Pattern, String> {
    Optional<Pattern> findByPatternNameIgnoreCase(String patternName);
}
