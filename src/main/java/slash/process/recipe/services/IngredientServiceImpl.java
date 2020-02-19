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
    RecipeService recipeService;


    public IngredientServiceImpl(IngredientToIngredientCommand ingredientToIngredientCommand, RecipeRepository recipeRepository, IngredientCommandToIngredient ingredientCommandToIngredient, UnitOfMeasureRepository unitOfMeasureRepository, RecipeService recipeService) {
        this.ingredientToIngredientCommand = ingredientToIngredientCommand;
        this.recipeRepository = recipeRepository;
        this.ingredientCommandToIngredient = ingredientCommandToIngredient;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
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
                Ingredient ingredient = ingredientCommandToIngredient.convert(command);
                ingredient.setRecipe(recipe);
                recipe.addIngredient(ingredient);

            }
            Recipe saveRecipe = recipeRepository.save(recipe);
            Optional<Ingredient> savedIngredientOptional = saveRecipe.getIngredients().stream()
                    .filter(recipeIngredients -> recipeIngredients.getId().equals(command.getId()))
                    .findFirst();

            //check by description
            if (!savedIngredientOptional.isPresent()) {
                //not totally safe... But best guess
                savedIngredientOptional = saveRecipe.getIngredients().stream()
                        .filter(recipeIngredients -> recipeIngredients.getDescription().equals(command.getDescription()))
                        .filter(recipeIngredients -> recipeIngredients.getAmount().equals(command.getAmount()))
                        .filter(recipeIngredients -> recipeIngredients.getUom().getId().equals(command.getUom().getId()))
                        .findFirst();
            }

            //to do check for fail
            return ingredientToIngredientCommand.convert(savedIngredientOptional.get());
        }
    }


    @Override
    public void deleteIngredient(Long recipeId, Long ingredientId) {
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        Recipe recipe = recipeOpt.get();

//        recipeOpt2.getIngredients().forEach(ingredient -> recipeOpt2.getIngredients().remove(ingredient.getId().equals(ingredientId)));
        Optional<Ingredient> ingredientOptional = recipe
                .getIngredients()
                .stream()
                .filter(ingredient -> ingredient.getId().equals(ingredientId))
                .findFirst();
        ingredientOptional.get().setRecipe(null);
        recipe.getIngredients().remove(ingredientOptional.get());
        recipeRepository.save(recipe);




    }
}
