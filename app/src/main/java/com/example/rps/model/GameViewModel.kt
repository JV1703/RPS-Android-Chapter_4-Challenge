package com.example.rps.model

import android.util.Log
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val _choices: List<String> = listOf("Batu", "Kertas", "Gunting")
    val choices get() = _choices

    private var _choice: String = ""
    val choice get() = _choice

    private var _computerChoice: String = ""
    val computerChoice get() = _computerChoice

    private var _result: String = ""
    val result get() = _result

    private var _status: Boolean = true
    val status get() = _status

    private var _playerSelectedId = (0)
    val playerSelectedId get() = _playerSelectedId

    fun setChoice(choice: String) {
        _choice = choice
        Log.d("GameViewModel checking", choice)
    }

    fun setPlayerSelectedId(id: Int) {
        _playerSelectedId = id
    }

    private fun setComputerChoice(): String {
        return choices.random()
    }

    fun setStatus(status: Boolean) {
        _status = status
    }

    fun playGame() {
        _computerChoice = setComputerChoice()
        _result = if (_choice == computerChoice) {
            "draw"
        } else if (choice == choices[0] && computerChoice == choices[2] ||
            choice == choices[1] && computerChoice == choices[0] ||
            choice == choices[2] && computerChoice == choices[1]
        ) {
            "win"
        } else {
            "lose"
        }

        Log.i(
            "result",
            "player: $choice - computer: $computerChoice - result = $result"
        )
    }
}