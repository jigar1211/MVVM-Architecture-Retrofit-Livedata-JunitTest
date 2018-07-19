package wars.star.com.starwars.service.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import wars.star.com.starwars.Utils.Tags;
import wars.star.com.starwars.service.model.StarWarsCharacters;


public class ProjectRepository {

    private static ProjectRepository projectRepository;
    public static StarWarsService starWarsService;



    public ProjectRepository() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();

        logging.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(logging)
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(StarWarsService.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        starWarsService = retrofit.create(StarWarsService.class);
    }

    public synchronized static ProjectRepository getInstance() {

        if (projectRepository == null) {
            projectRepository = new ProjectRepository();
        }

        return projectRepository;
    }


    /**
     * Get API response of star wars character details from this method
     * */
    public LiveData<StarWarsCharacters> getStarWarsCharacterList(int id) {

        final MutableLiveData<StarWarsCharacters> data = new MutableLiveData<>();

        starWarsService.getDetailsOfCharacters(id).enqueue(new Callback<StarWarsCharacters>() {


            @Override
            public void onResponse(Call<StarWarsCharacters> call, Response<StarWarsCharacters> response) {

                data.setValue(response.body());

            }

            @Override
            public void onFailure(Call<StarWarsCharacters> call, Throwable t) {
                data.setValue(null);
            }
        });

        return data;
    }


    /**
     * Get API response of star wars character list from this method
     * */
    public LiveData<List<StarWarsCharacters>> getsStarWarsCharacter(final int pageCount) {

        final MutableLiveData<List<StarWarsCharacters>> data = new MutableLiveData<>();

        starWarsService.getNameListOfCharacters(pageCount).enqueue(new Callback<StarWarsCharacters>() {


            @Override
            public void onResponse(@NonNull Call<StarWarsCharacters> call, @NonNull Response<StarWarsCharacters> response) {
                if(pageCount==1) {
                    Tags.Constants.CHARCHTERCOUNT = response.body().getCount();
                }
                Log.e("response:", "" + response.body().getResults());
                List<? extends StarWarsCharacters> starWarsCharactersList = response.body().getResults();
                data.setValue((List<StarWarsCharacters>) starWarsCharactersList);
            }

            @Override
            public void onFailure(@NonNull Call<StarWarsCharacters> call, @NonNull Throwable t) {
                data.setValue(null);
                t.getMessage();
            }
        });


        return data;
    }


}
