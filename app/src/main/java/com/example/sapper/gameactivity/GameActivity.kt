package com.example.sapper.gameactivity

import android.os.Bundle
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
    private lateinit var binding: GameActivityBinding
    private val tileUnopenedColor
        get() = ContextCompat.getColor(this, R.color.tile_background_unopened)
    private val tileDisableColor
        get() = ContextCompat.getColor(this, R.color.tile_background_disabled)
    private val tileNextColor
        get() = ContextCompat.getColor(this, R.color.button_background_blue)
    private val tileWinColor
        get() = ContextCompat.getColor(this, R.color.button_background_green)
    private val tileDefeatColor
        get() = ContextCompat.getColor(this, R.color.button_background_red)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = GameActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        createGameboard()
    }

    private fun createGameboard(){
        binding.grid.rowCount = 3
        binding.grid.columnCount = 3
        vm.shuffleCases()
        for (i in 0 until vm.getGameCasesCount()){
            val pile = Button(this)
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
                        binding.rulesTitle.text = getString(R.string.continue_title)
                    }

                    GameCase.WIN -> {
                        it.setBackgroundColor(tileWinColor)
                        binding.rulesTitle.text = getString(R.string.winning_title)
                        binding.grid.children.forEach {
                            it.isEnabled = false
                        }

                        with(binding.buttonNextRound) {
                            this.isVisible = true
                            this.setOnClickListener { onNextButtonClicked() }
                        }
                    }

                    GameCase.DEFEAT -> {
                        it.setBackgroundColor(tileDefeatColor)
                        binding.rulesTitle.text = getString(R.string.defeat_title)
                        binding.grid.children.forEach {
                            it.isEnabled = false
                        }

                        with(binding.buttonRefresh) {
                            this.isVisible = true
                            this.setOnClickListener { onRefreshButtonClicked() }
                        }
                    }
                }
            }

            grid.addView(pile)
        }
    }

    private fun refreshGameboard(){
        binding.grid.removeAllViews()
        binding.rulesTitle.text = getString(R.string.rules_title)
        createGameboard()
    }

    private fun onRefreshButtonClicked(){
        binding.buttonRefresh.isVisible = false
        refreshGameboard()
    }

    private fun onNextButtonClicked(){
        binding.buttonNextRound.isVisible = false
        refreshGameboard()
    }
}