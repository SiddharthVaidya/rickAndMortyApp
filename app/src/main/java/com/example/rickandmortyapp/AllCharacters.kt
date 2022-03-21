package com.example.rickandmortyapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
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
import com.google.android.material.floatingactionbutton.FloatingActionButton

class AllCharacters: Fragment(), CellClickListener {

    private  var adapter = CharacterListAdapter(this)
    private lateinit var mainViewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(R.layout.all_characters, container,
            false)

        //Setting up recyclerView
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter= adapter
        //progressBar
        val progress = view.findViewById<ProgressBar>(R.id.pg)


        //searchView setup
        val search = view.findViewById<SearchView>(R.id.searchBar)
        search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                adapter.filter.filter(p0)
                return false
            }
        })




        progress.visibility = View.VISIBLE
        val service = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        val repository = CharacterRepository(service)
        mainViewModel = ViewModelProvider(this,
        MainViewModelFactory(repository)
        ).get(MainViewModel::class.java)
        mainViewModel.characters.observe(viewLifecycleOwner) {
            val arr = ArrayList<Result>()
            for (i in 0 until 825 ){
                val char_brief = Result(it[i].created, it[i].episode, it[i].gender, it[i].id, it[i].image, it[i].location, it[i].name, it[i].origin, it[i].species, it[i].status, it[i].type, it[i].url)
                arr.add(char_brief)

            }

            adapter.setCharList(arr)


        }
        progress.visibility = View.INVISIBLE

    return view}

    // Passing data for character details activity
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

interface CellClickListener{
    fun onCellClickListener(data: Result)
}

