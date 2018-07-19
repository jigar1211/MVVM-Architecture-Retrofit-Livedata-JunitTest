package wars.star.com.starwars.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import wars.star.com.starwars.service.model.StarWarsCharacters;
import wars.star.com.starwars.service.repository.ProjectRepository;

/**
 * Created by T183 on 10-Jul-18.
 */

public class CharacterListViewModel extends AndroidViewModel {

    private LiveData<List<StarWarsCharacters>> starWarsCharacterListObservable;


    public CharacterListViewModel(@NonNull Application application, int pageCount) {
        super(application);
    }

    /**
     * Expose the LiveData Projects query so the UI can observe it.
     */
    public LiveData<List<StarWarsCharacters>> getCharacterListObservable() {
        return starWarsCharacterListObservable;
    }

    /**
    * Call API from the P
     */
    public  void refreshData(int pageCount){
        starWarsCharacterListObservable = ProjectRepository.getInstance().getsStarWarsCharacter(pageCount);
    }
}
