package com.itrexgroup.foosballratingsystem.ui.ratings

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.itrexgroup.foosballratingsystem.R
import com.itrexgroup.foosballratingsystem.databinding.FragmentRatingsBinding
import com.itrexgroup.foosballratingsystem.ui.pager.PagerFragmentDirections
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class RatingsListFragment @Inject constructor() : DaggerFragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val ratingsListViewModel by viewModels<RatingsListViewModel> { viewModelFactory }
    private lateinit var ratingsAdapter: RatingsListAdapter

    private var _binding: FragmentRatingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentRatingsBinding.inflate(inflater, container, false)
        val root = binding.root

        ratingsAdapter = RatingsListAdapter {
            val actionMainFragmentToPlayerGamesFragment =
                PagerFragmentDirections.actionMainFragmentToPlayerGamesFragment(it.playerName)
            requireParentFragment().findNavController()
                .navigate(actionMainFragmentToPlayerGamesFragment)
        }
        val linearLayoutManager = LinearLayoutManager(requireContext())
        binding.ratingsList.layoutManager = linearLayoutManager
        binding.ratingsList.adapter = ratingsAdapter

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
        binding.ratingsList.addItemDecoration(dividerItemDecoration)

        ratingsListViewModel.ratings.observe(viewLifecycleOwner, {
            ratingsAdapter.submitList(it)
        })

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        @JvmStatic
        fun newInstance(): RatingsListFragment {
            return RatingsListFragment()
        }
    }

}