package com.example.rps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.rps.databinding.ActivityMainBinding
import com.example.rps.model.GameViewModel
import com.google.android.material.imageview.ShapeableImageView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        binding.gameViewModel = gameViewModel

        setTitleColor()
        recreateState()

        binding.batuPlayer.setOnClickListener {
            gameViewModel.setChoice(getString(R.string.batuChoice))
            gameViewModel.setPlayerSelectedId(it.id)
            it.setBackgroundColor(getColor(R.color.selected))
            freezeState()
            gameViewModel.playGame(gameViewModel.choice)
            viewComputerChoice()
            setResult()
        }

        binding.kertasPlayer.setOnClickListener {
            gameViewModel.setChoice(getString(R.string.kertasChoice))
            gameViewModel.setPlayerSelectedId(it.id)
            it.setBackgroundColor(getColor(R.color.selected))
            freezeState()
            gameViewModel.playGame(gameViewModel.choice)
            viewComputerChoice()
            setResult()
        }

        binding.guntingPlayer.setOnClickListener {
            gameViewModel.setChoice(getString(R.string.guntingChoice))
            gameViewModel.setPlayerSelectedId(it.id)
            it.setBackgroundColor(getColor(R.color.selected))
            freezeState()
            gameViewModel.playGame(gameViewModel.choice)
            viewComputerChoice()
            setResult()
        }

        binding.refresh.setOnClickListener {
            restartGame()
        }
    }

    // set color of game title
    private fun setTitleColor() {
        val title = binding.title
        val spannableString = SpannableString(getString(R.string.title))
        val titleKertas =
            ForegroundColorSpan(getColor(R.color.title_orange))
        val titleGunting =
            ForegroundColorSpan(getColor(R.color.title_green))
        val titleBatu = ForegroundColorSpan(getColor(R.color.title_purple))

        spannableString.setSpan(titleKertas, 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(titleGunting, 7, 14, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannableString.setSpan(titleBatu, 15, 19, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        title.text = spannableString
    }

    // show the result of the match
    private fun setResult() {
        when (gameViewModel.result) {
            "draw" -> {
                binding.result.text = getString(R.string.draw)
                binding.result.setTextColor(getColor(R.color.white))
                binding.result.setBackgroundColor(getColor(R.color.draw))
            }
            "win" -> {
                binding.result.text = getString(R.string.player_win)
                binding.result.setTextColor(getColor(R.color.white))
                binding.result.setBackgroundColor(getColor(R.color.result))
            }
            else -> {
                binding.result.text = getString(R.string.computer_win)
                binding.result.setTextColor(getColor(R.color.white))
                binding.result.setBackgroundColor(getColor(R.color.result))
            }
        }
    }

    // highlight computer choice
    private fun viewComputerChoice() {
        when (gameViewModel.computerChoice) {
            "Batu" -> {
                binding.batuComputer.setBackgroundColor(getColor(R.color.selected))
                binding.kertasComputer.setBackgroundColor(getColor(R.color.white))
                binding.guntingComputer.setBackgroundColor(getColor(R.color.white))
            }
            "Kertas" -> {
                binding.batuComputer.setBackgroundColor(getColor(R.color.white))
                binding.kertasComputer.setBackgroundColor(getColor(R.color.selected))
                binding.guntingComputer.setBackgroundColor(getColor(R.color.white))
            }
            "Gunting" -> {
                binding.batuComputer.setBackgroundColor(getColor(R.color.white))
                binding.kertasComputer.setBackgroundColor(getColor(R.color.white))
                binding.guntingComputer.setBackgroundColor(getColor(R.color.selected))
            }
        }
    }

    // make all button non-tappable except restart button.
    private fun freezeState() {
        gameViewModel.setStatus(false)
        binding.batuPlayer.isEnabled = gameViewModel.status
        binding.kertasPlayer.isEnabled = gameViewModel.status
        binding.guntingPlayer.isEnabled = gameViewModel.status
    }

    // un-freeze freezeState or restart the game
    private fun restartGame() {
        gameViewModel.setStatus(true)
        binding.batuPlayer.isEnabled = gameViewModel.status
        binding.kertasPlayer.isEnabled = gameViewModel.status
        binding.guntingPlayer.isEnabled = gameViewModel.status
        binding.batuPlayer.setBackgroundColor(getColor(R.color.white))
        binding.kertasPlayer.setBackgroundColor(getColor(R.color.white))
        binding.guntingPlayer.setBackgroundColor(getColor(R.color.white))
        binding.batuComputer.setBackgroundColor(getColor(R.color.white))
        binding.kertasComputer.setBackgroundColor(getColor(R.color.white))
        binding.guntingComputer.setBackgroundColor(getColor(R.color.white))
        binding.result.text = getString(R.string.start_game)
        binding.result.textSize = 16f
        binding.result.setTextColor(getColor(R.color.VS))
        binding.result.setBackgroundColor(getColor(R.color.white))
    }

    // to handle configuration changes
    private fun recreateState() {
        if (gameViewModel.choice.isNotBlank()) {
            val selectedPlayerButton =
                findViewById<ShapeableImageView>(gameViewModel.playerSelectedId)
            selectedPlayerButton.setBackgroundColor(getColor(R.color.selected))

            val selectedComputerButton =
                when (gameViewModel.computerChoice) {
                    gameViewModel.choices[0] -> binding.batuComputer
                    gameViewModel.choices[1] -> binding.kertasComputer
                    gameViewModel.choices[2] -> binding.guntingComputer
                    else -> null
                }
            selectedComputerButton!!.setBackgroundColor(getColor(R.color.selected))
            freezeState()
            setResult()
        }
    }
}