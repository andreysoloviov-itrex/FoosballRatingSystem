package com.itrexgroup.foosballratingsystem.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.itrexgroup.foosballratingsystem.R
import com.itrexgroup.foosballratingsystem.databinding.FragmentResultsBinding
import com.itrexgroup.foosballratingsystem.ui.pager.PagerFragmentDirections
import dagger.android.support.DaggerFragment
import javax.inject.Inject


class GamesListFragment @Inject constructor() : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val gamesListViewModel by viewModels<GamesListViewModel> { viewModelFactory }
    private lateinit var gamesAdapter: GamesListAdapter

    private val args: GamesListFragmentArgs by navArgs()

    private var _binding: FragmentResultsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentResultsBinding.inflate(inflater, container, false)
        val root = binding.root

        setupToolbar()

        gamesListViewModel.initGames(args.playerName)

        gamesAdapter = GamesListAdapter {
            val navController = requireParentFragment().findNavController()
            val navDirection =
                if (navController.graph.startDestination == navController.currentDestination?.id)
                    PagerFragmentDirections.actionMainFragmentToGameFragment(it.id)
                else
                    GamesListFragmentDirections.actionGamesFragmentToGameFragment(it.id)
            navController.navigate(navDirection)
        }

        val linearLayoutManager = LinearLayoutManager(requireContext())
        with(binding) {
            gamesList.layoutManager = linearLayoutManager
            gamesList.adapter = gamesAdapter
            val dividerItemDecoration = DividerItemDecoration(
                requireContext(),
                linearLayoutManager.orientation
            )
            dividerItemDecoration.setDrawable(
                AppCompatResources.getDrawable(
                    requireContext(),
                    R.drawable.divider_with_padding
                )!!
            )
            gamesList.addItemDecoration(dividerItemDecoration)
            gamesListViewModel.games.observe(viewLifecycleOwner, {
                gamesAdapter.submitList(it)
            })
        }
        return root
    }

    private fun setupToolbar() {
        val playerName = args.playerName
        if (!playerName.isNullOrEmpty()) {
            with((requireActivity() as AppCompatActivity)) {
                binding.appbarLayout.isVisible = true
                binding.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }
                setSupportActionBar(binding.topAppBar)
                title = getString(R.string.player_games, playerName.toString())
                supportActionBar?.setDisplayShowHomeEnabled(true)
                supportActionBar?.setDisplayHomeAsUpEnabled(true)
            }
        }
    }

    companion object {

        @JvmStatic
        fun newInstance() = GamesListFragment().apply {
            arguments = Bundle()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}