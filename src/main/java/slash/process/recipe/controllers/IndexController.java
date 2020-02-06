package slash.process.recipe.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import slash.process.recipe.domain.Category;
import slash.process.recipe.domain.UnitOfMeasure;
import slash.process.recipe.repositories.CategoryRepository;
import slash.process.recipe.repositories.UnitOfMeasureRepository;
import slash.process.recipe.services.RecipeService;

import java.util.Optional;

@Controller
public class IndexController {

    private final RecipeService recipeService;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public IndexController(CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository, RecipeService recipeService) {
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
        this.recipeService = recipeService;
    }

    @RequestMapping({"", "/", "index"})
    public String getIndexPage(Model model) {

        Optional<Category> categoryOptional = categoryRepository.findByDescription("French");
        Optional<UnitOfMeasure> unitOfMeasureOptional = unitOfMeasureRepository.findByDescription("Ounce");

        System.out.println("Category id is : " + categoryOptional.get().getId());
        System.out.println("unit of measure id is : " + unitOfMeasureOptional.get().getId());
        model.addAttribute("recipes", recipeService.getRecipes());
        System.out.println("Some message to say...");
        return "index";
    }
}
