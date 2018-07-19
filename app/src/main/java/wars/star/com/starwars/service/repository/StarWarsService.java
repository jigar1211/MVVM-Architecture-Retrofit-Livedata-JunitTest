package wars.star.com.starwars.service.repository;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import wars.star.com.starwars.service.model.StarWarsCharacters;

/**
 * Service call methods
 */

public interface StarWarsService {

    String BASE_URL = "https://swapi.co/api/people/";

    @GET("./")
    Call<StarWarsCharacters> getNameListOfCharacters(@Query("page") int pageCount);

    @GET("{id}/")
    Call<StarWarsCharacters> getDetailsOfCharacters(@Path("id") int id);
}
