package com.example.rps.model

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val choices: List<String> = listOf("Batu", "Kertas", "Gunting")

    private var _choice: String = ""
    val choice get() = _choice

    private var _computerChoice: String = ""
    val computerChoice get() = _computerChoice

    private var _result: String = ""
    val result get() = _result

    fun setChoice(choice: String) {
        _choice = choice
        Log.d("GameViewModel checking", choice)
    }

    fun computerChoice() {
        _computerChoice = choices.random()
    }

    fun playGame(choice: String, computerChoice: String) {
        computerChoice()
        _result = if (choice == computerChoice) {
            "draw"
        } else if (choice == choices[0] && computerChoice == choices[2] || choice == choices[1] && computerChoice == choices[1] || choice == choices[2] && computerChoice == choices[0]
        ) {
            "win"
        } else {
            "lose"
        }

        Log.d("result", "player play: $choice, computer play: $computerChoice, result = $result")
        Log.d("resulttesting", "computer choice = $computerChoice ${computerChoice == choices[0]}")
        Log.d("resulttesting", "computer choice = $computerChoice ${computerChoice == choices[1]}")
        Log.d("resulttesting", "computer choice = $computerChoice ${computerChoice == choices[2]}")

    }

}