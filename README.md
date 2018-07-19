# MMVMArchitecture-Retrofit-Livedata-JunitTest
MMVMArchitecture-Retrofit-Livedata-JunitTest

Sample demo to implplement MVVM Architecture with Retrofit and Live data using star wars API
Also inculde simple Test cases to check API Response. 

Use Starwars API to dispaly list of starwars character name and their details.

For Test cases use followinf code : 


Add testCompile 'junit:junit:4.12' in app level build.gradle file


Java Test Code:

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


