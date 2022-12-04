package com.example.starwarsapp.ui.movieDetail.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.starwarsapp.R
import com.example.starwarsapp.StarWarsApplication
import com.example.starwarsapp.ui.movieDetail.characters.view.CharactersFragment
import com.example.starwarsapp.ui.movieDetail.planets.view.PlanetsFragment
import com.example.starwarsapp.ui.movieDetail.ships.view.ShipsFragment
import com.example.starwarsapp.ui.movieDetail.species.view.SpeciesFragment

class PageAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {
    override fun getCount(): Int {
        return 4
    }

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                return CharactersFragment()
            }
            1 -> {
                return PlanetsFragment()
            }
            2 -> {
                return SpeciesFragment()
            }
            3 -> {
                return ShipsFragment()
            }
            else -> {
                return CharactersFragment()
            }
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> {
                return StarWarsApplication.getResources()?.getString(R.string.characters)
            }
            1 -> {
                return StarWarsApplication.getResources()?.getString(R.string.planets)
            }
            2 -> {
                return StarWarsApplication.getResources()?.getString(R.string.species)
            }
            3 -> {
                return StarWarsApplication.getResources()?.getString(R.string.ships)
            }
        }
        return super.getPageTitle(position)
    }
}
