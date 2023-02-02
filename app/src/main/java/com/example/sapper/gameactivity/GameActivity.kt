package com.example.sapper.gameactivity

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.setMargins
import androidx.gridlayout.widget.GridLayout
import com.example.sapper.R
import com.example.sapper.databinding.GameActivityBinding
import com.example.sapper.models.GameCase
import kotlinx.android.synthetic.main.game_activity.*

class GameActivity : AppCompatActivity() {
    private var vm: GameVM = GameVM()
    private lateinit var bindingClass: GameActivityBinding
    private val tileUnopenedColor
        get() = ContextCompat.getColor(this, R.color.tile_unopened_background)
    private val tileDisableColor
        get() = ContextCompat.getColor(this, R.color.tile_disabled_background)
    private val tileNextColor
        get() = ContextCompat.getColor(this, R.color.button_blue_background)
    private val tileWinColor
        get() = ContextCompat.getColor(this, R.color.button_green_background)
    private val tileDefeatColor
        get() = ContextCompat.getColor(this, R.color.button_red_background)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bindingClass = GameActivityBinding.inflate(layoutInflater)
        setContentView(bindingClass.root)

        createGameboard()
    }

    fun createGameboard(){
        bindingClass.grid.rowCount = 3
        bindingClass.grid.columnCount = 3
        vm.shuffleCases()
        for (i in 0 until vm.getGameCasesCount()){
            var pile = Button(this)
            pile.setBackgroundColor(tileUnopenedColor)
            val param = GridLayout.LayoutParams()
            param.setMargins(5)
            param.width = 190
            param.height = 190
            pile.layoutParams = param

            pile.setOnClickListener {
                when(vm.sendCaseName(i)){
                    GameCase.NEXT -> {
                        it.setBackgroundColor(tileNextColor)
                        bindingClass.textviewTitle.text = getString(R.string.textview_next_title)
                    }

                    GameCase.WIN -> {
                        it.setBackgroundColor(tileWinColor)
                        bindingClass.textviewTitle.text = getString(R.string.textview_win_title)
                        bindingClass.grid.children.forEach {
                            it.isEnabled = false
                        }

                        with(bindingClass.buttonNextRound) {
                            this.isVisible = true
                            this.setOnClickListener { nextButtonClick() }
                        }
                    }

                    GameCase.DEFEAT -> {
                        it.setBackgroundColor(tileDefeatColor)
                        bindingClass.textviewTitle.text = getString(R.string.textview_defeat_title)
                        bindingClass.grid.children.forEach {
                            it.isEnabled = false
                        }

                        with(bindingClass.buttonRefresh) {
                            this.isVisible = true
                            this.setOnClickListener { refreshButtonClick() }
                        }
                    }
                }
            }

            grid.addView(pile)
        }
    }

    fun refreshGameboard(){
        bindingClass.grid.removeAllViews()
        bindingClass.textviewTitle.text = getString(R.string.textview_start_title)
        createGameboard()
    }

    fun refreshButtonClick(){
        bindingClass.buttonRefresh.isVisible = false
        refreshGameboard()
    }

    fun nextButtonClick(){
        bindingClass.buttonNextRound.isVisible = false
        refreshGameboard()
    }
}
