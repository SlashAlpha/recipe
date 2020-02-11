package slash.process.recipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import slash.process.recipe.commands.RecipeCommand;
import slash.process.recipe.domain.Recipe;
import slash.process.recipe.services.RecipeService;

@Slf4j
@Controller
public class RecipeController {
    private final RecipeService recipeService;

    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping
    @RequestMapping("/recipe/{id}/show")
    public String showById(@PathVariable String id, Model model) {

        Recipe recipe = new Recipe();
        recipe = recipeService.findById(new Long(id));

        model.addAttribute("recipe", recipeService.findById(new Long(id)));
        recipe.getIngredients().forEach(ingredient -> {
            System.out.println(ingredient.getDescription());
        });
        return "recipe/show";
    }

    @GetMapping
    @RequestMapping("/recipe/new")
    public String newRecipe(Model model) {
        model.addAttribute("recipe", new RecipeCommand());

        return "/recipe/recipeform";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/update")
    public String updateRecipe(@PathVariable String id, Model model) {
        model.addAttribute("recipe", recipeService.findCommandById(new Long(id)));
        return "recipe/recipeform";
    }

    @PostMapping
    @RequestMapping("recipe")
    public String saveOrUpdate(@ModelAttribute RecipeCommand command) {
        RecipeCommand recipeCommand = recipeService.saveRecipeCommand(command);
        return "redirect:/recipe/" + recipeCommand.getId() + "/show/";
    }

    @GetMapping
    @RequestMapping("recipe/{id}/delete")
    public String deleteById(@PathVariable String id) {
        log.debug("deleting by id :" + id);
        recipeService.deleteById(new Long(id));
        return "redirect:/";
    }
}
