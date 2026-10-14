package ro.mycode.app.user.model;
import jakarta.persistence.*;
import lombok.*;
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank(message= "Prenume obligatoriu")
    private String firstName;
    @NotBlank(message= "Nume obligatoriu")
    private String lastName;
    @NotBlank(message="Email obligatoriu")
    private String email;
    @NotBlank(message="Parola obligatorie")
    private String password;
    private enum Role{
        CLIENT,
        EMPLOYEE,
        ADMIN
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

}
