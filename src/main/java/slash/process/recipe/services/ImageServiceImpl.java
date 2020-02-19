package slash.process.recipe.services;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import slash.process.recipe.domain.Recipe;
import slash.process.recipe.repositories.RecipeRepository;

import java.io.IOException;

@Service
public class ImageServiceImpl implements ImageService {

    private final RecipeRepository recipeRepository;

    public ImageServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

    @Override
    @Transactional
    public void saveImageFile(Long id, MultipartFile file) {
        System.out.println("received file");
        try {
            Recipe recipe = recipeRepository.findById(id).get();
            Byte[] byteObject = new Byte[file.getBytes().length];
            int i = 0;
            for (byte b : file.getBytes()) {
                byteObject[i++] = b;
            }
            recipe.setImage(byteObject);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
