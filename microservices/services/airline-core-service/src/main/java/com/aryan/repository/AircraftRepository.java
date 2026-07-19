package com.aryan.repository;

import com.aryan.model.Aircraft;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AircraftRepository extends JpaRepository<Aircraft,Long> {

    List<Aircraft> findByAirlineId(Long airlineId);
    Boolean existsByCode(String code);
    Optional<Aircraft> findByIdAndAirlineId(Long id, Long airlineId);

}
