package com.jamirodev.superheroesapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.TypedValue
import android.view.View
import com.jamirodev.superheroesapp.databinding.ActivityDetailSuperheroBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.roundToInt

class DetailSuperheroActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailSuperheroBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailSuperheroBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val id: String = intent.getStringExtra(EXTRA_ID).orEmpty()
        getSuperheroInformation(id)
    }

    private fun getSuperheroInformation(id: String) {
        CoroutineScope(Dispatchers.IO).launch {
            val superheroDetail =
                getRetrofit().create(ApiService::class.java).getSuperheroDetail(id)

            if (superheroDetail.body() != null) {
                runOnUiThread { createUI(superheroDetail.body()!!) }
            }
        }
    }

    private fun createUI(superhero: SuperHeroDetailResponse) {
        val raceLabel = getString(R.string.race_label, superhero.appearance.race)
        val genderLabel = getString(R.string.gender_label, superhero.appearance.gender)

        val heights = superhero.appearance.height
        val formattedHeights = buildHeightString(heights)

        val weights = superhero.appearance.weight
        val formattedWeights = buildWeightString(weights)

        val heightLabel = getString(R.string.height_label, formattedHeights)
        val weightLabel = getString(R.string.weight_label, formattedWeights)


        Picasso.get().load(superhero.image.url).into(binding.ivSuperhero)
        binding.tvSuperheroName.text = superhero.name
        prepareStats(superhero.powerstats)
        binding.tvAlterEgo.text = superhero.biography.alterEgo
        binding.tvSuperheroRealName.text = superhero.biography.fullName
        binding.tvPublisher.text = superhero.biography.publisher
        binding.tvRace.text = raceLabel
        binding.tvGender.text = genderLabel
        binding.tvHeight.text = heightLabel
        binding.tvWeight.text = weightLabel

    }
    private fun buildHeightString(heights: List<String>): String {
        val heightInFeet = heights[0]
        val heightInCm = heights[1]
        return "$heightInFeet ($heightInCm)"
    }
    private fun buildWeightString(weights: List<String>): String {
        val weightInLb = weights[0]
        val weightInKg = weights[1]

        return "$weightInLb ($weightInKg)"
    }

    private fun prepareStats(powerstats: PowerStatsResponse) {
        updateHeight(binding.viewCombat, powerstats.combat)
        updateHeight(binding.viewDurability, powerstats.durability)
        updateHeight(binding.viewSpeed, powerstats.speed)
        updateHeight(binding.viewStrength, powerstats.strength)
        updateHeight(binding.viewIntelligence, powerstats.intelligence)
        updateHeight(binding.viewPower, powerstats.power)
    }

    private fun updateHeight(view: View, stat: String) {
        val params = view.layoutParams
        params.height = pxToDp(stat.toFloat())
        view.layoutParams = params
    }

    private fun pxToDp(px: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)
            .roundToInt()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit
            .Builder()
            .baseUrl("https://superheroapi.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}