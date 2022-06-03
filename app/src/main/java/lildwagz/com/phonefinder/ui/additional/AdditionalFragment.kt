package lildwagz.com.phonefinder.ui.additional

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.phonefinder.PhoneApp
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.data.remote.response.DataBrands
import lildwagz.com.phonefinder.data.remote.response.DataTopByFans
import lildwagz.com.phonefinder.data.remote.response.DataTopByInterest
import lildwagz.com.phonefinder.databinding.FragmentAdditionalBinding
import lildwagz.com.phonefinder.ui.additional.adapter.AdapterBasic
import lildwagz.com.phonefinder.ui.additional.adapter.AdapterTopByFans
import lildwagz.com.phonefinder.ui.additional.adapter.AdapterTopByInterest
import lildwagz.com.phonefinder.utils.KeyState
import lildwagz.com.phonefinder.vo.BrandsState
import lildwagz.com.phonefinder.vo.TopByFansState
import lildwagz.com.phonefinder.vo.TopByInterestState
import javax.inject.Inject


class AdditionalFragment : Fragment() {


    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val binding : FragmentAdditionalBinding by viewBinding()
    private var key_state : String = ""
    private lateinit var adapter : AdapterBasic
    private lateinit var adapterTopByFans: AdapterTopByFans
    private lateinit var adapterTopByInterest: AdapterTopByInterest
    private val viewModel: AdditionalViewModel by lazy {
        ViewModelProvider(requireActivity(), vmFactory).get(AdditionalViewModel::class.java)
    }
    private var listDataBrands: List<DataBrands> = emptyList()
    private var listTopByFans : List<DataTopByFans> = emptyList()
    private var listTopbyInterest : List<DataTopByInterest> = emptyList()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        key_state = arguments?.get(getString(R.string.key_type)) as String

        setToolBar()
        setupAction()

    }

    private fun setToolBar() {
        when(key_state){
            KeyState.TYPE_LIST_BRANDS ->{
                (requireActivity() as AppCompatActivity).supportActionBar?.title = "List of Brands"

            }
            KeyState.TYPE_TOP_BY_FANS ->{
                (requireActivity() as AppCompatActivity).supportActionBar?.title = "Top By Fans"

            }KeyState.TYPE_TOP_BY_INTEREST ->{
                (requireActivity() as AppCompatActivity).supportActionBar?.title = "Top By Interest"

            }
        }
    }


    private fun setupRv() = with(binding){
        when(key_state){
            KeyState.TYPE_LIST_BRANDS ->{
                adapter = AdapterBasic(listDataBrands as ArrayList<DataBrands>)
                adapter.itemclicklistener = {
                    val args = bundleOf(getString(R.string.brand_id) to it.brandSlug)
                    findNavController().navigate(R.id.action_nav_additional_to_nav_listbrandsFragment,args)

                }
                recyclerview.adapter = adapter
                recyclerview.itemAnimator = null
                recyclerview.layoutManager = LinearLayoutManager(requireContext())
                recyclerview.scrollToPosition(0)

            }
            KeyState.TYPE_TOP_BY_FANS ->{
                adapterTopByFans = AdapterTopByFans(listTopByFans as ArrayList<DataTopByFans>)
                adapterTopByFans.itemclicklistener = {
                    val args = bundleOf(getString(R.string.key_phone) to it.slug)
                    findNavController().navigate(R.id.action_nav_additional_to_nav_detail,args)

                }
                recyclerview.adapter = adapterTopByFans
                recyclerview.itemAnimator = null
                recyclerview.layoutManager = LinearLayoutManager(requireContext())
                recyclerview.scrollToPosition(0)

            }
            KeyState.TYPE_TOP_BY_INTEREST ->{
                adapterTopByInterest = AdapterTopByInterest(listTopbyInterest as ArrayList<DataTopByInterest>)
                adapterTopByInterest.itemclicklistener = {
                    val args = bundleOf(getString(R.string.key_phone) to it.slug)
                    findNavController().navigate(R.id.action_nav_additional_to_nav_detail,args)

                }
                recyclerview.adapter = adapterTopByInterest
                recyclerview.itemAnimator = null
                recyclerview.layoutManager = LinearLayoutManager(requireContext())
                recyclerview.scrollToPosition(0)
            }
        }

    }



    private fun setupAction() = with(binding){
        viewModel.getMedia(key_state)
        when(key_state){
            KeyState.TYPE_LIST_BRANDS ->{
                viewModel.brandsState.observe(viewLifecycleOwner){
                    when (it) {
                        is BrandsState.Loading -> {
                            onLoading()
                        }
                        is BrandsState.Success -> {
                            listDataBrands = it.payload
                            onMediaLoaded()
                        }
                        is BrandsState.Error -> {
                            onError(it.error)
                        }
                    }
                }
            }
            KeyState.TYPE_TOP_BY_FANS ->{
                viewModel.topByFansSate.observe(viewLifecycleOwner){
                    when (it) {
                        is TopByFansState.Loading -> {
                            onLoading()
                        }
                        is TopByFansState.Success -> {
                            listTopByFans = it.payload
                            onMediaLoaded()
                        }
                        is TopByFansState.Error -> {
                            onError(it.error)
                        }
                    }
                }

            }
            KeyState.TYPE_TOP_BY_INTEREST->{
                viewModel.topByInterest.observe(viewLifecycleOwner){
                    when (it) {
                        is TopByInterestState.Loading -> {
                            onLoading()
                        }
                        is TopByInterestState.Success -> {
                            listTopbyInterest = it.payload
                            onMediaLoaded()
                        }
                        is TopByInterestState.Error -> {
                            onError(it.error)
                        }
                    }
                }
            }
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
                viewModel.getMedia(key_state)
            }
            visibility = View.VISIBLE
        }

    }

    private fun onMediaLoaded() = with(binding) {
        hideViews(listOf(lvLoading, infoMv, tvInfoAction))
        recyclerview.visibility = View.VISIBLE
        setupRv()

    }

    private fun onLoading() = with(binding) {
        hideViews(listOf(recyclerview, infoMv,tvInfoAction))
        lvLoading.visibility = View.VISIBLE
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        return inflater.inflate(R.layout.fragment_additional, container, false)
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