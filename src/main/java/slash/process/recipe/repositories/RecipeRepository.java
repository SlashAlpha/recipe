package slash.process.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import slash.process.recipe.domain.Recipe;

public interface RecipeRepository extends CrudRepository<Recipe, Long> {
}
