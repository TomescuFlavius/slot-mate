package ro.mycode.app.category.model;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "categories")
public class Category {
    @Id
    private Long id;
    @NotBlank(message = "Numele categoriei este obligatoriu")
    @Size(max = 50, message = "Numele nu poate depasi 50 de caractere")
    @Column(name = "name", nullable = false, length = 50, unique = true)
    private String name;
}
