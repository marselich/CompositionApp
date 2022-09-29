package ru.kalievmars.compositionapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import ru.kalievmars.compositionapp.R
import ru.kalievmars.compositionapp.databinding.FragmentGameFinishedBinding
import ru.kalievmars.domain.entities.GameResult

class GameFinishedFragment : Fragment() {


    private val args by navArgs<GameFinishedFragmentArgs>()
    private val gameResult: GameResult by lazy {
        args.gameResult
    }

    private var _binding: FragmentGameFinishedBinding? = null
    private val binding: FragmentGameFinishedBinding
        get() = _binding ?: throw RuntimeException("FragmentGameFinishedBinding == null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
//            parseArgs()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentGameFinishedBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setResult()

        requireActivity().onBackPressedDispatcher.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    retryGame()
                }

            }
        )
        binding.buttonRetry.setOnClickListener {
            retryGame()
        }

    }

    private fun setResult() {
        if(gameResult.winner) {
            binding.emojiResult.setImageResource(R.drawable.ic_smile)
        } else {
            binding.emojiResult.setImageResource(R.drawable.ic_sad)
        }


        setResultToTextView(
            binding.tvRequiredAnswers,
            R.string.required_score,
            gameResult.gameSettings.minCountOfRightAnswers.toString()
        )
        setResultToTextView(
            binding.tvScoreAnswers,
            R.string.score_answers,
            gameResult.countOfRightAnswers.toString()
        )
        setResultToTextView(
            binding.tvRequiredPercentage,
            R.string.required_percentage,
            gameResult.gameSettings.minPercentOfRightAnswers.toString()
        )
        setResultToTextView(
            binding.tvScorePercentage,
            R.string.score_percentage,
            gameResult.percentOfRightAnswers.toString()
        )
    }

    private fun setResultToTextView(textView: TextView, resId: Int, result: String) {
        textView.text = String.format(
            requireActivity().resources.getString(resId),
            result
        )
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

//    private fun parseArgs() {
//        requireArguments().getParcelable<GameResult>(KEY_GAME_RESULT)?.let {
//            gameResult = it
//        }
//    }

    private fun retryGame() {
        findNavController().popBackStack()
    }

    companion object {

        const val KEY_GAME_RESULT = "game_result"

        fun newInstance(gameResult: GameResult): GameFinishedFragment {
            return GameFinishedFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(KEY_GAME_RESULT, gameResult)
                }
            }
        }
    }
}