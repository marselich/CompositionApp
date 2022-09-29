package ru.kalievmars.compositionapp.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.kalievmars.compositionapp.R
import ru.kalievmars.compositionapp.databinding.FragmentGameBinding
import ru.kalievmars.compositionapp.viewmodels.MainViewModel
import ru.kalievmars.domain.entities.GameResult
import ru.kalievmars.domain.entities.GameSettings
import ru.kalievmars.domain.entities.Level

class GameFragment : Fragment() {

    private lateinit var level: Level
    private lateinit var viewModel: MainViewModel
    private lateinit var gameSettings: GameSettings

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("GameFragment == null")

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseArgs()


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.setGameSettings(level)
        viewModel.gameSettings.observe(viewLifecycleOwner) {
            gameSettings = it
        }
        viewModel.setQuestion(gameSettings.maxSumValue)


        val gameSettings = GameSettings(
            maxSumValue = 2,
            minCountOfRightAnswers = 3,
            minPercentOfRightAnswers = 3,
            gameTimeInSeconds = 2
        )
        val gameResult = GameResult(
            winner = true,
            countOfRightAnswers = 2,
            countOfQuestions = 2,
            gameSettings = gameSettings
        )
        binding.tvLeftNumber.setOnClickListener {
            launchGameFinishedFragment(gameResult)
        }
    }

    private fun initSettings() {
        with(binding) {
            tvTimer.setText(gameSettings.gameTimeInSeconds)

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        requireArguments().getParcelable<Level>(KEY_LEVEL)?.let {
            level = it
        }
    }

    private fun launchGameFinishedFragment(gameResult: GameResult) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.main_container, GameFinishedFragment.newInstance(gameResult))
            .addToBackStack(null)
            .commit()
    }

    companion object {

        const val NAME = "GameFragment"

        private const val KEY_LEVEL = "level"

        fun newInstance(level: Level): GameFragment {
            return GameFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_LEVEL, level)
                }
            }
        }

    }
}