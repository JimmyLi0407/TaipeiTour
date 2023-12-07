package com.example.taipeitourv1.TaipeiTourList

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.taipeitourv1.DataClass.TaipeiTourData
import com.example.taipeitourv1.R

class TaipeiTourAdapter(var taipeitourData: List<TaipeiTourData>, private val onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<TaipeiTourHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaipeiTourHolder {
        context = parent.context
        return TaipeiTourHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_taipei_tour, parent, false))
    }

    override fun getItemCount(): Int {
        return taipeitourData.get(0).data.size
    }

    override fun onBindViewHolder(holder: TaipeiTourHolder, position: Int) {
        if (taipeitourData.get(0).data.get(position).images.size != 0) {
            Glide.with(context)
                .load(taipeitourData.get(0).data.get(position).images.get(0).src)
                .placeholder(R.drawable.image)
                .error(R.drawable.image)
                .into(holder.item_taipei_tour_imageview)
        } else if (taipeitourData.get(0).data.get(position).images.size == 0) {
            holder.item_taipei_tour_imageview.setImageResource(R.drawable.image)
        }

        holder.item_taipei_tour_title.text = taipeitourData.get(0).data.get(position).name
        holder.item_taipei_tour_content.text = taipeitourData.get(0).data.get(position).introduction
        holder.itemView.setOnClickListener {
            onItemClickListener.onItemClick(position)
        }
    }
}

class TaipeiTourHolder(view: View) : RecyclerView.ViewHolder(view) {
    val item_taipei_tour_imageview: ImageView = view.findViewById(R.id.Item_Taipei_Tour_ImageView)
    val item_taipei_tour_title: TextView = view.findViewById(R.id.Item_Taipei_Tour_Title)
    val item_taipei_tour_content: TextView = view.findViewById(R.id.Item_Taipei_Tour_Content)
}