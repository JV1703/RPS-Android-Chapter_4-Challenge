package com.example.rps.model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel : ViewModel() {

    private val choices: List<String> = listOf("Batu", "Kertas", "Gunting")

    private var _choice: String = ""
    val choice get() = _choice

    private var _computerChoice: String = ""
    val computerChoice get() = _computerChoice

    private var _result: String = ""
    val result get() = _result

    private var _status: Boolean = true
    val status get() = _status

    private var _playerSelectedId = MutableLiveData<Int>(0)
    val playerSelectedId: LiveData<Int> get() = _playerSelectedId


    fun setChoice(choice: String) {
        _choice = choice
        Log.d("GameViewModel checking", choice)
    }

    private fun computerChoice(): String {
        return choices.random()
    }

    fun setPlayerSelectedId(id: Int) {
        _playerSelectedId.value = id
    }

    fun setStatus(status: Boolean) {
        _status = status
    }


    fun playGame(choice: String) {
        _computerChoice = computerChoice()
        _result = if (choice == computerChoice) {
            "draw"
        } else if (choice == choices[0] && computerChoice == choices[2] ||
            choice == choices[1] && computerChoice == choices[0] ||
            choice == choices[2] && computerChoice == choices[1]
        ) {
            "win"
        } else {
            "lose"
        }

        Log.d(
            "resulttesting",
            "player play: $choice, computer play: $computerChoice, result = $result"
        )
        Log.d("resulttesting", "computer choice = $computerChoice ${computerChoice == choices[0]}")
        Log.d("resulttesting", "computer choice = $computerChoice ${computerChoice == choices[1]}")
        Log.d("resulttesting", "computer choice = $computerChoice ${computerChoice == choices[2]}")

    }

}