package com.example.rickandmortyapp

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

import com.squareup.picasso.Picasso

class CharacterDetailsActivity:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.character_details)

        val image = intent.getStringExtra("image")
        val charImage = findViewById<ImageView>(R.id.characterImage)
        val charName = findViewById<TextView>(R.id.characterName)
        val charGender = findViewById<TextView>(R.id.gender)
        val charSpecies = findViewById<TextView>(R.id.species)
        val charCreatedOn = findViewById<TextView>(R.id.created_on)
        val charType = findViewById<TextView>(R.id.type)
        val charLocation= findViewById<TextView>(R.id.location)
        val charEpisodes = findViewById<TextView>(R.id.episodes)
        Picasso.get().load(image).into(charImage)
        charName.text= "Name: "+intent.getStringExtra("name")
        charSpecies.text = "Species: "+intent.getStringExtra("species")
        charType.text = "Type: "+ if(intent.getStringExtra("type")!!.isEmpty()) "No Type" else intent.getStringExtra("type")
        charGender.text = "Gender: "+intent.getStringExtra("gender")
        charCreatedOn.text = "Created On: \n"+intent.getStringExtra("createdOn")
        charLocation.text = "Current Location: "+ intent.getStringExtra("location")
        charEpisodes.text = "Episodes Featured: "+ intent.getStringExtra("episodes")+" episodes"
    }
}