package ro.mycode.app.slot.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import ro.mycode.app.offering.model.Offering;

import java.time.LocalDateTime;
@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "slots")
public class Slot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Relația cu serviciul (Offering)
    @NotNull(message = "Serviciul (offering) este obligatoriu")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "offering_id", nullable = false)
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Offering offering;

    @NotNull(message = "Ora de început este obligatorie")
    @Column(name = "start_time", nullable = false)
    private LocalDateTime startTime;

    @NotNull(message = "Ora de sfârșit este obligatorie")
    @Column(name = "end_time", nullable = false)
    private LocalDateTime endTime;
}
