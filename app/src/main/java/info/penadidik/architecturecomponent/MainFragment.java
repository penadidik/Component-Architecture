package info.penadidik.architecturecomponent;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import info.penadidik.architecturecomponent.databinding.FragmentFirstBinding;
import info.penadidik.architecturecomponent.databinding.FragmentMainBinding;

public class MainFragment extends Fragment {

    FragmentMainBinding mViewDataBinding;
    NavController navController;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = FragmentMainBinding.inflate(inflater, container, false);
        return mViewDataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //setup Navigation Controller
        navController = Navigation.findNavController(this.getActivity(), R.id.my_nav_host_fragment);

        mViewDataBinding.firstButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_to_firstFragment);
            }
        });

        mViewDataBinding.secondButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_to_secondFragment);
            }
        });

        mViewDataBinding.thirdButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigate(R.id.action_to_thirdFragment);
            }
        });

    }


}
