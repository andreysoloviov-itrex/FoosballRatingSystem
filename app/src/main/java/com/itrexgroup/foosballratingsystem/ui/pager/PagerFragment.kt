package com.itrexgroup.foosballratingsystem.ui.pager

import android.content.DialogInterface
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.itrexgroup.foosballratingsystem.R
import com.itrexgroup.foosballratingsystem.databinding.FragmentMainBinding
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class PagerFragment @Inject constructor() : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val mainViewModel by viewModels<MainViewModel> { viewModelFactory }

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMainBinding.inflate(layoutInflater)

        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        val tabs: TabLayout = binding.tabs

        viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = getText(R.string.rating_title)
                    tab.icon = AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_stars_24
                    )
                }
                1 -> {
                    tab.text = getText(R.string.results_title)
                    tab.icon = AppCompatResources.getDrawable(
                        requireContext(),
                        R.drawable.ic_baseline_history_24
                    )
                }
            }
        }.attach()

        binding.addGameFab.setOnClickListener {
            val actionMainFragmentToGameFragment =
                PagerFragmentDirections.actionMainFragmentToGameFragment()
            requireParentFragment().findNavController().navigate(actionMainFragmentToGameFragment)
        }

        setupToolbar()
        return binding.root
    }

    private fun setupToolbar() {
        with((requireActivity() as AppCompatActivity)) {
            setSupportActionBar(binding.topAppBar)
            title = getText(R.string.app_name)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_new_season -> {
                AlertDialog.Builder(requireContext())
                    .setPositiveButton("YES") { _: DialogInterface, i: Int ->
                        mainViewModel.onNewSeasonClicked()
                    }
                    .setNegativeButton("NO", null)
                    .setTitle("Start new season?")
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}