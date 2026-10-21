package ro.mycode.app.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ro.mycode.app.category.model.Category;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface CategoryRepository extends JpaRepository<Category, UUID> {
    List<Category> findByName(@Param("name")String name);

    Optional<Category> getCategoryByName(String name);

    boolean existsByName(String name);
}
