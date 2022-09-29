package ru.kalievmars.compositionapp.fragments

import android.app.Application
import android.content.Context
import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ru.kalievmars.compositionapp.R
import ru.kalievmars.compositionapp.databinding.FragmentGameBinding
import ru.kalievmars.compositionapp.viewmodels.MainViewModel
import ru.kalievmars.compositionapp.viewmodels.MainViewModelFactory
import ru.kalievmars.domain.entities.GameResult
import ru.kalievmars.domain.entities.GameSettings
import ru.kalievmars.domain.entities.Level
import ru.kalievmars.domain.entities.Question

class GameFragment : Fragment() {
    private val options: MutableList<TextView> by lazy {
     mutableListOf<TextView>().apply {
         add(binding.tvOption1)
         add(binding.tvOption2)
         add(binding.tvOption3)
         add(binding.tvOption4)
         add(binding.tvOption5)
         add(binding.tvOption6)
     }
    }
    private lateinit var level: Level
    private val viewModelFactory: MainViewModelFactory by lazy {
        MainViewModelFactory(requireActivity().application, level)
    }
    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(
            this,
            viewModelFactory
        )[MainViewModel::class.java]
    }
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
        observeViewModel()
    }

    private fun observeViewModel() {
        with(viewModel) {
            formattedTime.observe(viewLifecycleOwner) {
                binding.tvTimer.text = it
            }

            question.observe(viewLifecycleOwner) {
                with(binding) {
                    tvSum.text = it.sum.toString()
                    tvLeftNumber.text = it.visibleNumber.toString()
                    for (i in 0 until it.options.size) {
                        options[i].text = it.options[i].toString()

                        options[i].setOnClickListener { _ ->
                            viewModel.chooseAnswer(it.options[i])
                        }
                    }
                }
            }

            percentOfRightAnswers.observe(viewLifecycleOwner) {
                binding.progressBar.setProgress(it, true)
            }

            progressAnswers.observe(viewLifecycleOwner) {
                binding.tvAnswersProgress.text = it
            }

            enoughCountOfRightAnswers.observe(viewLifecycleOwner) {
                binding.tvAnswersProgress.setTextColor(getColorByState(it))
            }

            enoughPercentOfRightAnswers.observe(viewLifecycleOwner) {
                val color = getColorByState(it)
                binding.progressBar.progressTintList = ColorStateList.valueOf(color)
            }

            minPercent.observe(viewLifecycleOwner) {
                binding.progressBar.secondaryProgress = it
            }

            gameResult.observe(viewLifecycleOwner) {
                launchGameFinishedFragment(it)
            }
        }
    }

    private fun getColorByState(goodState: Boolean): Int {
        val colorResId = if(goodState) {
            android.R.color.holo_green_light
        } else {
            android.R.color.holo_red_light
        }
        return ContextCompat.getColor(requireContext(), colorResId)
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
