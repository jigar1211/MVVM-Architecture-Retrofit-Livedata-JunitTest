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

import cn.pedant.SweetAlert.SweetAlertDialog;
import wars.star.com.starwars.R;
import wars.star.com.starwars.Utils.ProgressDialog;
import wars.star.com.starwars.Utils.UDF;
import wars.star.com.starwars.databinding.FragmentCharacterDetailListBinding;
import wars.star.com.starwars.service.model.StarWarsCharacters;
import wars.star.com.starwars.viewmodel.CharacterListDetailViewModel;

/**
 * Created by T183 on 10-Jul-18.
 */

public class CharacterDetailsListFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    int id = 0;
    CharacterListDetailViewModel viewModel;
    private FragmentCharacterDetailListBinding binding;
    private boolean isDialogVisible = false;
    private ProgressDialog progressdialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_detail_list, container, false);
        Bundle bundle = this.getArguments();

        if (bundle != null) {
            id = bundle.getInt("key");
        }
        showProgress();
        binding.swipeRefresh.setEnabled(false);
        binding.swipeRefresh.setOnRefreshListener(this);
        return binding.getRoot();
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel =
                ViewModelProviders.of(this).get(CharacterListDetailViewModel.class);
        viewModel.loadDetailData(id);
        observeViewModel(viewModel);
    }


    /**
     * Observe view model class to get response and display it in to android widget
     */
    private void observeViewModel(final CharacterListDetailViewModel viewModel) {

        viewModel.getCharacterListObservable().observe(this, new Observer<StarWarsCharacters>() {

            @Override
            public void onChanged(@Nullable StarWarsCharacters starWarsCharacters) {
                if(starWarsCharacters!=null) {
                    mappingData(starWarsCharacters);
                }
                else{
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
                                observeViewModel(viewModel);
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
     * Mapping data as per response
     * */
    private void mappingData(StarWarsCharacters starWarsCharacters) {
        binding.tvName.setText(starWarsCharacters.getName());
        binding.tvHeight.setText(Double.parseDouble(starWarsCharacters.getHeight())/100 + " meter");
        binding.tvWeight.setText(starWarsCharacters.getMass() + " kg");
        binding.tvCreateDate.setText(starWarsCharacters.getCreated());
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
        viewModel.loadDetailData(id);
        observeViewModel(viewModel);
    }
}
