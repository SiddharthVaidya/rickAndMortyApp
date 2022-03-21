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

class Groups:Fragment(), CellClickListener {
    val status = arrayListOf<String>("alive", "dead", "unknown")
    val species = arrayListOf<String>("Human", "Alien", "Poopybutthole", "Mythological Creature", "Disease", "Humanoid", "Animal", "Cronenberg", "Robot", "unknown")
    private var adapter = GroupAdapter(this)
    lateinit var speciesSpinner : Spinner
    private lateinit var mainViewModel: MainViewModel
    override fun onCreateView(inflater: LayoutInflater,
                              container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view: View = inflater.inflate(
            R.layout.groups, container,
            false)



        speciesSpinner = view.findViewById<Spinner>(R.id.speciesSpinner)
        val spinner = view.findViewById<Spinner>(R.id.spinner)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView_rv)
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter


        val aa2 = ArrayAdapter(this.requireContext(),com.google.android.material.R.layout.support_simple_spinner_dropdown_item, species )
        aa2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        speciesSpinner.adapter= aa2

        val aa =  ArrayAdapter(this.requireContext(), com.google.android.material.R.layout.support_simple_spinner_dropdown_item, status)
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter= aa



        val service = RetrofitHelper.getInstance().create(ApiInterface::class.java)
        val repository = CharacterRepository(service)
        mainViewModel = ViewModelProvider(this,
            MainViewModelFactory(repository)
        ).get(MainViewModel::class.java)
        mainViewModel.characters.observe(viewLifecycleOwner) {
            val arr = ArrayList<Result>()
            val alive = ArrayList<Result>()
            val dead = ArrayList<Result>()
            val unknown = ArrayList<Result>()
            for (i in 0 until 825 ){
                val char_brief = Result(it[i].created, it[i].episode, it[i].gender, it[i].id, it[i].image, it[i].location, it[i].name, it[i].origin, it[i].species, it[i].status, it[i].type, it[i].url)
                arr.add(char_brief)
                if(it[i].status == "Alive"){
                alive.add(char_brief)
                }
                else if(it[i].status == "Dead"){
                    dead.add(char_brief)
                }
                else{
                    unknown.add(char_brief)
                }
            }
            adapter.setList(alive)
            secondSpinner(arr)
            spinner.onItemSelectedListener = object :

                AdapterView.OnItemSelectedListener{
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                    when(p2){
                        0-> {adapter.setList(alive)
                            secondSpinner(alive)}
                        1->{adapter.setList(dead)
                            secondSpinner(dead)}
                        2->{adapter.setList(unknown)
                            secondSpinner(unknown)}
                        else->{adapter.setList(alive)
                            secondSpinner(alive)}
                    }
                }

                override fun onNothingSelected(p0: AdapterView<*>?) {
                    secondSpinner(alive)
                }

            }


        }



        return view
    }

    private fun secondSpinner(arr: ArrayList<Result>) {

        speciesSpinner.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                when(p2){
                    0-> {arr.filter { it.species == "Human" }
                            adapter.setList(arr.filter { it.species == "Human" } as ArrayList<Result>)
                            }
                    1->{arr.filter { it.species == "Alien" }
                        adapter.setList(arr.filter { it.species == "Alien" } as ArrayList<Result>)}
                    2->{arr.filter { it.species == "Poopybutthole" }
                        adapter.setList(arr.filter { it.species == "Poopybutthole" } as ArrayList<Result>)}
                    3->{arr.filter { it.species == "Mythological Creature" }
                        adapter.setList(arr.filter { it.species == "Mythological Creature" } as ArrayList<Result>)}
                    4->{arr.filter { it.species == "Disease" }
                        adapter.setList(arr.filter { it.species == "Disease" } as ArrayList<Result>)}
                    5->{arr.filter { it.species == "Humanoid" }
                        adapter.setList(arr.filter { it.species == "Humanoid" } as ArrayList<Result>)}
                    6->{arr.filter { it.species == "Animal" }
                        adapter.setList(arr.filter { it.species == "Animal" } as ArrayList<Result>) }
                    7->{arr.filter { it.species == "Cronenberg" }
                        adapter.setList(arr.filter { it.species == "Cronenberg" } as ArrayList<Result>)}
                    8->{arr.filter { it.species == "Robot" }
                        adapter.setList(arr.filter { it.species == "Robot" } as ArrayList<Result>)}
                    9->{arr.filter { it.species == "unknown" }
                        adapter.setList(arr.filter { it.species == "unknown" } as ArrayList<Result>)}
                }
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                adapter.setList(arr)
            }

        }
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