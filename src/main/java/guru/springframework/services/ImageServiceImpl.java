package guru.springframework.services;

import guru.springframework.domain.Recipe;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.reactive.RecipeReactiveRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * Created by jt on 7/3/17.
 */
@Slf4j
@Service
public class ImageServiceImpl implements ImageService {


    private final RecipeReactiveRepository recipeRepository;

    public ImageServiceImpl( RecipeReactiveRepository recipeService) {

        this.recipeRepository = recipeService;
    }

    @Override
    public Mono<Void> saveImageFile(String recipeId, MultipartFile file) {

        Mono<Recipe> recipeMono = recipeRepository
                .findById(recipeId)
                .map(recipe -> {
                    Byte[] byteObject = new Byte[0];
                    try{
                        byteObject = new Byte[file.getBytes().length];
                        int i = 0;
                        for (byte b: file.getBytes()){
                            byteObject[i++] = b;
                        }
                        recipe.setImage(byteObject);
                        return recipe;
                    }
                    catch (IOException e){
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                });
        recipeRepository.save(recipeMono.block()).block();
               // .publish(recipeMono -> recipeRepository.save(recipeMono.block()));
//        try {
//            Recipe recipe = recipeRepository.findById(recipeId).block();
//
//            Byte[] byteObjects = new Byte[file.getBytes().length];
//
//            int i = 0;
//
//            for (byte b : file.getBytes()){
//                byteObjects[i++] = b;
//            }
//
//            recipe.setImage(byteObjects);
//
//            recipeRepository.save(recipe);
//        } catch (IOException e) {
//            //todo handle better
//            log.error("Error occurred", e);
//
//            e.printStackTrace();
//        }
        return Mono.empty();
    }
}
