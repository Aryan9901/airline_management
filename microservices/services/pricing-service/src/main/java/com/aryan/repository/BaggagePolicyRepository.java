package com.aryan.repository;

import com.aryan.model.BaggagePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Repository for managing {@link BaggagePolicy} entities.
 */
public interface BaggagePolicyRepository extends JpaRepository<BaggagePolicy,Long> {

    /**
     * Retrieves the baggage policy associated with a specific fare.
     *
     * @param fareId fare identifier
     * @return matching baggage policy, if found
     */
    Optional<BaggagePolicy> findByFareId(Long fareId);

    /**
     * Retrieves all baggage policies for a specific airline.
     *
     * @param airlineId airline identifier
     * @return list of baggage policies
     */
    List<BaggagePolicy> findByAirlineId(Long airlineId);

    /**
     * Checks whether a baggage policy exists for a specific fare.
     *
     * @param fareId fare identifier
     * @return true if a policy exists
     */
    boolean existsByFareId(Long fareId);

}
