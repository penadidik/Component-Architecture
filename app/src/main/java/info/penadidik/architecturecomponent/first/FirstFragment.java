package info.penadidik.architecturecomponent.first;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import info.penadidik.architecturecomponent.R;
import info.penadidik.architecturecomponent.databinding.FragmentFirstBinding;

//present about Navigation Controller, View Model and Live Data
public class FirstFragment extends Fragment{

    FragmentFirstBinding mViewDataBinding;
    FirstViewModel mViewModel;
    NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateViewModel();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = FragmentFirstBinding.inflate(inflater, container, false);

        //setup view model
        mViewDataBinding.setFirstViewModel(mViewModel);
        mViewDataBinding.setLifecycleOwner(this);

        return mViewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //setup observer view model and content
        onCreateObserver(mViewModel);
        setContentData(view);

        //setup Navigation Controller
        navController = Navigation.findNavController(this.getActivity(), R.id.my_nav_host_fragment);

    }

    private FirstViewModel onCreateViewModel() {
        mViewModel = ViewModelProviders.of(this).get(FirstViewModel.class);
        return mViewModel;
    }

    private void onCreateObserver(FirstViewModel viewModel) {
        viewModel.getUser().observe(getViewLifecycleOwner(), new Observer<FirstModel>() {
            @Override
            public void onChanged(FirstModel firstModel) {
                if(firstModel.getEmail().length() > 0 || firstModel.getPassword().length() > 0){
                    Toast.makeText(requireContext(), "email : " + firstModel.getEmail() + " password " + firstModel.getPassword(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void setContentData(View view) {
        mViewDataBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigateUp();
            }
        });
    }
}
