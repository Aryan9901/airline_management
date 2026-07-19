package com.aryan.model;

import com.aryan.embeddable.Support;
import com.aryan.enums.AirlineStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Instant;

/**
 * Entity representing an airline.
 *
 * Stores airline identification, ownership,
 * operational status, support information,
 * and audit metadata.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EntityListeners(AuditingEntityListener.class)
public class Airline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String iataCode;

    @Column(unique = true, nullable = false)
    private String icaoCode;

    @Column(nullable = false)
    private String name;

    /**
     * Identifier of the user who owns
     * this airline.
     */
    @Column(nullable = false, unique = true)
    private  Long ownerId;

    private String alias;

    private String logoUrl;

    private String website;

    /**
     * Current operational status of the airline.
     */
    @Enumerated(EnumType.STRING)
    private AirlineStatus status = AirlineStatus.ACTIVE;

    private String alliance;

    private Long headquartersCityId;

    /**
     * Customer support contact information.
     */
    @Embedded
    private Support support;

    /**
     * Identifier of the user who last
     * updated this airline.
     */
    private Long updatedById;

    @CreatedDate
    @Column(updatable = false ,nullable = false)
    private Instant createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private Instant updatedAt;
}
