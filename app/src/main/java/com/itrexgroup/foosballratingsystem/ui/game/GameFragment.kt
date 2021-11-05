package com.itrexgroup.foosballratingsystem.ui.game

import android.app.DatePickerDialog
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.itrexgroup.foosballratingsystem.R
import com.itrexgroup.foosballratingsystem.databinding.FragmentGameBinding
import com.itrexgroup.foosballratingsystem.db.Game
import dagger.android.support.DaggerFragment
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import javax.inject.Inject

class GameFragment @Inject constructor() : DaggerFragment() {

    companion object {
        fun newInstance() = GameFragment()
    }

    val args: GameFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private val viewModel by viewModels<GameViewModel> { viewModelFactory }

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentGameBinding.inflate(inflater, container, false)
        val root = binding.root
        binding.viewmodel = viewModel
        viewModel.initGame(args.gameId)

        with(binding) {
            playerNameHomeEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onHomePlayerNameChanged(text.toString())
            }
            playerNameAwayEditText.doOnTextChanged { text, _, _, _ ->
                viewModel.onAwayPlayerNameChanged(text.toString())
            }

            viewModel.game.observe(viewLifecycleOwner, { bindGameToView(it) })

            viewModel.events.observe(viewLifecycleOwner, {
                when (it) {
                    is CloseScreenEvent -> findNavController().navigateUp()
                    is OpenDatePickerEvent -> showDatePickerDialog(it.dateTime)
                }
            })

            setupToolbar()
            return root
        }
    }

    private fun bindGameToView(it: Game?) {
        with(binding) {
            if (it != null) {
                if (playerNameAwayEditText.text.toString() != it.playerNameAway)
                    playerNameAwayEditText.setText(it.playerNameAway)
                if (playerNameHomeEditText.text.toString() != it.playerNameHome)
                    playerNameHomeEditText.setText(it.playerNameHome)
                playerScoreHome.text = it.scoreHome.toString()
                playerScoreAway.text = it.scoreAway.toString()
                gameDate.text = DateTimeFormat.shortDateTime().print(it.dateTime)
                winnerAway.isVisible = it.isAwayPlayerWins
                winnerHome.isVisible = it.isHomePlayerWins
                winnerHome.isVisible = it.isHomePlayerWins
                requireActivity().title =
                    if (it.playerNameHome.isNotEmpty() && it.playerNameAway.isNotEmpty()) getString(
                        R.string.player_vs_player,
                        it.playerNameHome,
                        it.playerNameAway
                    ) else
                        getString(R.string.new_game)
            }
        }
    }

    private fun showDatePickerDialog(dateTime: DateTime) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DatePickerDialog(requireContext())
                .apply {
                    updateDate(dateTime.year, dateTime.monthOfYear - 1, dateTime.dayOfMonth)
                    setOnDateSetListener { view, year, month, dayOfMonth ->
                        viewModel.onDatePicked(
                            DateTime().withYear(year).withMonthOfYear(month + 1)
                                .withDayOfMonth(dayOfMonth)
                        )
                    }
                }
                .show()
        }
    }

    private fun setupToolbar() {
        with((requireActivity() as AppCompatActivity)) {
            setSupportActionBar(binding.topAppBar)
            binding.topAppBar.setNavigationOnClickListener { findNavController().navigateUp() }
            title = if (args.gameId == 0) getString(R.string.new_game) else ""
            supportActionBar?.setDisplayShowHomeEnabled(true)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.game_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete -> {
                AlertDialog.Builder(requireContext())
                    .setPositiveButton(getString(R.string.yes)) { _: DialogInterface, i: Int ->
                        viewModel.onDeleteGameClicked()
                    }
                    .setNegativeButton(getString(R.string.no), null)
                    .setTitle(getString(R.string.delete_game_title))
                    .show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}