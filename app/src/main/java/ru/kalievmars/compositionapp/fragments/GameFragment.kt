package ru.kalievmars.compositionapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.kalievmars.compositionapp.R
import ru.kalievmars.compositionapp.databinding.FragmentGameBinding
import ru.kalievmars.domain.entities.GameResult
import ru.kalievmars.domain.entities.GameSettings
import ru.kalievmars.domain.entities.Level

class GameFragment : Fragment() {

    private lateinit var level: Level

    private var _binding: FragmentGameBinding? = null
    private val binding: FragmentGameBinding
        get() = _binding ?: throw RuntimeException("GameFragment == null")

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseArgs() {
        level = requireArguments().getSerializable(KEY_LEVEL) as Level
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
                    putSerializable(KEY_LEVEL, level)
                }
            }
        }

    }
}