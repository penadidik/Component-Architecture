package info.penadidik.architecturecomponent.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import info.penadidik.architecturecomponent.R;
import info.penadidik.architecturecomponent.databinding.FragmentSecondEditBinding;

public class EditSecondFragment extends Fragment {

    FragmentSecondEditBinding mViewDataBinding;
    SecondViewModel mViewModel;
    NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateViewModel();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = FragmentSecondEditBinding.inflate(inflater, container, false);

        //setup view model
        mViewDataBinding.setSecondViewModel(mViewModel);
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

    private SecondViewModel onCreateViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SecondViewModel.class);
        Bundle bundle = requireActivity().getIntent().getExtras();
        int id = bundle.getInt("id", 0);
        Toast.makeText(requireContext(), id, Toast.LENGTH_SHORT).show();
        return mViewModel;
    }

    private void onCreateObserver(SecondViewModel viewModel) {

    }

    private void setContentData(View view){
        mViewDataBinding.buttonSimpan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigateUp();
            }
        });
    }

}
