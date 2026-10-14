package ro.mycode.app.user.model;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

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

    @NotBlank(message= "Nume obligatoriu")
    @Column(name = "full_name", length = 100)
    private String fullName;

    @NotBlank(message="Email obligatoriu")
    @Column(unique = true, length = 100)
    @Email(message="Email invalid")
    private String email;

    @NotBlank(message="Parola obligatorie")
    private String passwordHash;

    @Size(min=10,max=10, message="Numar invalid")
    private String phone;

    private LocalDateTime createdAt=LocalDateTime.now();

    public enum Role{
        CLIENT,
        PROVIDER,
        ADMIN
    }
    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private Role role;

    @Override
    public boolean equals(Object o){
        if (this == o) return true;
        if (!(o instanceof User other)) return false;
        return id!=null && id.equals(other.id);
    }
}
