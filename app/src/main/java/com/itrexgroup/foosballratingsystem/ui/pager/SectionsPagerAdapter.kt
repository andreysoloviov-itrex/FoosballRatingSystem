package com.itrexgroup.foosballratingsystem.ui.pager

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.itrexgroup.foosballratingsystem.ui.ratings.RatingsListFragment
import com.itrexgroup.foosballratingsystem.ui.results.GamesListFragment

class SectionsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if (position == 1) GamesListFragment.newInstance() else RatingsListFragment.newInstance()
    }
}