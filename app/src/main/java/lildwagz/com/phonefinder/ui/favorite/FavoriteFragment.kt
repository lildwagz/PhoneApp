package lildwagz.com.phonefinder.ui.favorite

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import lildwagz.com.phonefinder.PhoneApp
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.data.local.model.PhoneModel
import lildwagz.com.phonefinder.databinding.FragmentFavoriteBinding
import lildwagz.com.phonefinder.ui.ViewModelFactory
import javax.inject.Inject

class FavoriteFragment : Fragment() {

    @Inject
    lateinit var vmFactory: ViewModelFactory
    private val viewModel: FavoriteViewModel by viewModels { vmFactory }
    private lateinit var adapter: FavoriteAdapter
    private val binding : FragmentFavoriteBinding by viewBinding()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        setActions()

    }

    private fun setActions() = with(binding){
        viewModel.getFavoriteData()
    }

    private fun initRecyclerView() =with(binding) {
        viewModel.favorite.observe(viewLifecycleOwner){
            adapter = FavoriteAdapter(it as ArrayList<PhoneModel>)
            adapter.itemclicklistener = {
                val args = bundleOf(getString(R.string.key_phone) to it.slug)
                findNavController().navigate(R.id.action_nav_favorite_to_nav_detail,args)

            }
            adapter.longClickListener = {
                deleteFavorite(it)
            }
            recyclerview.adapter = adapter
            recyclerview.itemAnimator = null
            recyclerview.layoutManager = GridLayoutManager(context, 2)
            recyclerview.scrollToPosition(0)
        }

    }

    private fun deleteFavorite(phone : PhoneModel) = with(binding) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setMessage("Are you sure you want to Delete ${phone.phone_name} from Favorite List?")
            .setCancelable(false)
            .setPositiveButton("Yes") { dialog, _ ->
                dialog.dismiss()
                viewModel.removePhone(phone)
                viewModel.getFavoriteData()
                Toast.makeText(requireContext(),"Ok",Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
        builder.create().show()


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (requireActivity() as AppCompatActivity).supportActionBar?.show()

        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (requireActivity().application as PhoneApp).appComponent.inject(this)
    }

}