package lildwagz.com.phonefinder.ui.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.data.local.model.PhoneModel
import lildwagz.com.phonefinder.data.remote.response.Phone
import lildwagz.com.phonefinder.databinding.ItemLastestphoneBinding
import lildwagz.com.phonefinder.ui.home.PhoneAdapter
import lildwagz.com.phonefinder.utils.GlideHelper

class FavoriteAdapter(private val phone : ArrayList<PhoneModel>) : RecyclerView.Adapter<FavoriteAdapter.ViewHolder>() {

//    private val phone = ArrayList<PhoneModel>()
    var itemclicklistener: ((PhoneModel) -> Unit)? = null
    var longClickListener: ((PhoneModel) -> Unit)? = null


//    fun setPhone(phoneList: List<PhoneModel>) {
//        phone.addAll(phoneList)
//        notifyDataSetChanged()
//    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_favorite, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(phone[position])

        holder.itemView.setOnClickListener {
            itemclicklistener?.invoke(phone[position])
        }
        holder.itemView.setOnLongClickListener {
            longClickListener?.invoke(phone[position])
            return@setOnLongClickListener true
        }
    }


    inner class ViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemLastestphoneBinding.bind(view)

        fun onBind(phone: PhoneModel) = with(binding) {
            tvTittle.text = phone.phone_name
            GlideHelper.loadCover(
                view.context,
                phone.image,
                ivCover,
                COVER_WIDTH,
                COVER_HEIGHT
            )


        }
    }

    override fun getItemCount(): Int {
        return phone.size
    }

    companion object {
        private const val COVER_HEIGHT = 212
        private const val COVER_WIDTH = 160
    }
}