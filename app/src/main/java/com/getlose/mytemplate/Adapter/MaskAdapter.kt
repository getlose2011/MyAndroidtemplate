package com.getlose.mytemplate.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.getlose.mytemplate.Model.Feature
import com.getlose.mytemplate.databinding.RowMaskBinding

class MaskAdapter(private val itemClickListener: IMaskItemClickListener) : RecyclerView.Adapter<MaskAdapter.MaskHolder>() {

    val TAG = MaskAdapter::class.java.simpleName

    var features:List<Feature> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaskHolder {
        val binding = RowMaskBinding
            .inflate(LayoutInflater.from(parent.context),parent,false)
        return MaskHolder(binding)
    }

    override fun getItemCount(): Int {
        return features?.size?:0
    }

    override fun onBindViewHolder(holder: MaskHolder, position: Int) {
        val feature = features?.get(position)?.properties
        holder.binding.tvName.text = feature.name
        holder.binding.tvAdultAmount.text = feature.mask_adult.toString()
        holder.binding.tvChildAmount.text = feature.mask_child.toString()
        holder.binding.layoutItem.setOnClickListener {
            itemClickListener.onItemClickListener(features?.get(position))
        }
    }

    //holder
    inner class MaskHolder(var binding: RowMaskBinding): RecyclerView.ViewHolder(binding.root){}

    //item click
    interface IMaskItemClickListener {
        fun onItemClickListener(data: Feature)
    }
}