package com.aryan.repository;

import com.aryan.model.BaggagePolicy;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BaggagePolicyRepository extends JpaRepository<BaggagePolicy,Long> {

    Optional<BaggagePolicy> findByFareId(Long fareId);
    List<BaggagePolicy> findByAirlineId(Long airlineId);
    boolean existsByFareId(Long fareId);

}
