package lildwagz.com.phonefinder.ui.home

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import lildwagz.com.phonefinder.PhoneApp
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.data.remote.response.Phone
import lildwagz.com.phonefinder.databinding.FragmentHomeBinding
import lildwagz.com.phonefinder.utils.KeyState
import lildwagz.com.phonefinder.vo.ListState
import javax.inject.Inject

class HomeFragment : Fragment() {


    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: HomeViewModel by viewModels { vmFactory }
    private val binding : FragmentHomeBinding by viewBinding()
    private lateinit var adapter : PhoneAdapter
    private var mediaType: String = KeyState.TYPE_LATEST
    private var isSearch: Boolean = false



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViews()
        setupAction()
    }

    private fun setupAction() {
        viewModel.getMediaList(mediaType)
        observeListState()
    }

    private fun setupViews(){
        setupTabs()
        setupSearch()

    }

    private fun setupSearch() = with(binding){
        lbSearch.setOnClickListener {
            if (searchView.isVisible) {
                searchView.visibility = View.GONE
                isSearch = false
            } else {
                isSearch = true
                lbSearch.backgroundTintList
                searchView.visibility = View.VISIBLE
            }
            resolveSearchIbBg()
        }
        with(searchView){
            setQuery("", false)
            setOnClickListener { isIconified = false }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        viewModel.searchForPhone(query)
                        recyclerview.scrollToPosition(0)
                        isSearch = true
                        Log.d("datalses","sukses")
                    } else {
                        isSearch = false
                        searchView.visibility = View.GONE
                    }
                    resolveSearchIbBg()
                    return false                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
            setOnCloseListener {
                if (this.query.isNullOrEmpty()) {
                    visibility = View.GONE
                    isSearch = false
                    viewModel.getMediaList(mediaType)
                    recyclerview.scrollToPosition(0)
                } else {
                    setQuery("", false)                 }
                resolveSearchIbBg()
                return@setOnCloseListener true
            }
        }
    }

    private fun resolveSearchIbBg() = with(binding){
        lbSearch.apply {
            if (isSearch) {
                setColorFilter(requireContext().getColor(R.color.colorAccent))
            } else {
                setColorFilter(requireContext().getColor(R.color.barIconTint))
            }
        }
    }

    private fun setupRv(payload: List<Phone>) = with(binding){
        adapter = PhoneAdapter(payload as ArrayList<Phone>)

        adapter.itemclicklistener = {
            val args = bundleOf(getString(R.string.key_phone) to it.slug)
            findNavController().navigate(R.id.action_nav_home_to_nav_detail,args)

        }
        recyclerview.adapter = adapter
        recyclerview.itemAnimator = null
        recyclerview.layoutManager = GridLayoutManager(context, 2)
        recyclerview.scrollToPosition(0)
    }

    private fun setupTabs() = with(binding) {
        when (viewModel.mediaType) {
            KeyState.TYPE_LATEST -> {
                mediaType = KeyState.TYPE_LATEST
                tablayout.getTabAt(0)?.select()
            }
            else -> {
                mediaType = KeyState.TYPE_TOP_BY_FANS
                tablayout.getTabAt(1)?.select()
            }
        }

        tablayout.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    recyclerview.scrollToPosition(0)
                    mediaType = when (tab?.position){
                        1 -> KeyState.TYPE_LATEST
                        else -> KeyState.TYPE_TOP_BY_FANS
                    }
                    if (isSearch) {
//                        viewModel.searchForPhone(searchView.query.toString())
                    } else {
                        viewModel.getMediaList(mediaType)
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {
                    recyclerview.scrollToPosition(0)
                }

                override fun onTabReselected(tab: TabLayout.Tab?) {
                    recyclerview.scrollToPosition(0)
                }

            }
        )
    }


    private fun observeListState() {
        viewModel.listState.observe(
            viewLifecycleOwner
        ){
            when (it) {
                is ListState.Loading -> {
                    Log.d("datalses","loading")
                    onLoading()
                }
                is ListState.Empty -> {
                    onEmpty()
                }
                is ListState.Success -> {
                    Log.d("datalses","sukses")
                    onLoaded(it.payload)
                }
                is ListState.Error -> {
                    Log.d("datalses","error")
                    onError(it.error)
                }
            }
        }
    }

    private fun onEmpty() = with(binding) {
        hideViews(listOf(lvLoading, recyclerview))
        infoMv.apply {
            setIcon(R.drawable.ic_baseline_sad)
            setText(R.string.empty_result_message)
            visibility = View.VISIBLE
        }

        
    }

    private fun onError(error: Throwable) = with(binding){
        hideViews(listOf(lvLoading, recyclerview))
        infoMv.apply{
            setIcon(R.drawable.ic_baseline_sad)
            setText(error.localizedMessage ?: error::class.java.name)
            visibility = View.VISIBLE
        }
        tvInfoAction.apply{
            text = requireContext().getString(R.string.media_list_reload)
            setOnClickListener(null)
            setOnClickListener {
                viewModel.getMediaList(mediaType)
            }
            visibility = View.VISIBLE
        }


    }

    private fun onLoaded(payload: List<Phone>) = with(binding) {
        hideViews(listOf(lvLoading, infoMv, tvInfoAction))
        recyclerview.visibility = View.VISIBLE
        setupRv(payload)
    }

    private fun onLoading() = with(binding){
        hideViews(listOf(recyclerview, infoMv, tvInfoAction))
        lvLoading.visibility = View.VISIBLE
    }

    private fun hideViews(views: List<View>) {
        views.forEach {
            it.visibility = View.GONE
        }
    }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as PhoneApp).appComponent.inject(this)
    }



}