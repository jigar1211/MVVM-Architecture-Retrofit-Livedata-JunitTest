package wars.star.com.starwars;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.IOException;

import retrofit2.Response;
import wars.star.com.starwars.service.model.StarWarsCharacters;
import wars.star.com.starwars.service.repository.ProjectRepository;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)

public class StarWarsCharacterAPITest {

    ProjectRepository projectRepository= new ProjectRepository();
    Response<StarWarsCharacters> response;

    @Test
    public void testCharacterNameAPI() throws InterruptedException {

        try {
            response = projectRepository.starWarsService.getNameListOfCharacters(1).execute();
            assertNotNull("Character name api :-> response can not be null",response);
            assertNotNull("Character name api :-> result array can not be null",response.body().getResults());
        } catch (IOException e) {
            fail("Fail to call API"+e.getMessage());
        }
    }


    @Test
    public void testCharacterDetailAPI() throws InterruptedException {

        try {
            response  = projectRepository.starWarsService.getDetailsOfCharacters(1).execute();
            assertNotNull("Character Detail API :-> response can not be null",response);
            assertNotNull("Character Detail API :-> response body can not be null",response.body());
        } catch (IOException e) {
            fail("Fail to call API"+e.getMessage());
        }
    }
}
