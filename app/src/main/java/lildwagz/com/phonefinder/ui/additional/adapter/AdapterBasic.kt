package lildwagz.com.phonefinder.ui.additional.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.data.remote.response.DataBrands
import lildwagz.com.phonefinder.databinding.ItemLayoutsBinding


class AdapterBasic(private val phone : ArrayList<DataBrands>) : RecyclerView.Adapter<AdapterBasic.ViewHolder>() {

    var itemclicklistener : ((DataBrands) -> Unit)? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_layouts, parent, false)
        return ViewHolder(view)    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(phone[position])

        holder.itemView.setOnClickListener {
            itemclicklistener?.invoke(phone[position])
        }    }

    override fun getItemCount(): Int {
        return phone.size
    }

    inner class ViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {

        private val binding = ItemLayoutsBinding.bind(view)

        fun onBind(phone: DataBrands) = with(binding) {
            tvTittle.text = phone.brandName



        }
    }
}