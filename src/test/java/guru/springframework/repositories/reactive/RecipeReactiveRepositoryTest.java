package guru.springframework.repositories.reactive;

import guru.springframework.domain.Recipe;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@DataMongoTest
public class RecipeReactiveRepositoryTest {
    @Autowired
    RecipeReactiveRepository recipeReactiveRepository;

    @Before
    public void setUp() throws Exception {
        recipeReactiveRepository.deleteAll().block();
    }

    @Test
    public void saveRecipes(){
        Recipe recipe1 = new Recipe();
        recipe1.setDescription("UNIT TEST");
        Recipe recipe2 = new Recipe();
        recipe2.setDescription("UNIT TEST2");
        recipeReactiveRepository.save(recipe1).block();
        recipeReactiveRepository.save(recipe2).block();

        Long count = recipeReactiveRepository.count().block();

        assertEquals(Long.valueOf(2L), count);
    }

}