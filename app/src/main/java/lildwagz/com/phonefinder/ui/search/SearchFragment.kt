package lildwagz.com.phonefinder.ui.search

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import lildwagz.com.phonefinder.PhoneApp
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.data.remote.response.Phone
import lildwagz.com.phonefinder.databinding.FragmetSearchBinding
import lildwagz.com.phonefinder.ui.home.PhoneAdapter
import lildwagz.com.phonefinder.utils.KeyState
import lildwagz.com.phonefinder.vo.ListState
import javax.inject.Inject

class SearchFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private lateinit var viewModel: SearchViewModel
    private lateinit var adapter : PhoneAdapter
    private val binding : FragmetSearchBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel =  ViewModelProvider(this,vmFactory).get(SearchViewModel::class.java)


        setupViews()
        setupAction()
        setupBottomSheet()
    }

    private fun setupBottomSheet() = with(binding) {
        fab.setOnClickListener {
            val dialog = BottomSheetDialog(requireContext())
            val view = layoutInflater.inflate(R.layout.bottom_sheet_menu, null)
            val listBrands = view.findViewById<LinearLayout>(R.id.layoutBrands)
            val listTopByFans = view.findViewById<LinearLayout>(R.id.layoutToByFans)
            val listTopByInterest = view.findViewById<LinearLayout>(R.id.layoutTopByInterest)
            val closeButton = view.findViewById<ImageView>(R.id.btnClose)

            closeButton.setOnClickListener {
                dialog.dismiss()
            }



            listBrands.setOnClickListener {
                val args = bundleOf(getString(R.string.key_type) to KeyState.TYPE_LIST_BRANDS)
                findNavController().navigate(R.id.action_nav_search_to_nav_additional, args)
                dialog.dismiss()
            }

            listTopByFans.setOnClickListener {
                val args = bundleOf(getString(R.string.key_type) to KeyState.TYPE_TOP_BY_FANS)
                findNavController().navigate(R.id.action_nav_search_to_nav_additional, args)
                dialog.dismiss()
            }

            listTopByInterest.setOnClickListener {
                val args = bundleOf(getString(R.string.key_type) to KeyState.TYPE_TOP_BY_INTEREST)
                findNavController().navigate(R.id.action_nav_search_to_nav_additional, args)
                dialog.dismiss()
            }



            dialog.setCancelable(true)
            dialog.setContentView(view)


            dialog.show()
        }


    }

    private fun setupAction() {
        observeListState()
    }

    private fun observeListState() {
        viewModel.searchState.observe(
            viewLifecycleOwner
        ){
            when (it) {
                is ListState.Loading -> {
                    Log.d("datakses","loading")
                    onLoading()
                }
                is ListState.Empty -> {
                    onEmpty()
                }
                is ListState.Success -> {
                    Log.d("datakses","sukses ${it.payload[0].slug}")
                    onLoaded(it.payload)
                }
                is ListState.Error -> {
                    Log.d("datakses","error")
                    onError(it.error)
                }
            }
        }
    }

    private fun onEmpty() = with(binding){
        hideViews(listOf(lvLoading, recyclerview))
        infoMv.apply {
            setIcon(R.drawable.ic_baseline_sad)
            setText(R.string.empty_result_message)
            visibility = View.VISIBLE
        }
    }

    private fun onLoaded(payload: List<Phone>) = with(binding) {
        hideViews(listOf(lvLoading, infoMv, tvInfoAction))
        recyclerview.visibility = View.VISIBLE
        setupRv(payload)



    }

    private fun onError(error: Throwable) = with(binding){
        hideViews(listOf(lvLoading, recyclerview))
        infoMv.apply{
            setIcon(R.drawable.ic_baseline_sad)
            setText(error.localizedMessage ?: error::class.java.name)
            visibility = View.VISIBLE
        }
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

    private fun setupViews() = with(binding){
        lvLoading.visibility = View.GONE
        infoMv.apply {
            setIcon(R.drawable.ic_baseline_search)
            setText(R.string.search_message)
            visibility = View.VISIBLE
        }

        setupSearch()
    }

    private fun setupRv(payload: List<Phone>) = with(binding){
        adapter = PhoneAdapter(payload as ArrayList<Phone>)
        adapter.itemclicklistener = {
            val args = bundleOf(getString(R.string.key_phone) to it.slug)
            findNavController().navigate(R.id.action_nav_search_to_nav_detail,args)

        }
        recyclerview.adapter = adapter
        recyclerview.itemAnimator = null
        recyclerview.layoutManager = GridLayoutManager(context, 2)
        recyclerview.scrollToPosition(0)
    }

    private fun setupSearch() = with(binding) {
        with(searchView){
            setQuery("", false)
            setOnClickListener { isIconified = false }
            setOnQueryTextListener(object : SearchView.OnQueryTextListener{
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (!query.isNullOrEmpty()) {
                        viewModel.searchForPhone(query)
                        recyclerview.scrollToPosition(0)
                        Log.d("datakses",query)
                    }

                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        return inflater.inflate(R.layout.fragmet_search, container, false)
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as PhoneApp).appComponent.inject(this)
    }

}