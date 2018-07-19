package wars.star.com.starwars.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import wars.star.com.starwars.service.model.StarWarsCharacters;
import wars.star.com.starwars.service.repository.ProjectRepository;

/**
 * Created by T183 on 10-Jul-18.
 */

public class CharacterListDetailViewModel extends AndroidViewModel {


    private LiveData<StarWarsCharacters> starWarscharaterListObservable;

    public CharacterListDetailViewModel(@NonNull Application application) {
        super(application);

       // starWarscharaterListObservable = ProjectRepository.getInstance().getStarWarsCharacterList(characterName);
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<StarWarsCharacters> getCharacterListObservable() {
        return starWarscharaterListObservable;
    }

    /**
     * Call star wars character detail API from the @class ProjectRepository
     */
    public void loadDetailData(int id){
        starWarscharaterListObservable = ProjectRepository.getInstance().getStarWarsCharacterList(id);
    }
}
