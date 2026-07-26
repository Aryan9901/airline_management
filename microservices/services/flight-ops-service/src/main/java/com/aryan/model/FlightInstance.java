package com.aryan.model;

import com.aryan.enums.FlightStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class FlightInstance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long airlineId;

    @ManyToOne
    private Flight flight;

    @Column(nullable = false)
    private Long departureAirportId;

    private Long arrivalAirportId;

    @Column(nullable = false)
    private Long scheduleId;

    private LocalDateTime departureDateTime;

    private LocalDateTime arrivalDateTime;

    @Column(nullable = false)
    private Integer totalSeats;

    @Column(nullable = false)
    private Integer availableSeats;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FlightStatus status;

    private Integer minAdvanceBookingDays;
    private Integer maxAdvanceBookingDays;

    private boolean isActive = true;

    @Transient
    public String getFormatedDuration(){
        if(departureDateTime == null || arrivalDateTime == null) return null;

        Duration duration = Duration.between(departureDateTime, arrivalDateTime);

        long hours = duration.toHours();
        long minutes = duration.toMinutes() % 60;

        StringBuilder sb = new StringBuilder();
        if(hours > 0) sb.append(hours + "h ");
        if(minutes > 0) sb.append(minutes).append("min");
        return sb.toString().trim();

//        also we can use
//        return String.format("%dh %dmin",
//                duration.toHoursPart(),
//                duration.toMinutesPart());
    }

}
