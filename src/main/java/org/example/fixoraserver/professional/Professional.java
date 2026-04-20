package org.example.fixoraserver.professional;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "professionals")
@AllArgsConstructor
@NoArgsConstructor
public class Professional {
    @Id
    @Column(nullable = false)
    private Long id;

    @Column(nullable = false)
    private String workerName;

    @Column(nullable = false)
    private String workerEmail;

    @Column(nullable = false)
    private String workerPhoneNumber;

    @Column(nullable = false)
    private String specialization;
}
