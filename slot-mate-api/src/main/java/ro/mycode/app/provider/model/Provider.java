package ro.mycode.app.provider.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import ro.mycode.app.category.model.Category;
import ro.mycode.app.user.model.User;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "providers")
public class Provider {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message = "Utilizatorul este obligatoriu")
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    @ToString.Exclude
    private User user;

    @NotBlank(message = "Categoria este obligatorie")
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "description",columnDefinition = "TEXT")
    private String description;

    @NotBlank(message = "Numele afacerii este obligatoriu")
    @Size(max = 150, message = "Numele afacerii nu poate depasii 150 de caractere")
    @Column(name = "business_name", length = 150, nullable = false)
    private String businessName;

    @NotBlank(message = "Orasul este obligatoriu")
    @Size(max = 100)
    @Column(name = "city", length = 100, nullable = false)
    private String city;

    @NotBlank(message = "Adresa este obligatorie")
    @Column(name = "adress", nullable = false)
    private String address;
}
