package com.aryan.repository;

import com.aryan.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CityRepository extends JpaRepository<City,Long> {
    Boolean existsByCityCode(String cityCode);
    Boolean existsByCityCodeAndIdNot(String cityCode,Long Id);
    Page<City> findByCountryCodeIgnoreCase(String countryCode, Pageable pageable);

    @Query("""
        SELECT c FROM City c
        WHERE lower(c.name) like lower(concat('%', :keyword, '%')) 
        OR lower(c.cityCode) like lower(concat('%', :keyword, '%')) 
        OR lower(c.countryCode) like lower(concat('%', :keyword, '%'))
        OR lower(c.countryName) like lower(concat('%', :keyword, '%')) 
        OR lower(c.regionCode) like lower(concat('%', :keyword, '%'))
    """)
    Page<City> searchByKeyword(String keyword, Pageable pageable);
}
