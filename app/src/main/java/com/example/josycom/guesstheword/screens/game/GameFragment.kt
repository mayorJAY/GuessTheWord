package com.example.josycom.guesstheword.screens.game

import android.os.Bundle
import android.text.format.DateUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment.findNavController
import com.example.josycom.guesstheword.R
import com.example.josycom.guesstheword.databinding.GameFragmentBinding

/**
 * Fragment where the game is played
 */
class GameFragment : Fragment() {

    private lateinit var binding: GameFragmentBinding

    private lateinit var gameViewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate view and obtain an instance of the binding class
        binding = DataBindingUtil.inflate(
                inflater,
                R.layout.game_fragment,
                container,
                false
        )

        Log.i("GameFragment", "Called ViewModelProvider")
        gameViewModel = ViewModelProvider(this).get(GameViewModel::class.java)

        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = this

        gameViewModel.eventGameFinish.observe(viewLifecycleOwner, Observer { hasFinished ->
            if (hasFinished) {
                gameFinished()
                gameViewModel.onGameFinishComplete()
            }
        })
        gameViewModel.currentTime.observe(viewLifecycleOwner, Observer { currentTime ->
            binding.timerText.text = DateUtils.formatElapsedTime(currentTime)
        })
        return binding.root

    }

    /**
     * Called when the game is finished
     */
    private fun gameFinished() {
        val currentScore = gameViewModel.score.value ?: 0
        val action = GameFragmentDirections.actionGameToScore(currentScore)
        findNavController(this).navigate(action)
    }
}
