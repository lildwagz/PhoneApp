package lildwagz.com.phonefinder.ui.additional.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.data.remote.response.DataTopByInterest
import lildwagz.com.phonefinder.databinding.ItemLayoutsBinding

class AdapterTopByInterest (private val phone : ArrayList<DataTopByInterest>) : RecyclerView.Adapter<AdapterTopByInterest.ViewHolder>()
 {
     var itemclicklistener : ((DataTopByInterest) -> Unit)? = null


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

        fun onBind(phone: DataTopByInterest) = with(binding) {
            tvTittle.text = phone.phoneName



        }
    }
}