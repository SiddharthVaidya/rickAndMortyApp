package com.example.rickandmortyapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.models.Result
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList


class CharacterListAdapter(private val cellClickListener: CellClickListener): RecyclerView.Adapter<CharacterViewHolder>(), Filterable {

    private var chars = ArrayList<Result>()
    var filterCharacters = ArrayList<Result>()



    //adding data
    fun setCharList(items: ArrayList<Result>) {
        chars = items

        filterCharacters = chars

        notifyDataSetChanged()
    }

    //adapter mandatory functions
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.character_brief, parent, false)
        return CharacterViewHolder(view)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.charName.text = filterCharacters[position].name
        holder.charStatus.text = filterCharacters[position].status
        Picasso.get().load(filterCharacters[position].image).into(holder.charImage)
        holder.layout.setOnClickListener {
            cellClickListener.onCellClickListener(filterCharacters[position])
        }


    }

    override fun getItemCount(): Int {
        return filterCharacters.size
    }



    //filter logic
    override fun getFilter(): Filter {
        return object : Filter(){
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val charSearch = p0.toString()
                if(charSearch.isEmpty()){
                    filterCharacters = chars
                }
                else{
                    val res = ArrayList<Result>()
                    for (i in chars){
                        if(i.name.lowercase(Locale.ROOT).contains(charSearch.lowercase(Locale.ROOT))|| i.episode.size.toString().equals(charSearch)){
                            res.add(i)
                        }
                    }
                    filterCharacters = res
                }
                val filterResults = FilterResults()
                filterResults.values = filterCharacters
                return filterResults
            }
@Suppress("UNCHECKED_CAST")
            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                filterCharacters = p1?.values as ArrayList<Result>
                notifyDataSetChanged()
            }
        }

    }

}

class CharacterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val charName: TextView = itemView.findViewById(R.id.name)
    val charStatus: TextView = itemView.findViewById(R.id.status)
    val charImage: ImageView = itemView.findViewById(R.id.characterImage)
    val layout: ConstraintLayout = itemView.findViewById(R.id.main_layout)
}