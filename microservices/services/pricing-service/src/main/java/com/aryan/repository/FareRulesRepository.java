package com.aryan.repository;

import com.aryan.model.FareRules;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing {@link FareRules} entities.
 */
public interface FareRulesRepository extends JpaRepository<FareRules, Long> {

    /**
     * Retrieves fare rules associated with a specific fare.
     *
     * @param fareId fare identifier
     * @return matching fare rules, if found
     */
    Optional<FareRules> findByFareId(Long fareId);

    /**
     * Retrieves all fare rules for a specific airline.
     *
     * @param airlineId airline identifier
     * @return list of fare rules
     */
    List<FareRules> findByAirlineId(Long airlineId);

    /**
     * Checks whether fare rules exist for a specific fare.
     *
     * @param fareId fare identifier
     * @return true if fare rules exist
     */
    boolean existsByFareId(Long fareId);

}
