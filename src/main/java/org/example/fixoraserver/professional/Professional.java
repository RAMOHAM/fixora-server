package org.example.fixoraserver.professional;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.fixoraserver.booking.Booking;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Table(name = "professionals")
@AllArgsConstructor
@NoArgsConstructor
public class Professional {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String workerName;

    @Column(nullable = false)
    private String workerEmail;

    @Column(nullable = false)
    private String phoneNumber;

    @Column(nullable = false)
    private String category;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "professional")
    private List<Booking> bookings;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
