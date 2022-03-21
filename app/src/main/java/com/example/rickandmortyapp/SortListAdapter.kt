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

class SortListAdapter(private val cellClickListener: CellClickListener): RecyclerView.Adapter<SortListViewHolder>() {
     var chars = ArrayList<Result>()

    fun setCharList(items: ArrayList<Result>) {

        chars = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SortListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_brief, parent, false)
        return SortListViewHolder(view)
    }

    override fun onBindViewHolder(holder: SortListViewHolder, position: Int) {
        holder.charName.text = chars[position].name
        holder.charStatus.text = chars[position].status
        Picasso.get().load(chars[position].image).into(holder.charImage)
        holder.layout.setOnClickListener {
            cellClickListener.onCellClickListener(chars[position])
        }
    }

    override fun getItemCount(): Int {
        return chars.size
    }

}

class SortListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val charName: TextView = itemView.findViewById(R.id.name)
    val charStatus: TextView = itemView.findViewById(R.id.status)
    val charImage: ImageView = itemView.findViewById(R.id.characterImage)
    val layout: ConstraintLayout = itemView.findViewById(R.id.main_layout)
}