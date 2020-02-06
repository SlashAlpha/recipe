package slash.process.recipe.repositories;

import org.springframework.data.repository.CrudRepository;
import slash.process.recipe.domain.UnitOfMeasure;

public interface UnitOfMeasureRepository extends CrudRepository<UnitOfMeasure, Long> {
}
