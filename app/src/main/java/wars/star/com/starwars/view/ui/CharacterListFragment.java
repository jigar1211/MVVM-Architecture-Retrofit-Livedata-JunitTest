package wars.star.com.starwars.view.ui;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;

import java.util.List;

import cn.pedant.SweetAlert.SweetAlertDialog;
import wars.star.com.starwars.R;
import wars.star.com.starwars.Utils.ProgressDialog;
import wars.star.com.starwars.Utils.Tags;
import wars.star.com.starwars.Utils.UDF;
import wars.star.com.starwars.databinding.FragmentCharacterListBinding;
import wars.star.com.starwars.service.model.StarWarsCharacters;
import wars.star.com.starwars.view.adapter.CharacterListAdapter;
import wars.star.com.starwars.viewmodel.CharacterListViewModel;
import wars.star.com.starwars.viewmodel.MyViewModelFactory;


public class CharacterListFragment extends Fragment implements CharacterListAdapter.OnItemClickListener, SwipeRefreshLayout.OnRefreshListener {

    int pageCount = 1;
    CharacterListViewModel viewModel;
    private FragmentCharacterListBinding binding;
    private CharacterListAdapter characterListAdapter;
    private int count = 0;
    private boolean isDialogVisible = false;
    private ProgressDialog progressdialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_list, container, false);
        mappingwidget();
        showProgress();
        return binding.getRoot();
    }

    /**
     * Mapping layout widget
     */
    private void mappingwidget() {
        characterListAdapter = new CharacterListAdapter(this, this);
        binding.characterList.setAdapter(characterListAdapter);
        binding.swipeRefresh.setEnabled(false);
        binding.swipeRefresh.setOnRefreshListener(this);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel =
                ViewModelProviders.of(this, new MyViewModelFactory(this.getActivity().getApplication(), pageCount)).get(CharacterListViewModel.class);

        setData(pageCount);
    }

    /**
     * call API using this method
     *
     * @param pageCount pass in API to get character list name
     */
    public void setData(int pageCount) {
        viewModel.refreshData(pageCount);
        observeViewModel(viewModel);
    }


    /**
     * Observe view model class to get response and display it in to list
     */
    private void observeViewModel(final CharacterListViewModel viewModel) {
        viewModel.getCharacterListObservable().observe(this, new Observer<List<StarWarsCharacters>>() {
            @Override
            public void onChanged(@Nullable List<StarWarsCharacters> starWarsCharacters) {
                if (starWarsCharacters != null) {
                    binding.swipeRefresh.setEnabled(false);
                    characterListAdapter.setList(starWarsCharacters);
                    count += starWarsCharacters.size();
                    if (count == Tags.Constants.CHARCHTERCOUNT) {
                        binding.ivLoadMore.setVisibility(View.GONE);

                    } else {
                        binding.ivLoadMore.setVisibility(View.VISIBLE);
                        binding.ivLoadMore.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showProgress();
                                pageCount++;
                                setData(pageCount);
                            }
                        });
                    }

                } else {
                    binding.swipeRefresh.setEnabled(true);
                    if (UDF.isOnline(getActivity())) {
                        UDF.showWarningSweetDialog(getActivity(), new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                sweetAlertDialog.dismiss();
                            }
                        }, new SweetAlertDialog.OnSweetClickListener() {
                            @Override
                            public void onClick(SweetAlertDialog sweetAlertDialog) {
                                setData(pageCount);
                                sweetAlertDialog.dismiss();
                            }
                        });

                    } else {
                        UDF.showErrorSweetDialog(getActivity().getResources().getString(R.string.check_internet_connection), getActivity());
                    }

                }
                dismissProgress();
            }
        });
    }


    /**
     * This method is use to get item click of recycler view item
     */
    @Override
    public void onItemClick(@NonNull View itemView, int position, long id) {
        Bundle bundle = new Bundle();
        bundle.putInt("key", position + 1); // Put anything what you want
        CharacterDetailsListFragment fragment = new CharacterDetailsListFragment();
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction()
                .add(R.id.fragment_container, fragment, Tags.Constants.CHARACTERDETAILSLISTFRAGMENT).addToBackStack(null).commit();
    }

    /**
     * Method to display progress
     */
    public void showProgress() {
        if (!getActivity().isFinishing() && !isDialogVisible) {
            progressdialog = new ProgressDialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
            progressdialog.setContentView(R.layout.loading);
            progressdialog.setCancelable(false);
            progressdialog.show();
            progressdialog.findViewById(R.id.loading_icon)
                    .startAnimation(AnimationUtils.loadAnimation(getActivity(), R.anim.flip_anim));
            isDialogVisible = true;
        }
    }


    /**
     * Method to dismiss progress
     */
    public void dismissProgress() {
        if (!getActivity().isFinishing() && isDialogVisible && progressdialog != null && progressdialog.isShowing()) {
            progressdialog.dismiss();
            isDialogVisible = false;
        }
    }

    /**
     * Swipe to refresh method call
     */
    @Override
    public void onRefresh() {
        setData(pageCount);
    }
}
