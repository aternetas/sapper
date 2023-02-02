package com.example.sapper.gameactivity

import com.example.sapper.models.GameCase

class GameVM {
    private var gameCases: MutableList<GameCase> = mutableListOf(
        GameCase.NEXT,
        GameCase.NEXT,
        GameCase.NEXT,
        GameCase.WIN,
        GameCase.WIN,
        GameCase.WIN,
        GameCase.DEFEAT,
        GameCase.DEFEAT,
        GameCase.DEFEAT
        )

    fun getGameCasesCount() : Int {
        return gameCases.count()
    }

    fun sendCaseName(index: Int) : GameCase {
        return gameCases[index]
    }

    fun shuffleCases(){
        gameCases.shuffle()
    }
}