package com.aryan.repository;

import com.aryan.model.City;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Repository for managing {@link City} entities.
 *
 * Provides custom query methods for city retrieval,
 * search, and validation.
 */
public interface CityRepository extends JpaRepository<City,Long> {

    /**
     * Checks whether a city with the given code exists.
     *
     * @param cityCode city code
     * @return true if the city exists
     */
    Boolean existsByCityCode(String cityCode);

    /**
     * Checks whether a city with the given code exists,
     * excluding the specified city identifier.
     *
     * Used during updates to prevent duplicate codes.
     *
     * @param cityCode city code
     * @param Id city identifier to exclude
     * @return true if a conflicting city exists
     */
    Boolean existsByCityCodeAndIdNot(String cityCode, Long Id);

    /**
     * Retrieves cities belonging to a specific country.
     *
     * @param countryCode ISO country code
     * @param pageable pagination information
     * @return paginated city list
     */
    Page<City> findByCountryCodeIgnoreCase(String countryCode, Pageable pageable);

    /**
     * Searches cities by keyword across name, city code,
     * country code, country name, and region code fields.
     *
     * @param keyword search term
     * @param pageable pagination information
     * @return paginated matching cities
     */
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
