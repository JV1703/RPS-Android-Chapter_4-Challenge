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

        val playerChoices: List<ShapeableImageView> = listOf(
            binding.batuPlayer,
            binding.kertasPlayer,
            binding.guntingPlayer
        )

        val computerChoices: List<ShapeableImageView> = listOf(
            binding.batuComputer,
            binding.kertasComputer,
            binding.guntingComputer
        )

        val allAvailableChoices: List<ShapeableImageView> =
            mergeAllChoices(playerChoices, computerChoices)



        setTitleColor()
        recreateState(allAvailableChoices)

        playerChoices.forEach {
            it.setOnClickListener { shapeAbleView ->
                gameViewModel.setChoice(shapeAbleView.contentDescription.toString())
                gameViewModel.setPlayerSelectedId(shapeAbleView.id)
                shapeAbleView.setBackgroundColor(getColor(R.color.selected))
                freezeState(allAvailableChoices)
                gameViewModel.playGame()
                viewComputerChoice(computerChoices)
                setResult()
            }
        }

        binding.refresh.setOnClickListener {
            restartGame(allAvailableChoices)
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
    private fun viewComputerChoice(computerChoices: List<ShapeableImageView>) {
        val computerChoice =
            computerChoices.filter { it.contentDescription == gameViewModel.computerChoice }
        computerChoice[0].setBackgroundColor(getColor(R.color.selected))
    }

    // make all button non-tappable except restart button.
    private fun freezeState(allAvailableChoices: List<ShapeableImageView>) {
        gameViewModel.setStatus(false)
        allAvailableChoices.forEach {
            it.isEnabled = gameViewModel.status
        }
    }

    // un-freeze freezeState or restart the game
    private fun restartGame(allAvailableChoices: List<ShapeableImageView>) {
        gameViewModel.setStatus(true)
        allAvailableChoices.forEach {
            it.isEnabled = gameViewModel.status
            it.setBackgroundColor(getColor(R.color.white))
        }
        binding.result.text = getString(R.string.start_game)
        binding.result.setTextColor(getColor(R.color.VS))
        binding.result.setBackgroundColor(getColor(R.color.white))
    }

    // to handle configuration changes
    private fun recreateState(selectedChoices: List<ShapeableImageView>) {
        if (gameViewModel.choice.isNotBlank()) {
            findViewById<ShapeableImageView>(gameViewModel.playerSelectedId).setBackgroundColor(
                getColor(R.color.selected)
            )

            when (gameViewModel.computerChoice) {
                gameViewModel.choices[0] -> binding.batuComputer
                gameViewModel.choices[1] -> binding.kertasComputer
                gameViewModel.choices[2] -> binding.guntingComputer
                else -> null
            }!!.setBackgroundColor(getColor(R.color.selected))

            freezeState(selectedChoices)
            setResult()
        }
    }

    // to merge all playable choices
    private fun mergeAllChoices(vararg shapeableImageViews: List<ShapeableImageView>): List<ShapeableImageView> {
        val mergedChoices = mutableListOf<ShapeableImageView>()
        shapeableImageViews.forEach {
            mergedChoices.addAll(it)
        }
        return mergedChoices
    }
}