package ro.mycode.app.offering.model;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import ro.mycode.app.provider.model.Provider;
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "offerings")
public class Offering {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Furnizorul este obligatoriu")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "provider_id")
    @ToString.Exclude
    private Provider provider;
    @NotBlank(message = "Numele serviciului este obligatoriu")
    @Size(max = 100, message = "Numele serviciului nu poate depasi 100 de caractere")
    @Column(length = 100, nullable = false, name = "name")
    private String name;
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;
    @Min(value=1, message = "Durata minima este de 1 minut")
    @Column(name = "duration_minutes", nullable = false)
    private Integer durationMinutes;
    @NotNull(message = "Pretul este obligatoriu")
    @PositiveOrZero(message = "Pretul nu poate fi negativ")
    @Column(name = "price", nullable = false)
    private Double price;
    @NotNull
    @Column(name = "active", nullable = false)
    private Boolean active;
}
