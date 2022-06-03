package lildwagz.com.phonefinder.ui.additional.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.data.remote.response.DataListPhonesBrand
import lildwagz.com.phonefinder.databinding.ItemLastestphoneBinding
import lildwagz.com.phonefinder.utils.GlideHelper

class AdapterPhonesByBrand( private val phone :  ArrayList<DataListPhonesBrand>) : RecyclerView.Adapter<AdapterPhonesByBrand.ViewHolder>() {

    var itemclicklistener : ((DataListPhonesBrand) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_lastestphone, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(phone[position])

        holder.itemView.setOnClickListener {
            itemclicklistener?.invoke(phone[position])
        }
    }


    inner class ViewHolder(private val view: View
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemLastestphoneBinding.bind(view)

        fun onBind(phone: DataListPhonesBrand) = with(binding){
            tvTittle.text = phone.phoneName
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