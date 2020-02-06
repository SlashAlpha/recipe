package slash.process.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import slash.process.recipe.domain.Category;

import java.util.Optional;

public interface CategoryRepository extends CrudRepository<Category, Long> {

    Optional<Category> findByDescription(String description);
}
