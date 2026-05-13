package org.example.fixoraserver.professional;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }
}
