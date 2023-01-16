package com.example.sapper.gameactivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.core.view.setMargins
import androidx.gridlayout.widget.GridLayout
import com.example.sapper.R
import com.example.sapper.models.GameCase

class GameActivity : AppCompatActivity() {
    private lateinit var grid: GridLayout
    private lateinit var title: TextView
    private lateinit var refreshBtn: Button
    private lateinit var nextRoundBtn: Button
    private var vm: GameVM = GameVM()
    private val tileUnopenedColor
        get() = ContextCompat.getColor(this, R.color.button_unopened_background)
    private val tileNextColor
        get() = ContextCompat.getColor(this, R.color.button_blue_background)
    private val tileWinColor
        get() = ContextCompat.getColor(this, R.color.button_green_background)
    private val tileDefeatColor
        get() = ContextCompat.getColor(this, R.color.button_red_background)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_activity)
        grid = findViewById(R.id.gridlayout_grid)
        title = findViewById(R.id.textview_title)
        refreshBtn = findViewById(R.id.button_refresh)
        nextRoundBtn = findViewById(R.id.button_next_round)

        createGameboard()
    }

    fun createGameboard(){
        grid.rowCount = 3
        grid.columnCount = 3
        vm.shuffleCases()
        for (i in 0 until vm.getCountGameCases()){
            var pile = Button(this)
            pile.text = getString(R.string.button_text)
            pile.setBackgroundColor(tileUnopenedColor)
            val param = GridLayout.LayoutParams(
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL),
                GridLayout.spec(GridLayout.UNDEFINED, GridLayout.FILL)
            )
            param.setMargins(5)
            pile.layoutParams = param
            pile.setOnClickListener {
                when(vm.sendCaseName(i)){
                    GameCase.NEXT -> {
                        it.setBackgroundColor(tileNextColor)
                        title.text = getString(R.string.textview_next_title)
                    }
                    GameCase.WIN -> {
                        it.setBackgroundColor(tileWinColor)
                        title.text = getString(R.string.textview_win_title)
                        nextRoundBtn.isVisible = true
                    }
                    GameCase.DEFEAT -> {
                        it.setBackgroundColor(tileDefeatColor)
                        title.text = getString(R.string.textview_defeat_title)
                        refreshBtn.isVisible = true
                    }
                }
            }
            grid.addView(pile)
        }
    }

    fun refreshGameboard(){
        grid.removeAllViews()
        createGameboard()
    }

    fun refreshButtonClick(view: View){
        refreshBtn.isVisible = false
        refreshGameboard()
    }

    fun nextButtonClick(view: View){
        nextRoundBtn.isVisible = false
        refreshGameboard()
    }
}
