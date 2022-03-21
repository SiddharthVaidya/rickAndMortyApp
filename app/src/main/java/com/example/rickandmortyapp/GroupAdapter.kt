package com.example.rickandmortyapp

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.models.Result
import com.squareup.picasso.Picasso


class GroupAdapter(private val cellClickListener: CellClickListener) : RecyclerView.Adapter<GroupViewHolder>() {
    var grps = ArrayList<Result>()

    fun setList(items: ArrayList<Result>) {
        grps= items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.group_chars, parent, false)
        return GroupViewHolder(view)
    }

    override fun onBindViewHolder(holder: GroupViewHolder, position: Int) {
        holder.charName.text = grps[position].name
        holder.charStatus.text = grps[position].status
        Picasso.get().load(grps[position].image).into(holder.charImage)
        holder.layout.setOnClickListener {
            cellClickListener.onCellClickListener(grps[position])
        }
    }

    override fun getItemCount(): Int {
        return grps.size
    }

}

class GroupViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val charName: TextView = itemView.findViewById(R.id.name)
    val charStatus: TextView = itemView.findViewById(R.id.status)
    val charImage: ImageView = itemView.findViewById(R.id.characterImage)
    val layout: ConstraintLayout = itemView.findViewById(R.id.groupLayout)
}