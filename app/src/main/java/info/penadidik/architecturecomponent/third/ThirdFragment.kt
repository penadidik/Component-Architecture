package info.penadidik.architecturecomponent.third

import info.penadidik.architecturecomponent.third.ThirdViewModel
import androidx.navigation.NavController
import info.penadidik.architecturecomponent.third.ThirdAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import info.penadidik.architecturecomponent.R
import androidx.lifecycle.ViewModelProviders
import info.penadidik.architecturecomponent.databinding.FragmentThirdBinding
import info.penadidik.architecturecomponent.third.data.State

class ThirdFragment : Fragment() {
    var mViewDataBinding: FragmentThirdBinding? = null
    var mViewModel: ThirdViewModel? = null
    var navController: NavController? = null
    var adapter: ThirdAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onCreateViewModel()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = FragmentThirdBinding.inflate(inflater, container, false)
        mViewDataBinding!!.thirdViewModel = mViewModel
        mViewDataBinding!!.lifecycleOwner = this
        return mViewDataBinding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //setup observer view model and content
        onCreateObserver(mViewModel)
        onSetContentData(view)

        //setup Navigation Controller
        navController = Navigation.findNavController(this.activity!!, R.id.my_nav_host_fragment)
    }

    private fun onCreateViewModel(): ThirdViewModel {
        mViewModel = ViewModelProvider(this).get(ThirdViewModel::class.java)
        return mViewModel as ThirdViewModel
    }

    private fun onCreateObserver(viewModel: ThirdViewModel?) {

        viewModel?.newsList?.observe(viewLifecycleOwner,
            Observer {
                adapter?.submitList(it)
            })

        viewModel?.getState()?.observe(viewLifecycleOwner, Observer { state ->
            mViewDataBinding?.progressBar?.visibility =
                if (viewModel.listIsEmpty() && state == State.LOADING) View.VISIBLE else View.GONE
            mViewDataBinding?.txtError?.visibility = if (viewModel.listIsEmpty() && state == State.ERROR) View.VISIBLE else View.GONE

            if (!viewModel.listIsEmpty()) {
                adapter?.setState(state ?: State.DONE)
            }
        })
    }

    private fun onSetContentData(view: View) {
        adapter = ThirdAdapter{mViewModel?.retry()}
        mViewDataBinding?.recyclerView?.adapter = adapter
        mViewDataBinding?.txtError?.setOnClickListener { mViewModel?.retry() }
        mViewDataBinding?.backButton?.setOnClickListener {
            navController?.navigateUp()
        }
    }
}