package slash.process.recipe.services;

import slash.process.recipe.commands.IngredientCommand;

public interface IngredientService {

    IngredientCommand findByRecipeAndIngredientId(Long recipeId, Long ingredientId);

    IngredientCommand saveIngredientCommand(IngredientCommand command);

    void deleteIngredient(Long recipeId, Long Ingredientid);

}
