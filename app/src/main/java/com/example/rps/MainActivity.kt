package com.example.rps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.util.Log
import androidx.activity.viewModels
import androidx.databinding.DataBindingUtil
import com.example.rps.databinding.ActivityMainBinding
import com.example.rps.model.GameViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val gameViewModel: GameViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        setTitleColor()
        binding.gameViewModel = gameViewModel

        binding.batuPlayer.setOnClickListener {
            gameViewModel.setChoice(getString(R.string.batuChoice))
            it.setBackgroundColor(getColor(R.color.choice))
            binding.kertasPlayer.setBackgroundColor(getColor(R.color.white))
            binding.guntingPlayer.setBackgroundColor(getColor(R.color.white))
            gameViewModel.computerChoice()
            viewComputerChoice()
            Log.d("testing-computer choice", gameViewModel.computerChoice)
            gameViewModel.playGame(gameViewModel.choice, gameViewModel.computerChoice)
//            showResult()
        }

        binding.kertasPlayer.setOnClickListener {
            gameViewModel.setChoice(getString(R.string.kertasChoice))
            it.setBackgroundColor(getColor(R.color.choice))
            binding.batuPlayer.setBackgroundColor(getColor(R.color.white))
            binding.guntingPlayer.setBackgroundColor(getColor(R.color.white))
            gameViewModel.computerChoice()
            viewComputerChoice()
            Log.d("testing-computer choice", gameViewModel.computerChoice)
            gameViewModel.playGame(gameViewModel.choice, gameViewModel.computerChoice)
//            showResult()
        }

        binding.guntingPlayer.setOnClickListener {
            gameViewModel.setChoice(getString(R.string.guntingChoice))
            it.setBackgroundColor(getColor(R.color.choice))
            binding.batuPlayer.setBackgroundColor(getColor(R.color.white))
            binding.kertasPlayer.setBackgroundColor(getColor(R.color.white))
            gameViewModel.computerChoice()
            viewComputerChoice()
            Log.d("testing-computer choice", gameViewModel.computerChoice)
            gameViewModel.playGame(gameViewModel.choice, gameViewModel.computerChoice)
//            showResult()
        }


    }




    // function to set the color of the title
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

    private fun showResult() {
        when (gameViewModel.result) {
            "draw" -> {
                binding.result?.text = getString(R.string.draw)
                binding.result?.setTextColor(getColor(R.color.white))
                binding.result?.setBackgroundColor(getColor(R.color.draw))
            }
            "win" -> {
                binding.result?.text = getString(R.string.player_win)
                binding.result?.textSize = 16f
                binding.result?.setTextColor(getColor(R.color.white))
                binding.result?.setBackgroundColor(getColor(R.color.result))
            }
            else -> {
                binding.result?.text = getString(R.string.computer_win)
                binding.result?.textSize = 16f
                binding.result?.setTextColor(getColor(R.color.white))
                binding.result?.setBackgroundColor(getColor(R.color.result))
            }
        }
    }

    private fun viewComputerChoice() {
        when (gameViewModel.computerChoice) {
            "Batu" -> {
                binding.batuComputer.setBackgroundColor(getColor(R.color.choice))
                binding.kertasComputer.setBackgroundColor(getColor(R.color.white))
                binding.guntingComputer.setBackgroundColor(getColor(R.color.white))
            }
            "Kertas" -> {
                binding.batuComputer.setBackgroundColor(getColor(R.color.white))
                binding.kertasComputer.setBackgroundColor(getColor(R.color.choice))
                binding.guntingComputer.setBackgroundColor(getColor(R.color.white))
            }
            "Gunting" -> {
                binding.batuComputer.setBackgroundColor(getColor(R.color.white))
                binding.kertasComputer.setBackgroundColor(getColor(R.color.white))
                binding.guntingComputer.setBackgroundColor(getColor(R.color.choice))
            }
        }
    }

    fun startGame(){
        if (gameViewModel.result!=""){
            gameViewModel.playGame(gameViewModel.choice, gameViewModel.computerChoice)
        }
    }
}