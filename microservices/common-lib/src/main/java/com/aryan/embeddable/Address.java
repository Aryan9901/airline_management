package com.aryan.embeddable;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Embeddable address component used across entities.
 *
 * This class represents reusable address-related fields
 * that can be embedded into multiple entities without
 * creating a separate database table.
 *
 * Common Usage:
 * - User address
 * - Vendor address
 * - Warehouse address
 * - Delivery address
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Address {

    /**
     * Street name and building information.
     */
    private String street;

    /**
     * Postal or ZIP code of the address.
     */
    private String postalCode;
}
