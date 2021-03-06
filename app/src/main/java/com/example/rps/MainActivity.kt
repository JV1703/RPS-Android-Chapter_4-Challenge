package com.example.rps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.rps.databinding.ActivityMainBinding
import com.example.rps.model.GameViewModel
import com.google.android.material.imageview.ShapeableImageView


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gameViewModel: GameViewModel by viewModels()

    private lateinit var playerChoices: List<ShapeableImageView>
    private lateinit var computerChoices: List<ShapeableImageView>
    private lateinit var allAvailableChoices: List<ShapeableImageView>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )

        binding.gameViewModel = gameViewModel
        binding.lifecycleOwner = this

        playerChoices = listOf(
            binding.batuPlayer,
            binding.kertasPlayer,
            binding.guntingPlayer
        )

        computerChoices = listOf(
            binding.batuComputer,
            binding.kertasComputer,
            binding.guntingComputer
        )

        allAvailableChoices = mergeAllChoices(playerChoices, computerChoices)


        setTitleColor()
        recreateState(allAvailableChoices)

        playerChoices.forEach {
            it.setOnClickListener { choice ->
                gameViewModel.setChoice(choice.contentDescription.toString())
                gameViewModel.setPlayerSelectedId(choice.id)
                choice.setBackgroundColor(getColor(R.color.selected))
                freezeState(playerChoices)
                gameViewModel.playGame()
                viewComputerChoice(computerChoices)
                setResult()
            }
        }

        binding.refresh.setOnClickListener {
            restartGame(allAvailableChoices)
        }
    }

    override fun onSaveInstanceState(outState: Bundle, outPersistentState: PersistableBundle) {

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
                binding.result.changeBackground(R.color.draw)
            }
            "win" -> {
                binding.result.text = getString(R.string.player_win)
                binding.result.setTextColor(getColor(R.color.white))
                binding.result.changeBackground(R.color.result)
            }
            else -> {
                binding.result.text = getString(R.string.computer_win)
                binding.result.setTextColor(getColor(R.color.white))
                binding.result.changeBackground(R.color.result)
            }
        }
    }

    // highlight computer choice
    private fun viewComputerChoice(computerChoices: List<ShapeableImageView>) {
        val computerChoice =
            computerChoices.filter { it.contentDescription == gameViewModel.computerChoice }
        computerChoice[0].changeBackground(R.color.selected)
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
        gameViewModel.resetAllChoice()
        gameViewModel.setStatus(true)
        allAvailableChoices.forEach {
            it.isEnabled = gameViewModel.status
            it.changeBackground(R.color.transparent)
        }
        binding.result.text = getString(R.string.start_game)
        binding.result.setTextColor(getColor(R.color.VS))
        binding.result.changeBackground(R.color.transparent)
    }

    // to handle configuration changes
    private fun recreateState(selectedChoices: List<ShapeableImageView>) {
        if (gameViewModel.playerSelectedId != 0) {
            findViewById<ShapeableImageView>(gameViewModel.playerSelectedId).changeBackground(
                R.color.selected
            )

            when (gameViewModel.computerChoice) {
                gameViewModel.choices[0] -> binding.batuComputer
                gameViewModel.choices[1] -> binding.kertasComputer
                gameViewModel.choices[2] -> binding.guntingComputer
                else -> null
            }!!.changeBackground(R.color.selected)

            freezeState(selectedChoices)
            setResult()
        } else {
            restartGame(selectedChoices)
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

    private fun View.changeBackground(Id: Int) {
        setBackgroundColor(getColor(Id))
    }
}