package slash.process.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import slash.process.recipe.domain.Category;

public interface CategoryRepository extends CrudRepository<Category, Long> {
}
