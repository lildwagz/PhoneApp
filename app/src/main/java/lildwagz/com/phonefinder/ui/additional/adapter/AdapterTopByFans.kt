package lildwagz.com.phonefinder.ui.additional.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import lildwagz.com.phonefinder.R
import lildwagz.com.phonefinder.data.remote.response.DataTopByFans
import lildwagz.com.phonefinder.databinding.ItemLayoutsBinding

class AdapterTopByFans(private val phone : ArrayList<DataTopByFans>) : RecyclerView.Adapter<AdapterTopByFans.ViewHolder>()
 {
     var itemclicklistener : ((DataTopByFans) -> Unit)? = null


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

        fun onBind(phone: DataTopByFans) = with(binding) {
            tvTittle.text = phone.phoneName



        }
    }
}