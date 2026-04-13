package org.example.fixoraserver.booking;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "bookings")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String jobDescription;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false)
    private String phone;

    @Column(nullable = false)
    private String dateOfJob;

    @Column(nullable = false)
    private String preferredWindow;

    @Column(nullable = false)
    private String category;

    @Column(updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    public String getCategory() {
        return category;
    }

    public String getAddress() {
        return address;
    }

    public String getDateOfJob() {
        return dateOfJob;
    }

    public String getEmail() {
        return email;
    }

    public String getPreferredWindow() {
        return preferredWindow;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public String getPhone() {
        return phone;
    }
}
