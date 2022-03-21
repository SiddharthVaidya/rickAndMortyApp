package com.example.rickandmortyapp

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmortyapp.api.ApiInterface
import com.example.rickandmortyapp.api.RetrofitHelper
import com.example.rickandmortyapp.models.Result
import com.example.rickandmortyapp.repository.CharacterRepository
import com.example.rickandmortyapp.viewmodels.MainViewModel
import com.example.rickandmortyapp.viewmodels.MainViewModelFactory

class Sort:Fragment(), CellClickListener {
    private var adapter = SortListAdapter(this)
    var sortBy = arrayListOf<String>("Name", "Number of episodes")
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(
            R.layout.sort, container,
            false
        )
        val sortRV = view.findViewById<RecyclerView>(R.id.sortRV)
        sortRV.layoutManager = LinearLayoutManager(this.context)
        sortRV.adapter = adapter
        val sortSpinner = view.findViewById<Spinner>(R.id.sortSpinner)
        val arrayAdapter = ArrayAdapter(this.requireContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, sortBy)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        sortSpinner.adapter = arrayAdapter



        val service = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        val repository = CharacterRepository(service)
        mainViewModel = ViewModelProvider(this,
            MainViewModelFactory(repository)
        ).get(MainViewModel::class.java)
        mainViewModel.characters.observe(viewLifecycleOwner) {
            val arr = ArrayList<Result>()
            for (i in 0 until 825) {
                val char_brief = Result(
                    it[i].created,
                    it[i].episode,
                    it[i].gender,
                    it[i].id,
                    it[i].image,
                    it[i].location,
                    it[i].name,
                    it[i].origin,
                    it[i].species,
                    it[i].status,
                    it[i].type,
                    it[i].url
                )
                arr.add(char_brief)
            }
            adapter.setCharList(arr)
            sortSpinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when(p2){
                        0-> {
                            val nameSorted = arr.sortedBy { it.name }
                            val a = nameSorted.toList()
                            adapter.setCharList(a as ArrayList<Result>)
                        }
                        1->{val nameSorted = arr.sortedByDescending { it.episode.size }
                            val a = nameSorted.toList()
                            adapter.setCharList(a as ArrayList<Result>)}
                        else->{adapter.setCharList(arr)}
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    adapter.setCharList(arr)
                }

            }
        }

    return view
    }

    override fun onCellClickListener(data: Result) {
        val intent = Intent(activity, CharacterDetailsActivity::class.java)
        intent.putExtra("image", data.image)
        intent.putExtra("name", data.name)
        intent.putExtra("species", data.species)
        intent.putExtra("gender", data.gender)
        intent.putExtra("type", data.type)
        intent.putExtra("createdOn", data.created)
        intent.putExtra("location", data.location.name)
        intent.putExtra("episodes",  " " +data.episode.size)
        startActivity(intent)
    }
}