package slash.process.recipe.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import slash.process.recipe.commands.IngredientCommand;
import slash.process.recipe.converters.IngredientCommandToIngredient;
import slash.process.recipe.converters.IngredientToIngredientCommand;
import slash.process.recipe.domain.Ingredient;
import slash.process.recipe.domain.Recipe;
import slash.process.recipe.domain.UnitOfMeasure;
import slash.process.recipe.repositories.RecipeRepository;
import slash.process.recipe.repositories.UnitOfMeasureRepository;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService {
    IngredientToIngredientCommand ingredientToIngredientCommand;
    RecipeRepository recipeRepository;
    IngredientCommandToIngredient ingredientCommandToIngredient;
    UnitOfMeasureRepository unitOfMeasureRepository;


    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }

    @Override
    public IngredientCommand findByRecipeAndIngredientId(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        Recipe recipe = recipeOpt.get();
        Optional<IngredientCommand> ingredientComOpt = recipe.getIngredients().stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .map(ingredient -> ingredientToIngredientCommand.convert(ingredient)).findFirst();

        return ingredientComOpt.get();
    }

    @Transactional
    @Override
    public IngredientCommand saveIngredientCommand(IngredientCommand command) {

        Optional<Recipe> recipeOptional = recipeRepository.findById(command.getRecipeId());
        if (!recipeOptional.isPresent()) {
            return new IngredientCommand();
        } else {
            Recipe recipe = recipeOptional.get();
            Optional<Ingredient> ingredientOptional = recipe
                    .getIngredients()
                    .stream()
                    .filter(ingredient -> ingredient.getId().equals(command.getId()))
                    .findFirst();
            if (ingredientOptional.isPresent()) {
                Ingredient ingredientFound = ingredientOptional.get();
                ingredientFound.setDescription((command.getDescription()));
                ingredientFound.setAmount(command.getAmount());
                Optional<UnitOfMeasure> unitOfMeasures = unitOfMeasureRepository.findById(command.getUom().getId());
                UnitOfMeasure unitOfMeasure = unitOfMeasures.get();
                ingredientFound.setUom(unitOfMeasure);
            } else {
                recipe.addIngredient(ingredientCommandToIngredient.convert(command));

            }
            Recipe saveRecipe = recipeRepository.save(recipe);
            return ingredientToIngredientCommand.convert(saveRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst()
                    .get());
        }
    }

    @Override
    public void deleteIngredient(Long recipeId, Long IngredientId) {

    }
}
