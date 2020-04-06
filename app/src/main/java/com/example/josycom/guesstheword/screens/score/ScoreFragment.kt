package com.example.josycom.guesstheword.screens.score

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.josycom.guesstheword.R
import com.example.josycom.guesstheword.databinding.ScoreFragmentBinding

/**
 * Fragment where the final score is shown, after the game is over
 */
class ScoreFragment : Fragment() {

    private lateinit var scoreViewModelFactory: ScoreViewModelFactory
    private lateinit var scoreViewModel: ScoreViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        // Inflate view and obtain an instance of the binding class.
        val binding: ScoreFragmentBinding = DataBindingUtil.inflate(
                inflater,
                R.layout.score_fragment,
                container,
                false
        )

        // Get args using by navArgs property delegate
        val scoreFragmentArgs by navArgs<ScoreFragmentArgs>()
        scoreViewModelFactory = ScoreViewModelFactory(scoreFragmentArgs.score)
        scoreViewModel = ViewModelProvider(this, scoreViewModelFactory).get(ScoreViewModel::class.java)

        binding.scoreViewModel = scoreViewModel
        binding.lifecycleOwner = this

        scoreViewModel.eventPlayAgain.observe(viewLifecycleOwner, Observer { playAgain ->
            if (playAgain){
                findNavController().navigate(ScoreFragmentDirections.actionRestart())
                scoreViewModel.onPlayAgainComplete()
            }
        })
        return binding.root
    }
}
