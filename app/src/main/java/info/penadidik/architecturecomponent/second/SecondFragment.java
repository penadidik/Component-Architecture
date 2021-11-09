package info.penadidik.architecturecomponent.second;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.room.Room;

import info.penadidik.architecturecomponent.R;
import info.penadidik.architecturecomponent.databinding.FragmentSecondBinding;
import info.penadidik.architecturecomponent.second.data.MyRoomDb;

//Present about PageList and Room
public class SecondFragment extends Fragment implements SecondAdapter.ClickListener, AdapterView.OnItemSelectedListener {

    FragmentSecondBinding mViewDataBinding;
    SecondViewModel mViewModel;
    NavController navController;
    Bundle dataShared;
    SecondAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateViewModel();
        mViewModel.myRoomDb = Room.databaseBuilder(requireContext(), MyRoomDb.class, MyRoomDb.DB_NAME).build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mViewDataBinding = FragmentSecondBinding.inflate(inflater, container, false);

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
        mViewModel.onStart(requireContext());
        setContentData(view);

        //setup Navigation Controller
        navController = Navigation.findNavController(this.getActivity(), R.id.my_nav_host_fragment);
    }

    private SecondViewModel onCreateViewModel() {
        mViewModel = ViewModelProviders.of(this).get(SecondViewModel.class);
        return mViewModel;
    }

    private void onCreateObserver(SecondViewModel viewModel) {
        viewModel.eventUpdateRecycle.observe(getViewLifecycleOwner(), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                adapter.notifyDataSetChanged();
            }
        });

    }

    private void setContentData(View view){

        mViewDataBinding.backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                navController.navigateUp();
            }
        });

        adapter = new SecondAdapter(this, mViewModel.todoArrayList);
        mViewDataBinding.recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        mViewDataBinding.recyclerView.setAdapter(adapter);


    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        String string = adapterView.getItemAtPosition(i).toString();
        mViewModel.loadFilteredTodos(string);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @Override
    public void launchIntent(int id) {
        dataShared = new Bundle();
        dataShared.putInt("REQUEST_CODE", SecondViewModel.UPDATE_TODO_REQUEST_CODE);
        dataShared.putInt("id", id);
        navController.navigate(R.id.action_secondFragment_to_editSecondFragment, dataShared);
    }
}
