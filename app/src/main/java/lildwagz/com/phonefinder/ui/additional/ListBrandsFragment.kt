package lildwagz.com.phonefinder.ui.additional

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.phonefinder.PhoneApp
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.data.remote.response.DataListPhonesBrand
import lildwagz.com.phonefinder.databinding.FragmentListBrandsBinding
import lildwagz.com.phonefinder.ui.additional.adapter.AdapterPhonesByBrand
import lildwagz.com.phonefinder.vo.PhonesBrandsState
import javax.inject.Inject


class ListBrandsFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: AdditionalViewModel by viewModels({requireActivity()}) { vmFactory }
    private val binding : FragmentListBrandsBinding by viewBinding()
    private lateinit var adapter : AdapterPhonesByBrand
    private var brands : String = ""


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        brands = arguments?.get(getString(R.string.brand_id)) as String

        setupAction()

    }

    private fun setupAction() {
        viewModel.getPhonesByBrands(brands)
        observeListState()

    }

    private fun setupRv(payload: List<DataListPhonesBrand>) = with(binding) {
        adapter = AdapterPhonesByBrand(payload as ArrayList<DataListPhonesBrand>)

        adapter.itemclicklistener = {
            val args = bundleOf(getString(R.string.key_phone) to it.slug)
            findNavController().navigate(R.id.action_nav_listbrandsFragment_to_nav_detail,args)

        }
        recyclerview.adapter = adapter
        recyclerview.itemAnimator = null
        recyclerview.layoutManager = GridLayoutManager(context, 2)
        recyclerview.scrollToPosition(0)
    }


    private fun observeListState() {
        viewModel.brandsPhoneState.observe(
            viewLifecycleOwner
        ){
            when (it) {
                is PhonesBrandsState.Loading -> {
                    Log.d("datalses","loading")
                    onLoading()
                }
                is PhonesBrandsState.Success -> {
                    Log.d("datalses","sukses")
                    onLoaded(it.payload)
                }
                is PhonesBrandsState.Error -> {
                    Log.d("datalses","error")
                    onError(it.error)
                }
            }
        }
    }

    private fun onLoaded(payload: List<DataListPhonesBrand>) = with(binding){
        hideViews(listOf(lvLoading, infoMv, tvInfoAction))
        recyclerview.visibility = View.VISIBLE
        setupRv(payload)
    }


    private fun onLoading() = with(binding) {
        hideViews(listOf(recyclerview, infoMv,tvInfoAction))
        lvLoading.visibility = View.VISIBLE
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
                viewModel.getPhonesByBrands(brands)
            }
            visibility = View.VISIBLE
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        (requireActivity() as AppCompatActivity).supportActionBar?.title = "List Phones"
        return inflater.inflate(R.layout.fragment_list_brands, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as PhoneApp).appComponent.inject(this)
    }

    private fun hideViews(views: List<View>) {
        views.forEach {
            it.visibility = View.GONE
        }
    }
}