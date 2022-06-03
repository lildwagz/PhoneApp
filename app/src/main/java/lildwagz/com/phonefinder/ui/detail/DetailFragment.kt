package lildwagz.com.phonefinder.ui.detail

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ShareCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.phonefinder.PhoneApp
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.data.local.model.PhoneModel
import lildwagz.com.phonefinder.data.remote.response.DataDetail
import lildwagz.com.phonefinder.databinding.FragmentDetailBinding
import lildwagz.com.phonefinder.utils.GlideHelper
import lildwagz.com.phonefinder.vo.DetailsState
import javax.inject.Inject


class DetailFragment : Fragment() {


    @Inject
    lateinit var vmFactory: ViewModelProvider.Factory
    private val viewModel: DetailViewModel by viewModels { vmFactory }
    private var isStarred: Boolean = false
    private val binding : FragmentDetailBinding by viewBinding()
    private var phoneKey : String = ""
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupAction()
    }

    private fun setupAction() = with(binding) {
        getArgument()
        Log.d("datamasuk",phoneKey)
        getDetailPhone(phoneKey)
        viewModel.detailsState.observe(
            viewLifecycleOwner
        ) {
            when (it) {
                is DetailsState.Loading -> {
                    onLoading()
                }
                is DetailsState.Success -> {
                    onMediaLoaded(it.payload)
                }
                is DetailsState.Error -> {
                    onError(it.error)
                }
            }
        }

    }

    private fun getArgument() {
        phoneKey = arguments?.get(getString(R.string.key_phone)) as String
    }

    private fun onError(error: Throwable) = with(binding) {
        hideViews(listOf(lvLoading, contentCl))
        infoMv.setIcon(R.drawable.ic_baseline_sad)
        infoMv.setText(error::class.java.name)
        infoMv.visibility = View.VISIBLE
    }

    private fun onMediaLoaded(payload: DataDetail) = with(binding) {
        hideViews(listOf(lvLoading, infoMv))
        Log.d("datamasuk",payload.toString())

        setViews(payload)

        contentCl.visibility = View.VISIBLE

    }

    private fun setViews(payload: DataDetail) = with(binding) {
        val phoenName = payload.phoneName
        val releaseDate = payload.releaseDate
        val dimesion = payload.dimension
        val storage = payload.storage
        val os = payload.os
        val network = "Technology \t: ${payload.specifications[0]?.specs[0]?.valX[0]}\n"
        val body = "Dimensions \t: ${payload.specifications[2]?.specs[0]?.valX[0]}\n" +
                "Weight \t: ${payload.specifications[2]?.specs[1]?.valX[0]}"
        val display = "Type \t: ${payload.specifications[3]?.specs[0]?.valX[0]}\n"+
                "Size \t: ${payload.specifications[3]?.specs[1]?.valX[0]}\n"+
                "Resolution \t: ${payload.specifications[3]?.specs[2]?.valX[0]}"
        val platform = "${payload.specifications[4].specs[0].key} \t: ${payload.specifications[4].specs[0].valX[0]}"

        val memory = "${payload.specifications[5].specs[0].key} \t: ${payload.specifications[5].specs[0].valX[0]}\n"+
                    "${payload.specifications[5].specs[0].key} \t: ${payload.specifications[5].specs[1].valX[0]}"

        val mainCam = "${payload.specifications[6]?.specs[0].key} \t: ${payload.specifications[6].specs[0].valX[0]}\n"+
                "${payload.specifications[6].specs[0].key} \t: ${payload.specifications[6].specs[1].valX[0]}\n"+
                "${payload.specifications[6].specs[0]?.key} \t: ${payload.specifications[6].specs[2].valX[0]}"

        val selfieCam = "${payload.specifications[7].specs[0].key} \t: ${payload.specifications[7].specs[0].valX[0]}\n"+
                "${payload.specifications[7].specs[0].key} \t: ${payload.specifications[7].specs[1].valX[0]}"

        val sound = "${payload.specifications[8].specs[0].key} \t: ${payload.specifications[8].specs[0].valX[0]}\n"+
                "${payload.specifications[8].specs[0].key} \t: ${payload.specifications[8].specs[1].valX[0]}"

        val comms = "${payload.specifications[9].specs[0].key} \t: ${payload.specifications[9].specs[0].valX[0]}\n"+
                "${payload.specifications[9].specs[0].key} \t: ${payload.specifications[9].specs[1].valX[0]}"

        val features = "${payload.specifications[10].specs[0].key} \t: ${payload.specifications[10].specs[0].valX[0]}"

        val battery = "${payload.specifications[11].specs[0].key} \t: ${payload.specifications[11].specs[0].valX[0]}"
        tvPhoneName.text = phoenName
        tvReleaseDate.text = releaseDate
        tvDimension.text = dimesion
        tvStorage.text = storage
        tvOs.text = payload.os
        tvNetwork.text = network
        tvBody.text = body
        tvDisplay.text = display
        tvPlatform.text = platform
        tvMemory.text = memory
        tvMainCamera.text = mainCam
        tvSelfieCamera.text = selfieCam
        tvSound.text = sound
        tvComms.text = comms
        tvFeatures.text = features

        tvBattery.text = battery
        GlideHelper.loadCover(
            root.context,
            payload.phoneImages[0],
            ivPhone,
            COVER_WIDTH,
            COVER_HEIGHT
        )
        tvMisc.text = payload.specifications[12].specs[0].valX[0]
        setFavoriteAction(payload)
        lbShare.setOnClickListener {
            val shareContent = "Phone Name : ${payload.phoneName}\nRelease : ${payload.releaseDate}"
            ShareCompat.IntentBuilder(requireContext())
                .setText(shareContent)
                .setType("text/plain")
                .startChooser()
        }


    }

    private fun setFavoriteAction(payload: DataDetail) = with(binding){
        val dataPhone = PhoneModel(
            slug = phoneKey,
            phone_name = payload.phoneName,
            image = payload.phoneImages[0]
        )
        lbFavorite.setOnClickListener {
            viewModel.addtoFavorite(dataPhone)
            Toast.makeText(requireContext(),"OK",Toast.LENGTH_SHORT).show()
        }
    }

    private fun onLoading() = with(binding) {
        hideViews(listOf(contentCl, infoMv))
        lvLoading.visibility = View.VISIBLE
    }

    private fun getDetailPhone(phoneKey: String) {
        viewModel.getMediaDetails(
            phoneKey = phoneKey
        )


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
        return inflater.inflate(R.layout.fragment_detail, container, false)
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

    companion object {
        private const val COVER_HEIGHT = 212
        private const val COVER_WIDTH = 160
    }

    }