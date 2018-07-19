package wars.star.com.starwars.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import wars.star.com.starwars.R;
import wars.star.com.starwars.databinding.RowCharacterListBinding;
import wars.star.com.starwars.service.model.StarWarsCharacters;
import wars.star.com.starwars.view.ui.CharacterListFragment;

/**
 * Created by T183 on 10-Jul-18.
 */

public class CharacterListAdapter extends RecyclerView.Adapter<CharacterListAdapter.CharacterViewHolder> {

    List<StarWarsCharacters> starWarsCharactersList;
    private CharacterListFragment context;
    private OnItemClickListener onItemClickListener;



    public CharacterListAdapter(CharacterListFragment context, @Nullable OnItemClickListener onItemClickListener) {
        this.context = context;
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * Set list to display data
     * */
    public void setList(final List<StarWarsCharacters> starWarsCharactersList) {

        if (this.starWarsCharactersList == null) {
            this.starWarsCharactersList = starWarsCharactersList;
        } else {
            for (int i = 0; i < starWarsCharactersList.size(); i++) {
                this.starWarsCharactersList.add(starWarsCharactersList.get(i));
            }

        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RowCharacterListBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.row_character_list, parent, false);
        //  binding.setCallback(starWarsCharacterCallback);
        return new CharacterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CharacterListAdapter.CharacterViewHolder holder, final int position) {
        holder.bindView(position);
    }

    @Override
    public int getItemCount() {
        return starWarsCharactersList == null ? 0 : starWarsCharactersList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(@NonNull View itemView, int position, long id);
    }

    class CharacterViewHolder extends RecyclerView.ViewHolder {

        final RowCharacterListBinding binding;
        int position;


        public CharacterViewHolder(RowCharacterListBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.tvCharacterName.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        onItemClickListener.onItemClick(v,position, position);
                    }
                }
            });
        }

        void bindView(int position) {
            this.position = position;
            binding.tvCharacterName.setText(starWarsCharactersList.get(position).getName());
            binding.executePendingBindings();
        }
    }
}
